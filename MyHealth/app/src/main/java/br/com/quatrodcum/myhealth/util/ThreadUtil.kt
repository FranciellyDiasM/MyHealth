package br.com.quatrodcum.myhealth.util

import android.annotation.SuppressLint
import android.os.AsyncTask


@Suppress("DEPRECATION")
@SuppressLint("StaticFieldLeak")
class ThreadUtil {

    @Suppress("OVERRIDE_DEPRECATION")
    companion object {
        fun <T> exec(
            onPreExecute: () -> Unit = {},
            doInBackground: () -> T,
            postExecuteTask: (T) -> Unit
        ) {
            object : AsyncTask<Void, Void, T>() {
                override fun doInBackground(vararg params: Void?): T {
                    return doInBackground()
                }

                override fun onPostExecute(result: T) {
                    postExecuteTask(result)
                }

                override fun onPreExecute() {
                    onPreExecute()
                }
            }.execute()
        }
    }
}
