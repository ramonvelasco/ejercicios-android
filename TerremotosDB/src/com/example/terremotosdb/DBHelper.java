package com.example.terremotosdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	static final String DATABASE_NAME = "TerremotosDB.db";
	static final String DATABASE_TABLE = "Terremotos";
	static final int DATABASE_VERSION = 1;
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * Metodo que debe contener las sentencias SQL para la creacion inicial
		 * de la base de datos. Es decir, creacion de tablas e insercion de
		 * datos iniciales si fuera necesario
		 */
		String sql = "CREATE TABLE Terremotos "
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", id_str TEXT UNIQUE"
				+ ", place TEXT, time INTEGER, detail TEXT, magnitude REAL, lat REAL, long REAL, url TEXT, created_at INTEGER, updated_at INTEGER)";
		// Ejecutamos la consulta con el metodo execSQL
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*
		 * Este metodo se ejecutaria si tuviesemos que realizar la actualizacion
		 * de la base de datos a una version superior
		 */
		if ((oldVersion == 1) && (newVersion == 3)) {
			// Recuperar los datos

			// Eliminar la tabla

			// Crear la tabla modificadad

			// Pasarle los datos
			//String sql = "CREATE TABLE Vehiculos....";
		}
		db.execSQL("DROP TABLE IF EXISTS " + "Terremotos");
		onCreate(db);

	}

}
