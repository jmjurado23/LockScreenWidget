package com.screen.lock.screen;

import android.app.ActionBar;
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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class ConfigureActivity extends Activity implements OnClickListener {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Button white_button;
    Button black_button;
    Button transparent_button;

    public ConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_configure);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle(R.string.app_name);

        // Set the result to CANCELED. This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        white_button = (Button) findViewById(R.id.white_button);
        black_button = (Button) findViewById(R.id.black_button);
        transparent_button = (Button) findViewById(R.id.transparent_button);

        white_button.setOnClickListener(this);
        black_button.setOnClickListener(this);
        transparent_button.setOnClickListener(this);

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
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, WidgetLock.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] {mAppWidgetId});
        sendBroadcast(intent);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveIntervalPref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefs.putString(Integer.toString(appWidgetId), text);
        prefs.commit();
    }

    public void onClick(View v) {
        if(v.equals(white_button))
            clickButton("white");
        else if(v.equals(black_button))
            clickButton("black");
        else if(v.equals(transparent_button))
            clickButton("transparent");
    }
}

