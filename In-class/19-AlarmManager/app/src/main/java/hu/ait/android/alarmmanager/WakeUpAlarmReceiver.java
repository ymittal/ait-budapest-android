package hu.ait.android.alarmmanager;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;


public class WakeUpAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            String message = bundle.getString(MainActivity.KEY_ALARM_MESSAGE);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            try {
                KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("MyKeyguardLock");
                try {
                    kl.disableKeyguard();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
                wakeLock.acquire();

                Intent newIntent = new Intent(context, MainActivity.class);
                newIntent.putExtra(MainActivity.KEY_WAKEUP, 1);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);

                context.startActivity(newIntent);
            } catch (Exception e) {
                Toast.makeText(
                        context,
                        "There was a problem, but we still received an alarm",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }

        } catch (Exception e) {
            Toast.makeText(
                    context,
                    "There was an exception, but we still received an alarm",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}