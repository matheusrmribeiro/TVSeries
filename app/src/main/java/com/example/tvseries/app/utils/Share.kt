package com.example.tvseries.app.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

class Share {

    companion object {
        fun shareText(context: Context, text: String, title: String? = null) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, title)
            startActivity(context, shareIntent, null)
        }
    }

}