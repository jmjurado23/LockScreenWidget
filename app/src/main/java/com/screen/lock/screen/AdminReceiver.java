package com.screen.lock.screen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;



public class AdminReceiver extends DeviceAdminReceiver {

	public void onEnabled(Context context, Intent intent) {
	    showToast(context, context.getString(R.string.admin_enabled));
	    }
	
	    @Override
	    public CharSequence onDisableRequested(Context context, Intent intent) {
	    return context.getString(R.string.admin_changed);
	    }
	
	    @Override
	    public void onDisabled(Context context, Intent intent) {
	    showToast(context, context.getString(R.string.admin_disabled));
	    }
	    void showToast(Context context, CharSequence msg) {
	        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	
	    }

}