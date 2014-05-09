package com.ramonvelasco.calculadora_fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TecladoFragment extends Fragment {

	public interface ICalculadora {
		public void digito(String d);

		public void operacion(String o);
		
		public void borrar();
	}

	ICalculadora activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			this.activity = (ICalculadora) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ICalculadora");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main2, container,
				false);

		// Array de ids
		TypedArray arDig = getResources().obtainTypedArray(
				R.array.ids_array_digito);
		TypedArray arOp = getResources().obtainTypedArray(
				R.array.ids_array_operacion);

		OnClickListener OnClickDig = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Button b = (Button) v;
				String buttonText = b.getText().toString();
				activity.digito(buttonText);
			}
		};
		OnClickListener OnClickOp = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Button b = (Button) v;
				String buttonText = b.getText().toString();
				activity.operacion(buttonText);
			}
		};
		OnClickListener OnClickDel= new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				activity.borrar();
			}
		};
		for (int i = 0; i < arDig.length(); i++) {
			Button btn = (Button) rootView.findViewById(arDig.getResourceId(i,
					0));
			btn.setOnClickListener(OnClickDig);
		}
		arDig.recycle();
		for (int i = 0; i < arOp.length(); i++) {
			Button btn = (Button) rootView.findViewById(arOp
					.getResourceId(i, 0));
			btn.setOnClickListener(OnClickOp);
		}
		arOp.recycle();
		
		Button btn = (Button) rootView.findViewById(R.id.button1);
		btn.setOnClickListener(OnClickDel);
		
		return rootView;
	}
}
