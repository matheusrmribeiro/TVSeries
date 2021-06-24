package com.example.tvseries.app.utils

import android.util.Base64
import org.json.JSONObject

class Base64Utils {

    companion object {

        fun toJson(value: String) : JSONObject {
            val argData = Base64.decode(value, Base64.DEFAULT).decodeToString()
            return JSONObject(argData)
        }

        fun toBase64(value: String) : String {
            return Base64.encode(value.toByteArray(), Base64.DEFAULT).decodeToString()
        }

    }

}