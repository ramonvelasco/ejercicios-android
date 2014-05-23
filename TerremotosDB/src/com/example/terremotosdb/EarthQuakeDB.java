package com.example.terremotosdb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class EarthQuakeDB {

	private static EarthQuakeDB db = null;
	private SQLiteDatabase bd = null;
	private DBHelper helper;
	ArrayList<EarthQuake> earthquakes = new ArrayList<EarthQuake>();

	public static String ID = "_id";
	public static String ID_STR = "id_str";
	public static String PLACE = "place";
	public static String TIME = "time";
	public static String DETAIL = "detail";
	public static String MAGNITUDE = "magnitude";
	public static String LAT = "lat";
	public static String LONG = "long";
	public static String URL = "url";
	public static String CREATED_AT = "created_at";
	public static String UPDATED_AT = "updated_at";
	
	public EarthQuake eq ;

	private EarthQuakeDB(Context context, int version) {
		helper = new DBHelper(context, DBHelper.DATABASE_NAME, null, version);

	}

	public static EarthQuakeDB getDB(Context context, int version) {
		if (db == null) {
			db = new EarthQuakeDB(context, version);
			db.open();
		}

		return db;
	}

	private void open() {
		bd = helper.getWritableDatabase();
	}

	public void close() {
		bd.close();
	}

	public ArrayList<EarthQuake> getEarthQuakesFiltered(float magnitud) {
		String where = "magnitude > ?";
		String[] valoresWhere = { String.valueOf(magnitud) };
		Cursor resultados = db.query(where, valoresWhere);

		Log.d("TAG DB", "Cursor DB");
		while (resultados.moveToNext()) {
			// Recuperamos los valores mediante getXXX(nº columna)
			long _id = resultados.getLong(0);
			String id_str = resultados.getString(1);
			String place = resultados.getString(2);
			long time = resultados.getLong(3);
			String detail = resultados.getString(4);
			float magnitude = resultados.getFloat(5);
			float lat = resultados.getFloat(6);
			float lon = resultados.getFloat(7);
			String url = resultados.getString(8);
			int created_at = resultados.getInt(9);
			int updated_at = resultados.getInt(10);


			Date date = new Date(time);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fecha = null;
			try {
				fecha = sdf.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			EarthQuake e = new EarthQuake(_id,id_str, place, time, detail, magnitude,
					lat, lon, url, created_at, updated_at);
			// La a–adimos al arraylist
			this.earthquakes.add(e);
		}

		return earthquakes;

	}

	public EarthQuake getEarthQuakeById(long id) {
		String where = "_id = ?";
		String[] valoresWhere = { String.valueOf(id) };
		Cursor resultados = db.query(where, valoresWhere);

		while (resultados.moveToNext()) {
			// Recuperamos los valores mediante getXXX(nº columna)
			long _id = resultados.getLong(0);
			String id_str = resultados.getString(1);
			String place = resultados.getString(2);
			long time = resultados.getLong(3);
			String detail = resultados.getString(4);
			float magnitude = resultados.getFloat(5);
			float lat = resultados.getFloat(6);
			float lon = resultados.getFloat(7);
			String url = resultados.getString(8);
			int created_at = resultados.getInt(9);
			int updated_at = resultados.getInt(10);

			Date date = new Date(time);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fecha = null;
			try {
				fecha = sdf.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			eq = new EarthQuake(_id, id_str, place, time, detail, magnitude, lat, lon,
					url, created_at, updated_at);
		}

		return eq;

	}

	private Cursor query(String where, String[] valoresWhere) {
		String[] result_columns = new String[] {};
		String groupBy = null;
		String having = null;
		String orderBy = TIME + " DESC";
		Cursor resultados = bd.query(false, DBHelper.DATABASE_TABLE,
				result_columns, where, valoresWhere, groupBy, having, orderBy,
				null);

		return resultados;
	}

	public long insert(EarthQuake q) {
		ContentValues valores = new ContentValues();
		/*
		 * Escribimos las parejas clave-valor donde la clave es el nombre de la
		 * columna y el valor, el valor que deseamos insertar
		 */
		Date date = new Date();
		int created = (int)date.getTime();
		valores.put(ID_STR, q.getIdStr());
		valores.put(PLACE, q.getPlace());		
		valores.put(TIME, q.getTime());
		valores.put(DETAIL, q.getDetail());
		valores.put(MAGNITUDE, q.getMagnitude());
		valores.put(LAT, q.getLat());
		valores.put(LONG, q.getLong());
		valores.put(CREATED_AT, created);
		valores.put(UPDATED_AT, created);

		return bd.insert(DBHelper.DATABASE_TABLE, null, valores);

	}

	public void update(ContentValues valores, String where,
			String[] valoresWhere) {

		bd.update(DBHelper.DATABASE_TABLE, valores, where, valoresWhere);

	}

	public void delete(String where, String[] valoresWhere) {

		bd.delete(DBHelper.DATABASE_TABLE, where, valoresWhere);

	}

}
