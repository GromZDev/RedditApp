package reddit.app.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import reddit.app.utils.networkstatus.AlertDialogFragment
import reddit.app.utils.networkstatus.OnlineLiveData
import reddit.app.R
import reddit.app.data.DataModel
import reddit.app.data.RedditChildrenResponse
import reddit.app.data.RedditDataResponse
import reddit.app.data.RedditNewsDataResponse

abstract class BaseFragment<T : AppState, I : LogicInterActor<T>> : Fragment() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)

    /** Проверка состояния сети ============================= */
    protected var isNetworkAvailable: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange() {
        context?.let { net ->
            OnlineLiveData(net).observe(
                viewLifecycleOwner,
                {
                    isNetworkAvailable = it
                    if (!isNetworkAvailable) {
                        Toast.makeText(
                            context,
                            R.string.dialog_message_device_is_offline,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun isDialogNull(): Boolean {
        return childFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    private fun showAlertDialog(title: String?, message: String?) {
        childFragmentManager.let {
            AlertDialogFragment.newInstance(title, message).show(it, DIALOG_FRAGMENT_TAG)
        }
    }

    abstract fun setDataToAdapter(data: List<RedditChildrenResponse>)

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "Network_detecting_TAG_999"
    }
}