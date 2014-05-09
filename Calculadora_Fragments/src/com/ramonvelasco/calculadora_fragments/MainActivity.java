package com.ramonvelasco.calculadora_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends Activity implements
		TecladoFragment.ICalculadora {

	Boolean punto = true;
	Calc calc;

	FragmentManager fragmentManager;
	DisplayFragment displayFragment;

	// DisplayFragment displayFragment= new DisplayFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		calc = new Calc();
		fragmentManager = getFragmentManager();
		displayFragment = (DisplayFragment) fragmentManager
				.findFragmentById(R.id.fragment1);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.

		displayFragment.mostrarResultado(savedInstanceState.getString("display"));
		calc.dig1 = savedInstanceState.getString("dig1");
		calc.dig2 = savedInstanceState.getString("dig2");
		calc.op1 = savedInstanceState.getString("op1");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate and
		// onRestoreInstanceState if the process is
		// killed and restarted by the run time.
		

		savedInstanceState.putString("display", displayFragment.devolverResutlado());
		savedInstanceState.putString("dig1", calc.dig1);
		savedInstanceState.putString("dig2", calc.dig2);
		savedInstanceState.putString("op1", calc.op1);
		
		super.onSaveInstanceState(savedInstanceState);
	}


	
		@Override
		public void borrar(){

			calc.dig = "";
			calc.dig1 = "";
			calc.dig2 = "";
			calc.op = "";
			calc.op1 = "";
			displayFragment.mostrarResultado("");

		}


	@Override
	public void digito(String d) {
		// TODO Auto-generated method stub
		if (!d.equals(".")) {
			displayFragment.anadirResultado(d);
			// display.append(d);
		} else if (punto) {
			displayFragment.anadirResultado(d);
			// display.append(d);
			punto = false;
		}
	}

	@Override
	public void operacion(String o) {
		// TODO Auto-generated method stub
		punto = true;
		calc.dig = displayFragment.devolverResutlado();
		calc.op = o;
		displayFragment.mostrarResultado(calc.Calcul());
		if (displayFragment.devolverResutlado().contains(".")) {
			punto = false;
		}
	}

}
