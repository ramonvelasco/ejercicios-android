package com.ramonvelasco.calculadora;

public class Calc {

public String op;
public String op1="";
public String dig;
public String dig1="";
public String dig2="";

	
	public String Calcul(){
		
		if (!op.equals("=")){
			this.dig1=this.dig;
			this.op1=this.op;
			return "";
			
		}
		else
		{
			this.dig2=this.dig;
			if (!(this.dig1.equals("")||this.dig2.equals("")||this.op1.equals(""))){
			double d1 = Double.parseDouble(this.dig1);
			double d2 = Double.parseDouble(this.dig2);
			if (op1.equals("+"))
			{
				d1= d1+d2;
			}
			else if (op1.equals("-"))
			{
				d1= d1-d2;
			}
			else if (op1.equals("x"))
			{
				d1= d1*d2;
			}
			else if (op1.equals("/"))
			{
				d1= d1/d2;
			}
			this.dig1=String.valueOf(d1);
			return this.dig1;
		}
			else return "";
			}
		
		
	}
	
	
}
