package com.example.terremotosdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AsynTaskListadoTerremotos extends
		AsyncTask<String, Void, EarthQuake> {

	public interface IUpdateQuakes {
		void addQuake(EarthQuake q);
	}

	private static final String TAG = "EARTHQUAKE";

	private Context mContext;
	private EarthQuakeDB bd;
	private IUpdateQuakes i;

	public AsynTaskListadoTerremotos(Context context, IUpdateQuakes i) {
		mContext = context;
		this.i = i;
		bd = EarthQuakeDB.getDB(context,
				DBHelper.DATABASE_VERSION);
	}

	ProgressDialog progreso;

	// Tipo de datos que le llega al:
	// <doInBackground, onProgress, onPostExecute>
	// ArrayList donde guardaremos a las personas recuperadas

	@Override
	protected void onPostExecute(EarthQuake q) {
		Log.d("TAG", "SENT: " + q);
		i.addQuake(q);
	}

	// @Override
	// protected void onPreExecute() {
	// progreso = ProgressDialog.show(MainActivity.this,
	// "Realizando petici—n", "Consultando terremotos");
	// }

	@Override
	protected EarthQuake doInBackground(String... arg0) {
		/*
		 * Tarea asincrona que recupera los datos en formato JSON y los
		 * convierte en objetos tipo persona para pasarselos al listview. En
		 * este caso la peticion es tipo GET
		 */
		HttpClient cliente = new DefaultHttpClient();
		String url = arg0[0];
		HttpGet peticion = new HttpGet(url);
		try {
			// Lanzamos la peticion y recuperamos la respuesta
			HttpResponse respuesta = cliente.execute(peticion);
			HttpEntity entityRespuesta = respuesta.getEntity();
			/*
			 * Abrimos un InputStream para leer la respuesta y poder crear asi
			 * un objeto JSONObject
			 */
			InputStream is = entityRespuesta.getContent();
			String resultado = "";
			JSONObject json = null;
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder builder = new StringBuilder();
			/*
			 * Leemos el contenido linea a linea
			 */
			String linea = null;
			while ((linea = reader.readLine()) != null) {
				builder.append(linea + "\n");
			}
			// cerramos el InputStream
			is.close();
			// Creamos el objeto json con el resultado
			resultado = builder.toString();
			json = new JSONObject(resultado);
			/*
			 * Dado que el objeto devuelto es un array debemos extraer la
			 * informacion de los diferentes objetos contenidos en el.
			 * Llamaremos a una funcion para ello
			 */
			return parseJSONTerremotos(json.getJSONArray("features"));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private EarthQuake parseJSONTerremotos(JSONArray jsonArray)
			throws JSONException {
		// Inicializamos el ArrayList de personas
		/*
		 * Con un for vamos recuperando los elementos del jsonArray y los
		 * transformamos a personas
		 */
		EarthQuake q = new EarthQuake();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject p = jsonArray.getJSONObject(i).getJSONObject(
					"properties");
			JSONArray l = jsonArray.getJSONObject(i).getJSONObject("geometry")
					.getJSONArray("coordinates");
			q.setIdStr(p.getString("id_str"));
			q.setPlace(p.getString("place"));
			q.setTime(p.getLong("time"));
			q.setDetail(p.getString("detail"));
			q.setMagnitude(p.getInt("mag"));
			q.setUrl(p.getString("url"));

			q.setLat(l.getLong(0));
			q.setLong(l.getLong(1));
			
			long id = bd.insert(q);
			if(id != -1) {
				Log.d("TAG", "INSERTED: " + q);
				q.setId((int)id);
			}

			// e.setCreated(jsonArray.getJSONObject(i).getInt("created_at"));
			// e.setUpdated(jsonArray.getJSONObject(i).getInt("updated_at"));

		}
		
		return q;
	}

}