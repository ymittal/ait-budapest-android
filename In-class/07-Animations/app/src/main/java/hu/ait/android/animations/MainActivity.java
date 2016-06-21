package hu.ait.android.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation pushAnim = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.push_anim);
        final Animation sendAnim = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.send_anim);

        final TextView tvData = (TextView) findViewById(R.id.tvData);
        final Button btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSend.startAnimation(pushAnim);
            }
        });

        pushAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvData.startAnimation(sendAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
