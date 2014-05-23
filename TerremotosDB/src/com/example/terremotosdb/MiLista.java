package com.example.terremotosdb;

import java.util.ArrayList;

import com.example.terremotosdb.AsynTaskListadoTerremotos.IUpdateQuakes;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MiLista extends ListFragment implements IUpdateQuakes{

	ArrayAdapter<EarthQuake> adaptador;
	EarthQuakeListAdapter listAdapter;
	EarthQuakeDB bd;
	ArrayList<EarthQuake> earthquakes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bd = EarthQuakeDB.getDB(inflater.getContext(),
				DBHelper.DATABASE_VERSION);

		earthquakes = bd.getEarthQuakesFiltered(0);
		listAdapter = new EarthQuakeListAdapter(getActivity(), earthquakes);
		setListAdapter(listAdapter);
		// listAdapter.notifyDataSetChanged();

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void onActivityCreated(Bundle inState) {
		// Recuperamos la lista al crear la actividad. Si es null es porque se
		// trata de la primera ejecucion,
		// sino es porque se ha producido un cambio de estado.
		super.onActivityCreated(inState);
		
		new AsynTaskListadoTerremotos(getActivity(), this).execute("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");

		// if (!(inState == null)) {
		// earthquakes.addAll(bd.getEarthQuakesFiltered(0));
		// }
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Guardamos el contenido de la lista para poder recuperarla en caso de
		// cambio de configuraci—n

		outState.putSerializable("earthquakes", earthquakes);
		super.onSaveInstanceState(outState);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {

		Intent textIntent = new Intent(getActivity(), EarthQuakesTexto.class);
		long det = listAdapter.getItemId(position);
		textIntent.putExtra("det", det);
		startActivity(textIntent);
	}

	// public void refreshEarthquakes() {
	// new UpdateEarthQuakesTask(earthquakeActivity)
	// .execute(getString(R.string.quake_feed));
	// }

	public void addQuake(EarthQuake q) {
		// TODO Auto-generated method stub
		Log.d("TAG", "RECEIVED: " + q);

		if (q.getId() > 0) {
			earthquakes.add(0, q);
			listAdapter.notifyDataSetChanged();
		}
	}

}
