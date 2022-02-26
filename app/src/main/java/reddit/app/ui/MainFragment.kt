package reddit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope
import reddit.app.R
import reddit.app.base.AppState
import reddit.app.base.BaseFragment
import reddit.app.data.RedditChildrenResponse
import reddit.app.databinding.FragmentMainBinding
import reddit.app.interactor.MainInterActor
import reddit.app.utils.image.GlideImageLoader
import reddit.app.utils.networkstatus.isOnline
import reddit.app.utils.viewById
import reddit.app.viewmodel.MainViewModel

class MainFragment : BaseFragment<AppState, MainInterActor>() {

    override lateinit var model: MainViewModel
    private val scope: Scope = getKoin().createScope<MainFragment>()

    private val observer = Observer<AppState> {
        renderData(it)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    /** Инициируем два ProgressBar через кастомный делегат */
    private val progressBarHorizontal by viewById<ProgressBar>(R.id.progress_bar_horizontal)
    private val progressBarRound by viewById<ProgressBar>(R.id.progress_bar_round)

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: RedditChildrenResponse) {

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater, container, false).also {
        _binding = it
        iniViewModel()
        initViews()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /** Сеть =================================================== */
        isNetworkAvailable = context?.let { it1 -> isOnline(it1) } == true
        if (isNetworkAvailable) {
            model.getData(true)

        } else {
            model.getData(false)
            showNoInternetConnectionDialog()
            binding.mainFragmentRoot.showSnackBarForConnection(
                getString(R.string.no_connection), 5000,
                { setColorSbBG() },
                { setTextSbColor(ContextCompat.getColor(context, R.color.black)) }
            )
        }
        /** ======================================================== */

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** Также закрываем наш скоуп когда уже не нужен */
    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel?.data?.children?.isEmpty() == true) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {

                        binding.mainActivityRecyclerview.adapter =
                            dataModel?.let {
                                dataModel.data?.children?.let { it1 ->
                                    MainAdapter(
                                        onListItemClickListener,
                                        it1, GlideImageLoader()
                                    )
                                }
                            }
                    } else {
                        binding.mainActivityRecyclerview.let {
                            dataModel?.data?.children?.let { list ->
                                adapter!!.setData(
                                    list
                                )
                            }
                        }
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    progressBarHorizontal.visibility = VISIBLE
                    progressBarRound.visibility = GONE
                    progressBarHorizontal.progress = appState.progress
                } else {
                    progressBarHorizontal.visibility = GONE
                    progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
          //  model.getData(true)


        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = VISIBLE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }

    /** Реализация Koin ========================== */
    private fun initViews() {
        binding.mainActivityRecyclerview.layoutManager =
            LinearLayoutManager(context)
        binding.mainActivityRecyclerview.adapter = adapter
    }

    private fun iniViewModel() {
        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        val viewModel: MainViewModel by scope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, observer)
    }

    override fun setDataToAdapter(data: List<RedditChildrenResponse>) {
        adapter?.setData(data)
    }

    /** Сетим кастомные Экстеншены для SnackBar: */
    private fun View.showSnackBarForConnection(
        text: String, length: Int, bg: Snackbar.() -> Unit, col: Snackbar.() -> Unit
    ) {
        val sBar = Snackbar.make(this, text, length)
        sBar.bg()
        sBar.col()
        sBar.show()
    }

    private fun Snackbar.setColorSbBG() {
        setBackgroundTint(ContextCompat.getColor(context, R.color.img_stroke_color))
    }

    private fun Snackbar.setTextSbColor(color: Int) {
        setTextColor(color)
    }
}