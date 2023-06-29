package com.example.homework2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class CustomViewDrum(
    context: Context,
    attributes: AttributeSet
) : View(context, attributes) {

    private val colors = listOf(
        context.getColor(R.color.red),
        context.getColor(R.color.orange),
        context.getColor(R.color.yellow),
        context.getColor(R.color.green),
        context.getColor(R.color.light_blue),
        context.getColor(R.color.blue),
        context.getColor(R.color.violet)
    )

    private val _resultRandom = MutableLiveData<String>()
    val resultRandom: LiveData<String>
        get() = _resultRandom

    private var prevValue: Double = 0.0
    private var currentColorIndex = 0
    private var isRotating = false

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    init {
        setOnClickListener {
            if (!isRotating) {
                isRotating = true
                rotateDrum()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = minOf(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDrum(canvas)
    }

    private fun drawDrum(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = minOf(width, height) / 2f

        val startAngle = 0f
        val sweepAngle = 360f / colors.size

        for (i in colors.indices) {
            val colorIndex = (currentColorIndex + i) % colors.size
            paint.color = colors[colorIndex]
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle + i * sweepAngle,
                sweepAngle,
                true,
                paint
            )
        }
    }

    private fun rotateDrum() {
        _resultRandom.value = "VOID"
        val randomIndexColor = (0..6).random()

        val randomOffset1 = DRUM_SHARE * randomIndexColor
        val randomOffset2 = 360 - prevValue
        val randomOffset3 = randomOffset1 + randomOffset2

        val rotates = randomIndexColor * 360f
        val randomDuration = Random.nextInt(1000, 4000).toLong()

        animate()
            .rotationBy(randomOffset3.toFloat() + rotates)
            .setDuration(randomDuration)
            .withEndAction {
                getResult(randomIndexColor)
                isRotating = false
                prevValue = randomOffset1
            }.start()
    }

    private fun getResult(randomResult: Int) {
        when (randomResult) {
            0 -> _resultRandom.value = "https://1gai.ru/uploads/posts/2015-01/1421252108_2015-kawasaki.jpg"
            1 -> _resultRandom.value = "ГОЛУБОЙ"
            2 -> _resultRandom.value = "https://avtoshkola177.ru/wp-content/uploads/2020/10/moto2.jpg"
            3 -> _resultRandom.value = "ЖЕЛТЫЙ"
            4 -> _resultRandom.value = "https://avatars.mds.yandex.net/get-vertis-journal/4220003/Bez-imeni-1.jpg_1628511811120/orig"
            5 -> _resultRandom.value = "КРАСНЫЙ"
            6 -> _resultRandom.value = "ФИОЛЕТОВЫЙ"
            else -> throw Exception("Invalidate randomResult!")
        }
    }

    companion object {
        private const val DRUM_SHARE = 360.0 / 7
    }
}