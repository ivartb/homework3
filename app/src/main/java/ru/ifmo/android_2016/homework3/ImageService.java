package ru.ifmo.android_2016.homework3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by -- on 20.11.2016.
 */

public class ImageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final String URI = "http://sankt-peterburg.buyreklama.ru/sankt-peterburg/photos/21130508/%E4%EC%203.jpg";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final File file = new File(getFilesDir(), "wallpaper.jpg");
        if (!file.exists()) {
            new Thread(
                    new Runnable() {
                        public void run() {
                            InputStream in = null;
                            FileOutputStream out = null;
                            try {
                                in = new BufferedInputStream((new URL(URI)).openStream());
                                out = new FileOutputStream(file);
                                int counter;
                                byte[] buffer = new byte[1024];
                                while ((counter = in.read(buffer)) != -1) {
                                    out.write(buffer, 0, counter);
                                }
                                sendBroadcast(new Intent(MainActivity.MY_INTENT));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (in != null) {
                                        in.close();
                                    }
                                    if (out != null) {
                                        out.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            ).start();
        }

        return START_STICKY;
    }
}
