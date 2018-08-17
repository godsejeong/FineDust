package com.fine_dust

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
class ApiExplorer(name : String) : Thread() {
    var data = ""
    var name_ = name

    var servicekey = "DXpwTc0VFcnpT6grVOvVKe6CFwKqd4kOtJsRPrYk0YR%2BxtbmNep5SrDPZJr5%2B5Lm5C8XaTZS4geK9O6Svg6m5Q%3D%3D"
    override fun run() {
        val url = URL("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName=$name_&pageNo=1&numOfRows=10&_returnType=json&ServiceKey=$servicekey&ver=1.3")
        Log.e("url",url.toString())
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Content-type", "application/json")
        System.out.println("Response code: " + conn.responseCode)
        var rd: BufferedReader
        if (conn.responseCode in 200..300) {
            rd = BufferedReader(InputStreamReader(conn.inputStream))
        } else {
            rd = BufferedReader(InputStreamReader(conn.errorStream))
        }
        val sb = StringBuilder()

        for (i in rd.readLine()) {
            if (i != null) {
                sb.append(i)
            }
        }

        rd.close()
        conn.disconnect()
        data = sb.toString()
        Log.e("asdf", sb.toString())
    }

    fun data(): String {
        return data
    }
}