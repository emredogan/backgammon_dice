package com.emredogan.tavlazari

import android.app.AlertDialog
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.intro_dialog.view.*
import kotlin.math.sqrt
import kotlin.random.Random


class MainActivity : AppCompatActivity(), SensorEventListener {

    private var isDialogVisible: Boolean = false
    private lateinit var sensorManager: SensorManager
    private var mAccel // acceleration apart from gravity
            = 0f
    private var mAccelCurrent // current acceleration including gravity
            = 0f
    private var mAccelLast // last acceleration including gravity
            = 0f
    private lateinit var mediaPlayer: MediaPlayer
    private var isRolling = false
    private var numberOfDicesRolled = 0
    private var randomNumber1 = 1
    private var randomNumber2 = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.registerListener(mSensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        mAccel = 0.00f
        mAccelCurrent = SensorManager.GRAVITY_EARTH
        mAccelLast = SensorManager.GRAVITY_EARTH

        result_image.setImageResource(R.drawable.dice_1)
        result_image2.setImageResource(R.drawable.dice_1)

        roll_button.setOnClickListener {
            rollDice()
        }
        if(!prefs.dontShowIntro) {
            showIntroductionDialogue()
        }
    }

    private fun showIntroductionDialogue() {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.intro_dialog, null)

        val shake = AnimationUtils.loadAnimation(this,R.anim.shake_animation)
        mDialogView.phoneShakeImage.animation = shake
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle(getString(R.string.welcome_string))
        //show dialog
        val mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.dismiss_button.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            if(mDialogView.dontShowCheckBox.isChecked) {
                prefs.dontShowIntro = true
            }
        }

        if (mAlertDialog.isShowing) {
            isDialogVisible = true
        }

        mAlertDialog.setOnDismissListener {
            isDialogVisible = false
        }

    }



    private val mSensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(se: SensorEvent) {
            val x = se.values[0]
            val y = se.values[1]
            val z = se.values[2]
            mAccelLast = mAccelCurrent
            mAccelCurrent =
                sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = mAccelCurrent - mAccelLast
            mAccel = mAccel * 0.9f + delta // perform low-cut filter
            if (mAccel > 12) {
               rollDice()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.dice_sound)
        sensorManager.registerListener(
            mSensorListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        sensorManager.unregisterListener(mSensorListener)
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()
        super.onPause()
    }


    private fun rollDice() {
        if(!isRolling && !isDialogVisible) {
            val prevDiceText: String = String.format(
                resources.getString(R.string.previous_dice_string), randomNumber1, randomNumber2)
            previousDiceText.text = prevDiceText

            isRolling = true
            result_image.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim))
            result_image2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim))

            roll_button.isClickable = false
            roll_button.setBackgroundColor(resources.getColor(R.color.colorRed))
            playDiceSound()

            randomNumber1 = Random.nextInt(1, 7)
            randomNumber2 = Random.nextInt(1, 7)

            val resourceId1 = when (randomNumber1) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                6 -> R.drawable.dice_6
                else -> R.drawable.dice_3
            }

            val resourceId2 = when (randomNumber2) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                6 -> R.drawable.dice_6
                else -> R.drawable.dice_3
            }
            result_image.setImageResource(resourceId1)
            result_image2.setImageResource(resourceId2)

            stopAnimation()
            numberOfDicesRolled++
        }
    }

    private fun playDiceSound() {
        mediaPlayer.start()
    }

    private fun stopAnimation() {
        Handler().postDelayed({ result_image.clearAnimation() }, 1500)
        Handler().postDelayed({
            result_image2.clearAnimation()
            roll_button.isClickable = true
            roll_button.setBackgroundColor(resources.getColor(R.color.colorGrey))
            isRolling = false
        }, 1500)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        TODO("Not yet implemented")
    }
}
