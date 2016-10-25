package com.moyinoluwa.barcodedetection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {
    Bitmap barcodeBitmap;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        textView = (TextView) findViewById(R.id.text_view);

        barcodeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.images);

        imageView.setImageBitmap(barcodeBitmap);
    }

    public void processBarcode(View view) {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        if (!barcodeDetector.isOperational()) {
            new AlertDialog.Builder(this)
                    .setMessage("Barcode detector could not be set up on your device :(")
                    .show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(barcodeBitmap).build();
            SparseArray<Barcode> barcode = barcodeDetector.detect(frame);

            int size = barcode.size();
            String barcodeValue = "";

            if (size == 0) {
                textView.setText("No information available");
            } else {
                for (int i = 0; i < size; i++) {
                    barcodeValue += (barcode.valueAt(i).displayValue + "\n");
                }
                textView.setText(barcodeValue);
            }
        }

        barcodeDetector.release();
    }
}
