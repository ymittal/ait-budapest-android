package hu.ait.android.qrcodereader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView tvCode;
    private CameraSourcePreview cameraSourcePreview;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCode = (TextView) findViewById(R.id.tvCode);
        cameraSourcePreview = (CameraSourcePreview) findViewById(R.id.cameraSourcePreview);

        setupBarcodeDetector();
        setupCameraSource();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestNeededPermission();
    }

    private void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(MainActivity.this,
                        "I need it for QR", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    101);
        } else {
            startCameraSource();
        }
    }

    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                cameraSourcePreview.start(cameraSource);
            } catch (IOException e) {
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    private void setupBarcodeDetector() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    tvCode.post(new Runnable() {
                        public void run() {
                            tvCode.setText(barcodes.valueAt(0).displayValue);
                            Linkify.addLinks(tvCode, Linkify.ALL);
                        }
                    });
                }
            }
        });

        if (!barcodeDetector.isOperational()) {
            Log.w("TAG_QR", "Detector dependencies are not yet available.");
        }

    }

    private void setupCameraSource() {
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(15.0f)
                .setRequestedPreviewSize(640, 640)
                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSourcePreview.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }
}
