package com.example.sandvich;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings extends Activity {
	
	TextView ipAddressText;
	TextView portText;
	Button updateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		final EditText ipAddressText = (EditText) findViewById(R.id.ipAddressTextField);
		final EditText portText = (EditText) findViewById(R.id.portTextField);
		Button updateButton = (Button) findViewById(R.id.settingsUpdateButton);
		
		try {
			ipAddressText.setText(MainActivity.ipAddress);
			Log.v("Port", MainActivity.port);
			portText.setText(MainActivity.port);
		} catch (Exception e){}
		
		updateButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				boolean noErrors = true;
				String tempIP = ipAddressText.getText().toString();

				if (!((String) portText.getText().toString()).equals("")) {
					MainActivity.port = (String) portText.getText().toString();
				} else {
					// Let user know that port is not set
					noErrors = false;
				}
				
				IPAddressValidator validator = new IPAddressValidator();
				
				if (validator.validate(tempIP)) {
					MainActivity.ipAddress = tempIP;
				} else {
					// Tell user invalid ip address
					noErrors = false;
				}
				
				if (noErrors) {
					Log.v("Setting", "Port: " + portText.getText());
					Log.v("Setting", "IP Address: " + ipAddressText.getText());
					finish();
				} else {
					// Let user know to change errors
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
