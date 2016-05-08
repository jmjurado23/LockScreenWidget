package com.screen.lock.screen;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class ConfigureActivity extends Activity implements OnClickListener {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Button white_button;
    Button black_button;

    public ConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED. This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_configure);
        white_button = (Button) findViewById(R.id.white_button);
        black_button = (Button) findViewById(R.id.black_button);

        white_button.setOnClickListener(this);
        black_button.setOnClickListener(this);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID,
        // finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
    }


    public void clickButton(String button) {
        final Context context = ConfigureActivity.this;

        // Return RESULT_OK from this activity
        saveIntervalPref(context, mAppWidgetId, button);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveIntervalPref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefs.putString("WIDGET_COLOR", text);
        prefs.commit();
    }

    public void onClick(View v) {
        if(v.equals(white_button))
            clickButton("white");
        else if(v.equals(black_button))
            clickButton("black");

    }
}

