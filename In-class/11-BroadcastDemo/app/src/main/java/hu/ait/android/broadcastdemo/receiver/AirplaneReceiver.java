package hu.ait.android.broadcastdemo.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean airplaneState = intent.getBooleanExtra("state", false);

        Toast.makeText(context, "Airplane: " + airplaneState, Toast.LENGTH_SHORT).show();
    }
}
