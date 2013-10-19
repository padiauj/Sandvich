package com.example.sandvich;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MakeSandwich extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_sandwich);
		
		RadioGroup breadGroup = (RadioGroup) findViewById(R.id.breadGroup);
		RadioButton whiteButton = (RadioButton) findViewById(R.id.whiteButton);
		RadioButton ryeButton = (RadioButton) findViewById(R.id.ryeButton);
		RadioButton wheatButton = (RadioButton) findViewById(R.id.wheatButton);
		CheckBox roastBeefButton = (CheckBox) findViewById(R.id.roastBeefButton);
		CheckBox veggieBurgerButton = (CheckBox) findViewById(R.id.veggieBurgerButton);
		CheckBox turkeyButton = (CheckBox) findViewById(R.id.turkeyButton);
		CheckBox lettuceButton = (CheckBox) findViewById(R.id.lettuceButton);
		CheckBox tomatoButton = (CheckBox) findViewById(R.id.tomatoButton);
		Button submitButton = (Button) findViewById(R.id.submitButton);
		
		submitButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				
				// Add functionality to send order
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.make_sandwich, menu);
		return true;
	}

}
