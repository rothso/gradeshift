package io.gradeshift.ui.resolver

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.google.android.gms.common.GoogleApiAvailability
import org.jetbrains.anko.support.v4.withArguments

class ErrorDialogFragment : DialogFragment() {

    companion object {
        private val ARGS_ERROR_CODE = "error_code"
        private val ARGS_RESULT_CODE = "result_code"

        fun create(errorCode: Int, resultCode: Int): ErrorDialogFragment {
            return ErrorDialogFragment().withArguments(
                    ARGS_ERROR_CODE to errorCode,
                    ARGS_RESULT_CODE to resultCode)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val errorCode = this.arguments.getInt(ARGS_ERROR_CODE)
        val resultCode = this.arguments.getInt(ARGS_RESULT_CODE)
        return GoogleApiAvailability.getInstance().getErrorDialog(
                this.activity, errorCode, resultCode)
    }

    override fun onDismiss(dialog: DialogInterface) {
        (activity as ResolverActivity).onDialogDismissed()
    }
}