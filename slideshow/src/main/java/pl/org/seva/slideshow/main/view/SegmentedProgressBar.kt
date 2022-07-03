package pl.org.seva.slideshow.main.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

class SegmentedProgressbar(context: Context?, attrs: AttributeSet?): View(context, attrs) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var fraction: Float? = null
        set(value) {
            field = value
            invalidate()
        }
    var numberOfSegments = 0
    var progress = 0
        set(value) {
            field = if (value >= 0) {
                if (value <= numberOfSegments)
                    value
                else numberOfSegments
            }
            else 0
            invalidate()
        }

    init {
        backgroundPaint.color = resources.getColor(com.google.android.material.R.color.material_dynamic_primary20, null)
        backgroundPaint.style = Paint.Style.FILL
        progressedPaint.color = resources.getColor(com.google.android.material.R.color.material_dynamic_primary80, null)
        progressedPaint.style = Paint.Style.FILL
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val segmentSize = (width / numberOfSegments)
        fun drawSegment(id: Int, paint: Paint) {
            canvas.drawRoundRect(
                3F + id * segmentSize,
                0F,
                (id + 1) * segmentSize - 3F,
                height.toFloat(),
                40F,
                40F,
                paint,
            )
        }

        for (i in 0 until  numberOfSegments) {
            drawSegment(i, if (i < progress) progressedPaint else backgroundPaint)
        }
        if (fraction != null) {
            canvas.clipRect(
                Rect(
                    (3F + progress * segmentSize + fraction!! * segmentSize).roundToInt(),
                    0,
                    (id + 1) * segmentSize - 3,
                    height,
                )
            )
        }
        drawSegment(progress, progressedPaint)
    }

}
