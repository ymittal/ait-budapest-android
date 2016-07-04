package hu.ait.android.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 101;
    public static final String KEY_DATA = "KEY_DATA";
    private ImageView ivPhoto;
    private Bitmap imageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        Button btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentTakePhoto, REQUEST_CODE);
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_DATA)) {
            ivPhoto.setImageBitmap((Bitmap) savedInstanceState.getParcelable(KEY_DATA));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (imageBitmap != null) {
            outState.putParcelable(KEY_DATA, imageBitmap);
        }

        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            imageBitmap = (Bitmap) data.getExtras().get("data");

            ivPhoto.setImageBitmap(imageBitmap);
        }
    }
}
