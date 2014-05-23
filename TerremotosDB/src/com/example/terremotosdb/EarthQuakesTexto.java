package com.example.terremotosdb;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EarthQuakesTexto extends Activity {
	/** Called when the activity is first created. */
	String det;
	EarthQuakeDB bd;
	EarthQuake eq;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.texto_earthquake);

		Bundle bundle = getIntent().getExtras();
		long det = bundle.getInt("det");

		TextView place = (TextView) findViewById(R.id.place);
		TextView detail = (TextView) findViewById(R.id.detail);
		TextView mag = (TextView) findViewById(R.id.mag);
		TextView coordinates = (TextView) findViewById(R.id.coordinates);
		TextView time = (TextView) findViewById(R.id.time);
		//TextView created = (TextView) findViewById(R.id.created);
		//TextView updated = (TextView) findViewById(R.id.updated);

		bd = EarthQuakeDB.getDB(this, DBHelper.DATABASE_VERSION);

		eq = bd.getEarthQuakeById(det);

		place.setText(eq.getPlace());
		detail.setText(eq.getDetail());
		mag.setText("Mag: "+String.valueOf(eq.getMagnitude()));
		coordinates.setText("Lat : "+String.valueOf(eq.getLat()) + " Long: "+ String.valueOf(eq.getLong()));
		
		//time.setText(String.valueOf(Date.(String.valueOf(eq.getTime()))));
		Date date = new Date(eq.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fecha = null;
		try {
			fecha = sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		time.setText(String.valueOf(fecha));
		//created.setText(eq.getCreated());
		//updated.setText(eq.getUpdated());



	}

}