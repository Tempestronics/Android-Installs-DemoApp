package com.tempestronics.androidinstallsdemo;

import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.iid.InstanceID;
import com.tempestronics.androidinstallsdemo.util.InstallUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InstallUtils.pushInstall(this, "http://youroctoberserver.com");

        // Generate a unique identifier
        String iid = InstanceID.getInstance(this).getId();

        // Device data
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        model = model.replace(manufacturer, "");

        TextView tvData = (TextView) findViewById(R.id.tvData);
        tvData.setText(Html.fromHtml(
                "<h5>The following data has been pushed to the server</h5>"
                + "<b>Instance ID:</b> " + iid + "<br>"
                + "<b>Manufacturer:</b> " + manufacturer + "<br>"
                + "<b>Model:</b> " + model + "<br>"
                + "<b>Device ID:</b> " + device_id
        ));
    }
}
