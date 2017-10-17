package com.moyinoluwa.barcodedetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

private const val EMPTY_STRING = ""
private const val NO_INFO = "No information available"
private const val ERROR_MESSAGE = "Barcode detector could not be set up on your device :("

class MainActivity : AppCompatActivity() {

    lateinit var barcodeBitmap: Bitmap
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<View>(R.id.image_view) as ImageView
        textView = findViewById<View>(R.id.text_view) as TextView

        barcodeBitmap = BitmapFactory.decodeResource(resources, R.drawable.images)

        imageView.setImageBitmap(barcodeBitmap)
    }

    fun processBarcode(view: View) {
        val barcodeDetector = BarcodeDetector.Builder(this)
                                             .setBarcodeFormats(Barcode.ALL_FORMATS)
                                             .build()

        if (!barcodeDetector.isOperational) {
            AlertDialog.Builder(this)
                       .setMessage(ERROR_MESSAGE)
                       .show()
        } else {
            val frame = Frame.Builder().setBitmap(barcodeBitmap).build()
            val barcode = barcodeDetector.detect(frame)

            val size = barcode.size()

            if (size == 0) {
                textView.text = NO_INFO
            } else {
                var barcodeValue = EMPTY_STRING

                for (i in 0 until size) {
                    barcodeValue += "${barcode.valueAt(i).displayValue}\n"

                }
                textView.text = barcodeValue
            }
        }

        barcodeDetector.release()
    }
}