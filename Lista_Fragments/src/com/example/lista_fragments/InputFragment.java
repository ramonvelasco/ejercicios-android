package com.example.lista_fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class InputFragment extends Fragment {

	public interface Iinput {
		public void anadir(String d);
	}

	Iinput activity;
	EditText edit;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			this.activity = (Iinput) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement Iinput");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.input, container,
				false);
		edit = (EditText) rootView.findViewById(R.id.editText1);
		OnClickListener OnClickAnadir= new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity.anadir(edit.getText().toString());
				edit.setText("");
			}
		};
		Button btn = (Button) rootView.findViewById(R.id.button1);
		btn.setOnClickListener(OnClickAnadir);
		
		
		return rootView;
	}
	 
	
	
}
