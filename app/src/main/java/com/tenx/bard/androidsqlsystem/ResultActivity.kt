package com.tenx.bard.androidsqlsystem

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvResult.text = intent.getStringExtra("result")
        tvResultLabel.apply {
            alpha = 0f
            tvResultLabel.visibility = View.VISIBLE
            animate().alpha(1f).setStartDelay(500).setDuration(500).setInterpolator(FastOutSlowInInterpolator())
        }
        tvResult.apply {
            alpha = 0f
            tvResult.visibility = View.VISIBLE
            animate().alpha(1f).setStartDelay(1500).setDuration(500).setInterpolator(FastOutSlowInInterpolator())
        }
        btnTryAgain.apply {
            alpha = 0f
            btnTryAgain.visibility = View.VISIBLE
            animate().alpha(1f).setStartDelay(2500).setDuration(500).setInterpolator(FastOutSlowInInterpolator())
        }
        btnTryAgain.setOnClickListener {
            setResult(1)
            finish()
        }

    }

}
