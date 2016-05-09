package com.screen.lock.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TutoActivity extends Activity implements OnClickListener{

	private Button buttonSettings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle(R.string.app_name);

        buttonSettings = (Button)findViewById(R.id.buttonSettings);
        
        buttonSettings.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tuto, menu);
        return true;
    }

	public void onClick(View arg0) {
		if(arg0.equals(buttonSettings)){
			Intent settings = new Intent(Settings.ACTION_SECURITY_SETTINGS);
			startActivity(settings);
		}
		
	}

    
}
