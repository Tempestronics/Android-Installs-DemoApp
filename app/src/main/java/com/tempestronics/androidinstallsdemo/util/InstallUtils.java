package com.tempestronics.androidinstallsdemo.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Moz on 08/03/16.
 */
public class InstallUtils {

    private InstallUtils() {
        //No instances.
    }

    public static void pushInstall(Context context, String server_url) {
        try {
            // Generate a unique identifier
            String iid = InstanceID.getInstance(context).getId();

            // Device data
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            String device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            model = model.replace(manufacturer, "");

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("instance_id", iid)
                    .add("device_id", device_id)
                    .add("manufacturer", manufacturer.trim())
                    .add("model", model.trim())
                    .build();
            Request request = new Request.Builder()
                    .url(server_url + "/android_install.json")
                    .post(formBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                }
            });

        } catch (Throwable e) {
            // Could not decode
        }
    }
}
