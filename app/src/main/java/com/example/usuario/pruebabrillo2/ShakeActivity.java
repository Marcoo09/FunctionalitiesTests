package com.example.usuario.pruebabrillo2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

public class ShakeActivity extends Activity
{
    private Shake mShaker;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new Shake(this);
        mShaker.setOnShakeListener(new Shake.OnShakeListener () {
            public void onShake()
            {
                vibe.vibrate(100);
                AlertDialog.Builder builder = new AlertDialog.Builder(ShakeActivity.this);
                        builder.setPositiveButton(android.R.string.ok, null)
                        .setMessage("Shooken!")
                        .show();
            }
        });
    }

    @Override
    public void onResume()
    {
        mShaker.resume();
        super.onResume();
    }
    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }
}
