package com.ramonvelascopreferences;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

public class PreferencesActivity extends Activity {
	Switch auto;
	Spinner inter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		auto = (Switch) findViewById(R.id.switch1);
		inter = (Spinner) findViewById(R.id.spinner1);
		
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
		
		
		if (autorefresh) {
		auto.setChecked(true);
		}
		else {
		auto.setChecked(false);
		}
		final List<String> list=new ArrayList<String>();
		list.add("5 min");
		list.add("10 min");
		list.add("15 min");
		list.add("30 min");
		list.add("60 min");

		ArrayAdapter<String> adp= new ArrayAdapter<String>(this,
		                                android.R.layout.simple_list_item_1,list);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inter.setAdapter(adp);
		if (!interval.equals("Null"))
		{
			int i=list.indexOf(interval);
		inter.setSelection(i);
		}

	}

	protected void onPause() {
		super.onPause();
		SharedPreferences preferencias = getSharedPreferences(
				String.valueOf(R.string.preferencias), Context.MODE_PRIVATE);
		/*
		 * Necesitamos utilizar un editor para escribir en las preferencias
		 */
		SharedPreferences.Editor editor = preferencias.edit();
		/*
		 * Usando el editor podemos escribir los valores que queramos
		 */
		editor.putBoolean(String.valueOf(R.string.autorefresh), auto.isChecked());
		editor.putString(String.valueOf(R.string.interval),inter.getSelectedItem().toString());
		// Debemos validar los cambios con un commit()
		editor.commit();
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
