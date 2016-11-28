package ru.ifmo.android_2016.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by -- on 25.11.2016.
 */
public class ImageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ImageService.class));
    }
}
