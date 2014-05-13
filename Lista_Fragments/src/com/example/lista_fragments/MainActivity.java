package com.example.lista_fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements InputFragment.Iinput {
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;

	private MiLista listFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//   El if es para no introducir repetidos fragment. Solo se a–adiran una vez
		if (savedInstanceState == null) {
			listFragment = new MiLista();
			fragmentManager = getFragmentManager();
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.InputFragment, new InputFragment());
			fragmentTransaction.add(R.id.MiLista, listFragment, "list");
			//   Metemos el tag "list" para poder referenciar este fragment una vez este creado y ejecutandose
			fragmentTransaction.commit();
		}

	}

	@Override
	public void anadir(String d) {
		
		((MiLista)getFragmentManager().findFragmentByTag("list")).addItem(d);
        //  Hacemos referencia al fragment en ejecucion mediante el tagh introducido previamente, 
		//  esto hace acceder exactamente a la clase que se esta ejecutando.

	}


}
