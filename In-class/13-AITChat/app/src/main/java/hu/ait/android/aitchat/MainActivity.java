package hu.ait.android.aitchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResideMenu resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);

        ResideMenuItem loginMenu = new ResideMenuItem(this, R.mipmap.ic_launcher,
                "Login");
        loginMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        resideMenu.addMenuItem(loginMenu,ResideMenu.DIRECTION_LEFT);

        ResideMenuItem aboutMenu = new ResideMenuItem(this, R.mipmap.ic_launcher,
                "About");
        aboutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "App made by AIT team",
                        Toast.LENGTH_SHORT).show();
            }
        });
        resideMenu.addMenuItem(aboutMenu,ResideMenu.DIRECTION_LEFT);
    }
}
