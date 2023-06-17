package com.jdc.iotcontrolcenter.ui.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.jdc.iotcontrolcenter.R
import com.jdc.iotcontrolcenter.data.model.DHT11Data
import com.jdc.iotcontrolcenter.ui.viewmodel.Dht11SensorViewModel
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TemperatureActivity : AppCompatActivity() {

    private val dht11SensorViewModel : Dht11SensorViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog
    private val dataList = mutableListOf<DHT11Data>()
    private val handler = Handler()
    private val updateInterval = 2000L
    private val dataPointHandler: Runnable = object : Runnable {
        override fun run() {

            dht11SensorViewModel.getAllDhtRecordList()
            handler.postDelayed(this, updateInterval)
        }
    }
    private lateinit var graph: GraphView
    private lateinit var series: LineGraphSeries<DataPoint>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)

        loadingDialog = LoadingDialog().createProgressDialog(this)
        loadingDialog.show()

        graph = findViewById(R.id.graph)
        series = LineGraphSeries()

        initGraphView()
        requestApiForDHTData()
        dht11SensorViewModel.getAllDhtRecordList()
        val clearButton = findViewById<Button>(R.id.deleteData)
        clearButton.setOnClickListener {
            dht11SensorViewModel.clearDhtSensorRecords()
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
        dht11SensorViewModel.dhtRecordListObservable.observe(this, Observer { dhtSensorRecords ->
            dataList.clear()
            dataList.addAll(dhtSensorRecords)
            updateGraphView()
            loadingDialog.hide()
        })
    }

    private fun initGraphView() {
        series.isDrawDataPoints = true
        series.color = Color.rgb(240, 105, 33)
        graph.addSeries(series)

        val gridLabelRenderer = graph.gridLabelRenderer
        gridLabelRenderer.textSize = 18f

        graph.viewport.isScrollable = true
        graph.viewport.setScalableY(true)

    }

    private fun updateGraphView() {
        val dataPoints = dataList.mapIndexed { index, data ->
            DataPoint(index.toDouble(), data.temperature.toDouble())
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
