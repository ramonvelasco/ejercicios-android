package com.ramonvelasco.intent_image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SegundaActivity extends Activity {
	EditText texto;
	TextView titulo;
	Button ok, back;
	public static String TEXT="TEXT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_layout);

		texto = (EditText) findViewById(R.id.edicion);
		titulo= (TextView) findViewById(R.id.titulo);
		ok = (Button) findViewById(R.id.ok);
		back= (Button) findViewById(R.id.back);
		
		Bundle datos = getIntent().getExtras();
		String nombre = datos.getString("texto");
		//Por ultimo escribimos ese texto en el TextView
		titulo.setText(nombre);
		
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent result = new Intent();
				result.putExtra(TEXT, texto.getText().toString());
				setResult(RESULT_OK, result);
				finish();
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
