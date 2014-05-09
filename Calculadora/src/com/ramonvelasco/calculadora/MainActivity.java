package com.ramonvelasco.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	Boolean punto= true;
	TextView display;
	Calc calc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		display = (TextView) this.findViewById(R.id.textView1);
		calc = new Calc();
		Button del = (Button) findViewById(R.id.button1);
		del.setOnClickListener(onClickListener);

	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	     super.onRestoreInstanceState(savedInstanceState);
	     // Restore UI state from the savedInstanceState.
	     // This bundle has also been passed to onCreate.
	     
	     display.setText(savedInstanceState.getString("display"));
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
	     super.onSaveInstanceState(savedInstanceState);
	     
	     savedInstanceState.putString("display", display.getText().toString());
	     savedInstanceState.putString("dig1", calc.dig1);
	     savedInstanceState.putString("dig2", calc.dig2);
	     savedInstanceState.putString("op1", calc.op1);
	}
	
	public OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			calc.dig = "";
			calc.dig1 = "";
			calc.dig2 = "";
			calc.op = "";
			calc.op1 = "";
			display.setText("");

		}
	};

	public void digito(View v) {
		Button but = (Button) v;
		if (!but.getText().equals("."))
		{
			display.append(but.getText());
		}
		else 
			if (punto)
			{
				display.append(but.getText());
				punto = false;
			}


	}

	public void operacion(View v) {
		 punto= true;
		Button but = (Button) v;
		calc.dig = String.valueOf(display.getText());
		calc.op = String.valueOf(but.getText());
		display.setText(calc.Calcul());
		if (display.getText().toString().contains("."))
		{
			punto=false;
		}

	}

}
