package com.vahabghadiri.degreeviewsampleapp.weightcontrol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vahabghadiri.degreeviewsampleapp.R
import kotlinx.android.synthetic.main.activity_weight_control.*
import kotlin.math.roundToInt

class WeightControlActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_control)
        setupWeightViews()
    }

    private fun renderCurrentLatestWeight(weight: Float) {
//        latestWeight = weight
//        rvWeights.post {
//            val px = mmPixel * 10 * weight - startOffset + badStartOffset
//            rvWeights.smoothScrollBy(px.toInt(), 0, OvershootInterpolator())
//        }
    }

    private fun setupWeightViews() {

        with(rvWeights) {
            layoutManager = LinearLayoutManager(
                this@WeightControlActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            adapter = WeightsRvAdapter(MutableList(300) { WeightListItem(it) },
                object : WeightsRvAdapter.WeightsRvAdapterCallback {
                    override fun onItemClick(data: WeightListItem) {

                    }
                })
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val offset = recyclerView.computeHorizontalScrollOffset()
                    renderWeight(offset)
                }
            })
        }

        frmWhite.setOnTouchListener { v, event ->
            rvWeights.onTouchEvent(event)
        }

        rvWeights.post {
            val itemWith = baseContext.dpToPx(50)
            badStartOffset = itemWith / 2
            mmPixel = itemWith.toFloat() / 10
            val w1 = rvWeights.measuredWidth.toFloat() / 2
            startOffset = w1.toInt()
            renderWeight(0)
        }
    }

    private var latestWeight = 0f
    private var mmPixel = 0f
    private var startOffset = 0
    private var badStartOffset = 0
    private fun renderWeight(offset: Int) {
        val rawValue = offset + startOffset - badStartOffset
        val result = (rawValue.toFloat() / mmPixel) / 10

        if (mmPixel!=0f)
        latestWeight = result.toOneDigit()
        val str = "$latestWeight kg"
        tvWeighthNum.text = str
    }

    private fun Context.dpToPx(value: Number): Int {
        val dip = value.toFloat()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resources.displayMetrics).toInt()
    }

    private fun Float.toOneDigit(): Float {
        return (this * 10).roundToInt().toFloat() / 10
    }
}
