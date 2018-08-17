package com.fine_dust

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import android.widget.AdapterView
import android.widget.Toast
import org.json.JSONObject
import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser


class MainActivity : AppCompatActivity() {
    var citydata = ArrayList<String>()
    var stationdata = ArrayList<String>()
    var json = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mainText.text = api.data()
//서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종
        citydata.add("도시선택")
        citydata.add("서울")
        citydata.add("부산")
        citydata.add("대구")
        citydata.add("인천")
        citydata.add("광주")
        citydata.add("대전")
        citydata.add("울산")
        citydata.add("경기")
        citydata.add("강원")
        citydata.add("충북")
        citydata.add("충남")
        citydata.add("전북")
        citydata.add("전남")
        citydata.add("경북")
        citydata.add("경남")
        citydata.add("제주")
        citydata.add("세종")

        var adatper = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, citydata)

        citySpinner.adapter = adatper


        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var name = citySpinner.getItemAtPosition(position) as String
                if(name != "도시선택"){
                    api(name)
                }else{
                    Toast.makeText(applicationContext,"도시를 선택해 주세요",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        stationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var station = stationSpinner.getItemAtPosition(position) as String
                if(station != "세부정보선택"){
                    mise(station)
                }else{
                    Toast.makeText(applicationContext,"세부정보를 선택해 주세요",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }

    fun api(name : String) {
        var api = ApiExplorer(name)
        api.start()
        api.join()
        json = api.data()
        var obj = JSONParser().parse(json) as org.json.simple.JSONObject
        var array = obj.get("list") as JSONArray

        stationLayout.visibility = View.VISIBLE
        stationdata.add("세부정보 선택")
        for (i in 0 until array.size) {
            var tmp = array[i] as org.json.simple.JSONObject
            stationdata.add(tmp.get("stationName") as String)
        }
        var adatper = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,stationdata)
        stationSpinner.adapter = adatper
        }

    fun mise(station : String) {
        var obj = JSONParser().parse(json) as org.json.simple.JSONObject
        var array = obj.get("list") as JSONArray

        for (i in 0 until array.size) {
            var tmp = array[i] as org.json.simple.JSONObject

            if (tmp.get("stationName") == station) {
                mainText.visibility = View.VISIBLE
                mainText.text = station + " 미세먼지 농도 : " + tmp.get("pm25Value")
            }
        }
    }
}


