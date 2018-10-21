package com.example.usuario.pruebabrillo2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    SeekBar ligthBar;

    Context context;

    int Sbrigthness;

    HandleChangesInScreen handle = new HandleChangesInScreen();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ligthBar = findViewById(R.id.seekBar);

        context = getApplicationContext();

        Sbrigthness = getScreenBrightness(context);

        ligthBar.setProgress(Sbrigthness);

        ligthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Get app context object.
                Context context = getApplicationContext();

                // Check whether has the write settings permission or not.
                //boolean settingsCanWrite = hasWriteSettingsPermission(context);
                boolean settingsCanWrite = handle.hasWriteSettingsPermission(context);
                // If do not have then open the Can modify system settings panel.
                if(!settingsCanWrite) {
                    handle.changeWriteSettingsPermission(context);
                }else {
                    handle.changeScreenBrightness(context, i);
                }

                //Change brightness
    /*
                int mode = -1;
                try {
                    mode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE); //this will return integer (0 or 1)
                } catch (Exception e) {}

                if (mode == 1) {
                    //Automatic mode
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                }

                Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,i);

                try {
                    int br = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);  //this will get the information you have just set...

                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.screenBrightness = (float) br / 255; //...and put it here
                    getWindow().setAttributes(lp);
                } catch (Exception e) {}
*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private int getScreenBrightness(Context context){
        int brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,0);

        return brightness;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasWriteSettingsPermission(Context context)
    {
        boolean ret = true;
        // Get the result from below code.
        ret = Settings.System.canWrite(context);
        return ret;
    }

    // Start can modify system settings panel to let user change the write settings permission.
    private void changeWriteSettingsPermission(Context context)
    {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        context.startActivity(intent);
    }

    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private void changeScreenBrightness(Context context, int screenBrightnessValue)
    {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);

        /*
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = screenBrightnessValue / 255f;
        window.setAttributes(layoutParams);
        */
    }
}
