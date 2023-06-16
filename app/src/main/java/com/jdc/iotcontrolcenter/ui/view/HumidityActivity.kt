package com.jdc.iotcontrolcenter.ui.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.jdc.iotcontrolcenter.R
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.domain.TemperatureSensorManagement
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.launch

class HumidityActivity : AppCompatActivity() {

    private val temperatureSensorManagement = TemperatureSensorManagement()
    private lateinit var loadingDialog: AlertDialog
    private val dataList = mutableListOf<DHT11Data>()
    private val handler = Handler()
    private val updateInterval = 2000L
    private val dataPointHandler: Runnable = object : Runnable {
        override fun run() {
            requestApiForDHTData()
            handler.postDelayed(this, updateInterval)
        }
    }
    private lateinit var graph: GraphView
    private lateinit var series: LineGraphSeries<DataPoint>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_humidity)
        loadingDialog = LoadingDialog().createProgressDialog(this)
        loadingDialog.show()

        graph = findViewById(R.id.graph)
        series = LineGraphSeries()

        initGraphView()
        requestApiForDHTData()
        val clearButton = findViewById<Button>(R.id.deleteData)
        clearButton.setOnClickListener {
            lifecycleScope.launch {
                temperatureSensorManagement.clearRecords()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        handler.postDelayed(dataPointHandler, updateInterval)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(dataPointHandler)
    }

    private fun requestApiForDHTData() {
        lifecycleScope.launch {
            dataList.clear()
            dataList.addAll(temperatureSensorManagement.getAllDHTRecords())
            updateGraphView()
            loadingDialog.hide()
        }
    }

    private fun initGraphView() {
        series.isDrawDataPoints = true
        series.color = Color.rgb(199, 0, 57)
        graph.addSeries(series)

        val gridLabelRenderer = graph.gridLabelRenderer
        gridLabelRenderer.textSize = 18f

        graph.viewport.isScrollable = true
        graph.viewport.setScalableY(true)

        loadingDialog.hide()
    }

    private fun updateGraphView() {
        val dataPoints = dataList.mapIndexed { index, data ->
            DataPoint(index.toDouble(), data.humidity.toDouble())
        }.toTypedArray()

        series.resetData(dataPoints)

        if (dataList.size >= 2) {
            val labels = dataList.map { data -> data.time }.toTypedArray()
            val staticLabelsFormatter = StaticLabelsFormatter(graph)
            staticLabelsFormatter.setHorizontalLabels(labels)
            graph.gridLabelRenderer.labelFormatter = staticLabelsFormatter
        }

        graph.onDataChanged(false, false)
    }
}