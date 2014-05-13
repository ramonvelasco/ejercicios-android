package com.example.lista_fragments;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MiLista extends ListFragment {

	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	
//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		list = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void addItem(String item) {
		//   A–adimos el nuevo item a la lista ya creada
		list.add(0, item);
		adapter.notifyDataSetChanged();
	}
	
	public void onActivityCreated(Bundle inState)
	{
		//  Recuperamos la lista al crear la actividad. Si es null es porque se trata de la primera ejecucion,
		//  sino es porque se ha producido un cambio de estado.
		super.onActivityCreated(inState);
		if (!(inState==null))
		{
			list.addAll(inState.getStringArrayList("lista"));
		}
	}
		
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Guardamos el contenido de la lista para poder recuperarla en caso de cambio de configuraci—n
		
		outState.putStringArrayList("lista",list);
		super.onSaveInstanceState(outState);
	}
}
