package reddit.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import reddit.app.R
import reddit.app.data.RedditChildrenResponse
import reddit.app.utils.networkstatus.AlertDialogFragment
import reddit.app.utils.networkstatus.isOnline

abstract class BaseFragment<T : AppState, I : LogicInterActor<T>> : Fragment() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)

    /** Проверка состояния сети ============================= */
    protected var isNetworkAvailable: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isNetworkAvailable = context?.let { isOnline(it) } == true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = context?.let { isOnline(it) } == true
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