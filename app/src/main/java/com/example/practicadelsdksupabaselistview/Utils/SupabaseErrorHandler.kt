package ec.edu.uteq.app.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.github.jan.supabase.exceptions.RestException

object SupabaseErrorHandler {

    fun show(context: Context, e: Exception) {
        val title = if (e is RestException) e.error else "Error"
        val message = if (e is RestException) e.description else (e.message ?: "Error desconocido")

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}