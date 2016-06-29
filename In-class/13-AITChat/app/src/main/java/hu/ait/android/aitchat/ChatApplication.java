package hu.ait.android.aitchat;

import android.app.Application;

import com.backendless.Backendless;

public class ChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // feel free to play with my app key :)
        Backendless.initApp(this,
                "0944A04D-2DC9-AFF6-FF3E-969AEE334D00",
                "1A390D29-789E-6ACD-FF67-0A991B6B4000",
                "v1");
    }
}
