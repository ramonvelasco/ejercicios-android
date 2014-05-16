package com.ramonvelascopreferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView auto,inter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		auto = (TextView) findViewById(R.id.autorefresh);
		inter = (TextView) findViewById(R.id.intervalo);
	}

	protected void onResume(){
		super.onResume();
			SharedPreferences preferencias 
			= getSharedPreferences(String.valueOf(R.string.preferencias)
					,Context.MODE_PRIVATE);
			/*
			 * Recuperamos los valores mediante su clave.
			 * Debemos indicar un valor por defecto
			 * en caso de que la preferencia no exista
			 */
			boolean autorefresh 
			= preferencias.getBoolean(String.valueOf(R.string.autorefresh)
					, false);
			String interval 
			= preferencias.getString(String.valueOf(R.string.interval)
					, "Null");
			//Mostramos los valores en los textview
			auto.setText(String.valueOf(autorefresh));
			inter.setText(interval);
			
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, PreferencesActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
