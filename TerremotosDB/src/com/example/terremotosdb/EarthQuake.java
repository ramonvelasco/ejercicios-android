package com.example.terremotosdb;

import java.io.Serializable;

public class EarthQuake implements Comparable<EarthQuake>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2889290593506153325L;
	private long _id;
	private String id_str;
	private String place;
	private long time;
	private String detail;
	private float magnitude;
	private float lat;
	private float lon;
	private String url;
	private int created_at;
	private int updated_at;
	//Constructor
	public EarthQuake(long i,String id_str,String p,long t,String d,float m,float lat,float lon,String u,int ca, int ua){
		this._id = i;
		this.id_str = id_str;
		this.place = p;
		this.time = t;
		this.detail = d;
		this.magnitude = m;
		this.lat = lat;
		this.lon = lon;
		this.url = u;
		this.created_at = ca;
		this.updated_at = ua;
	}
	public EarthQuake() {
		// TODO Auto-generated constructor stub
	}
	//toString
	public String toString(){
		return this._id + " "+this.place + " " + this.detail;
	}
	
	long getId() {
		return _id;
	}
	void setId(long _id) {
		this._id = _id;
	}
	String getIdStr() {
		return id_str;
	}
	void setIdStr(String id_str) {
		this.id_str = id_str;
	}
	String getPlace() {
		return place;
	}
	void setPlace(String place) {
		this.place = place;
	}
	long getTime() {
		return time;
	}
	void setTime(long time) {
		this.time = time;
	}
	String getDetail() {
		return detail;
	}
	void setDetail(String detail) {
		this.detail = detail;
	}
	float getMagnitude() {
		return magnitude;
	}
	void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
	}
	float getLat() {
		return lat;
	}
	void setLat(float lat) {
		this.lat = lat;
	}
	float getLong() {
		return lon;
	}
	void setLong(float lon) {
		this.lon = lon;
	}
	String getUrl() {
		return url;
	}
	void setUrl(String url) {
		this.url = url;
	}
	
	void setCreated(int created) {
		this.created_at = created;
	}
	int getCreated() {
		return created_at;
	}
	void setUpdated(int updated) {
		this.updated_at = updated;
	}
	int getUpdated() {
		return updated_at;
	}
	@Override
	public int compareTo(EarthQuake arg0) {
		/*
		 * Para realizar ordenacion natural de objetos
		 * utilizamos este metodo que devuelve un int.
		 * Si devolvemos -1 quiere decir que nuestro objeto
		 * (this) va delante del pasado como parametros.
		 * Si devuelve 0 quiere decir que ambos estan al mismo nivel
		 * Si devuelve 1 quiere decir que nuestro objeto
		 * iria detras del pasado como parametro.
		 * En este caso ordenaremos por codigo
		 */
		if(this.time>arg0.time){
			return -1;
		}
		else if(this.time<arg0.time){
			return 1;
		}
		return 0;
	}

	
}
