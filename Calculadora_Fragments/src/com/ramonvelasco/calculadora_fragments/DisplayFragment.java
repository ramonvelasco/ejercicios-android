package com.ramonvelasco.calculadora_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayFragment extends Fragment {
	
	TextView display;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		display = (TextView) rootView.findViewById(R.id.textView1);
		return rootView;
	}
	
	public void anadirResultado(String d)
	{
		display.append(d);
	}
	
	public void mostrarResultado(String d)
	{
		display.setText(d);
	}
	
	public String devolverResutlado()
	{
		
		return display.getText().toString();
	}
}
