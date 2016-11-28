package ru.ifmo.android_2016.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity{

    TextView errorText;
    ImageView image;

    BroadcastReceiver screenReceiver, downloadedReceiver;

    public static final String MY_INTENT = "DOWNLOADED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorText = (TextView) findViewById(R.id.text_error);
        image = (ImageView) findViewById(R.id.image);
        File file = new File(getFilesDir(), "wallpaper.jpg");
        if (!file.exists()) {
            errorText.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
        } else {
            errorText.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }

        screenReceiver = new ImageReceiver();
        downloadedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                File file = new File(getFilesDir(), "wallpaper.jpg");
                if (file.exists()) {
                    errorText.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                }
            }
        };
        registerReceiver(screenReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(downloadedReceiver, new IntentFilter(MY_INTENT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenReceiver);
        unregisterReceiver(downloadedReceiver);
    }
}
