package com.example.tut;

public class Medicion {
	
	double ph;
	double ORP;
	double turbidez;
	double TDS;
	int numero_estacion;
	String username;
	String potable;
	
	public Medicion(double ph, double oRP, double turbidez, double tDS, int numero_estacion, String username, String potable) {
		super();
		this.ph = ph;
		this.ORP = oRP;
		this.turbidez = turbidez;
		this.TDS = tDS;
		this.numero_estacion = numero_estacion;
		this.username = username;
		this.potable = potable;
	}
}


