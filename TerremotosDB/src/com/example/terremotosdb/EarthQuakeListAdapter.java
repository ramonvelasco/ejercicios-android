package com.example.terremotosdb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EarthQuakeListAdapter extends BaseAdapter {

	protected Activity activity;
	protected ArrayList<EarthQuake> items;

	public EarthQuakeListAdapter(Activity activity, ArrayList<EarthQuake> items) {
		this.activity = activity;
		this.items = items;
	}

	public int getCount() {
		return items.size();
	}

	public EarthQuake getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return items.get(position).getId();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = inflater.inflate(R.layout.list_item_layout, null);
		}

		EarthQuake earthquake = items.get(position);
		//
		// ImageView image = (ImageView) vi.findViewById(R.id.imageview1);
		// Bitmap bitmap = null;
		// try {
		// bitmap = BitmapFactory.decodeStream((InputStream)new
		// URL(galeria.getPhoto()).getContent());
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// image.setImageBitmap(bitmap);
		//
		// String val=Locale.getDefault().getLanguage();

		TextView place = (TextView) vi.findViewById(R.id.place);
		place.setText(earthquake.getPlace());

		TextView detail = (TextView) vi.findViewById(R.id.detail);
		detail.setText(earthquake.getDetail());
		
		Date date = new Date(earthquake.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fecha = null;
		try {
			fecha = sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextView time = (TextView) vi.findViewById(R.id.fecha);
		time.setText(String.valueOf(fecha));
		
		TextView mag = (TextView) vi.findViewById(R.id.magnitude);
		mag.setText(String.valueOf(earthquake.getMagnitude()));

		TextView coordinates = (TextView) vi.findViewById(R.id.coordinates);
		coordinates.setText("Lat: " + String.valueOf(earthquake.getLat())
				+ "  Long: " + String.valueOf(earthquake.getLong()));

		return vi;
	}

}