package br.com.quatrodcum.myhealth.util

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

fun String.renderHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}

fun String.transformFlattenedJson(): String {
    val jsonObject = JsonParser.parseString(this).asJsonObject
    val transformedJson = JsonObject()

    val groupedFields = mutableMapOf<String, MutableMap<String, JsonElement>>()
    for ((key, value) in jsonObject.entrySet()) {
        if (key.contains(".")) {
            val parts = key.split(".")
            val prefix = parts[0]
            val field = parts[1]

            groupedFields.computeIfAbsent(prefix) { mutableMapOf() }[field] = value
        } else {
            transformedJson.add(key, value)
        }
    }

    for ((prefix, fields) in groupedFields) {
        val groupArray = JsonArray()
        val groupObject = JsonObject()

        fields.forEach { (field, fieldValue) ->
            groupObject.add(field, fieldValue)
        }

        groupArray.add(groupObject)
        transformedJson.add(prefix, groupArray)
    }

    return Gson().toJson(transformedJson)
}