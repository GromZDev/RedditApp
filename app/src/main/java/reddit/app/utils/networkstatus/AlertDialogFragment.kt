package reddit.app.utils.networkstatus

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

/** Фрагмент для показа уведомления о том, что нет интернета */
class AlertDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        var alertDialog = getStubAlertDialog(context!!)
        val args = arguments
        if (args != null) {
            val title = args.getString(TITLE_EXTRA)
            val message = args.getString(MESSAGE_EXTRA)
            alertDialog = getAlertDialog(context, title, message)
        }
        return alertDialog
    }

    companion object {

        private const val TITLE_EXTRA = "Alert_Tag_title_111"
        private const val MESSAGE_EXTRA = "Alert_Tag_message_222"

        fun newInstance(title: String?, message: String?): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()
            args.putString(TITLE_EXTRA, title)
            args.putString(MESSAGE_EXTRA, message)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }
}
