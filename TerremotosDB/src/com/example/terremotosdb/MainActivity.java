package com.example.terremotosdb;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

//	ListView lstTerremotos;
//	ArrayAdapter<EarthQuake> adaptador;
//	EarthQuakeListAdapter listAdapter;
//	EarthQuakeDB bd;
//	ArrayList<EarthQuake> earthquakes;

	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;

	private MiLista listFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		bd = EarthQuakeDB.getDB(this,
//				DBHelper.DATABASE_VERSION);

		if (savedInstanceState == null) {
			listFragment = new MiLista();
			fragmentManager = getFragmentManager();
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.MiLista, listFragment, "list");
			// Metemos el tag "list" para poder referenciar este fragment una
			// vez este creado y ejecutandose
			fragmentTransaction.commit();
		}

		// lstTerremotos.setAdapter(adaptador);
		// earthquakes.addAll(bd.getEarthQuakesFiltered(0));
		// listAdapter.notifyDataSetChanged();
		// adaptador.notifyDataSetChanged();

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

	
//	public void addQuake(EarthQuake q) {
//		((MiLista)getFragmentManager().findFragmentByTag("list")).addNewQuake(q);
//	}

}
