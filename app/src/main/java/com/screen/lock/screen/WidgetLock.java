package com.screen.lock.screen;


import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetLock extends AppWidgetProvider {

	public static String ACTION_WIDGET_RECEIVER_LOCK = "ActionReceiverWidgetLock";
	private Context c;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            // Actualizar el widget

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget);


            Intent active = new Intent(context, WidgetLock.class);
            active.setAction(ACTION_WIDGET_RECEIVER_LOCK);

            PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);

            // Read color of the widget
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            String color = sharedPref.getString(Integer.toString(appWidgetId), "white");
            if (color.equals("white"))
                remoteViews.setImageViewResource(R.id.button_lock, R.drawable.buttonlock_white);
            else if (color.equals("black"))
                remoteViews.setImageViewResource(R.id.button_lock, R.drawable.buttonlock_black);
            else if (color.equals("transparent"))
                remoteViews.setImageViewResource(R.id.button_lock, R.drawable.buttonlock_transparent);


            // Add events to buttons and background
            remoteViews.setOnClickPendingIntent(R.id.button_lock,
                    actionPendingIntent);
            PendingIntent actionPendingIntent2 = PendingIntent.getBroadcast(context, 0, active, 0);
            remoteViews.setOnClickPendingIntent(R.id.image_lock,
                    actionPendingIntent2);

            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
	}
	

	@Override
	public void onReceive(Context context, Intent intent) {

		final String action = intent.getAction();

		c = context;
		
		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			final int appWidgetId = intent.getExtras().getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[] { appWidgetId });
			}
		} else {
			// check, if our Action was called
			if (intent.getAction().equals(ACTION_WIDGET_RECEIVER_LOCK)) {
				try {
					//create a policy for System Admin
					DevicePolicyManager mDPM;
					mDPM = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
			        mDPM.lockNow();
			        super.onReceive(context, intent);

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(context, context.getString(R.string.no_admin), Toast.LENGTH_LONG).show();
					Intent intent3 =  new Intent(context, TutoActivity.class);
					intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent3);
				}
			}
			super.onReceive(context, intent);
		}
	}
}