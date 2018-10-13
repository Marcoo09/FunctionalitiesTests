package com.example.usuario.pruebabrillo2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class screenEnabled extends AppCompatActivity {
    int defaultTurnOffTime;

    Button btn;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_enabled);

        context = getApplicationContext();

        btn = findViewById(R.id.button);
        // Check whether has the write settings permission or not.

        boolean settingsCanWrite = hasWriteSettingsPermission(context);

        if (!settingsCanWrite) {
            changeWriteSettingsPermission(context);
            defaultTurnOffTime = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 0);

        }

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(View view) {
                //To set the time to never
                // Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,Integer.MAX_VALUE);
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,60000);


            }
        });

        //Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT, defaultTurnOffTime);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasWriteSettingsPermission(Context context) {
        boolean ret = true;
        // Get the result from below code.
        ret = Settings.System.canWrite(context);
        return ret;
    }

    // Start can modify system settings panel to let user change the write settings permission.
    private void changeWriteSettingsPermission(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        context.startActivity(intent);
    }
}
