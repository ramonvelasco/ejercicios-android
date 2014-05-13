package com.ramonvelasco.intent_image;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	EditText texto;
	TextView titulo;
	Button form, camera, contactos;
	ImageView imageView;
	private File ruta;
	private static int CAMARA = 1;
	private static int FORM = 2;
	private static int CONTACTOS = 3;
	private Uri uriContact;
	private String contactID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		texto = (EditText) findViewById(R.id.editText1);
		titulo = (TextView) findViewById(R.id.textView1);
		form = (Button) findViewById(R.id.button1);
		camera = (Button) findViewById(R.id.button2);
		contactos = (Button) findViewById(R.id.button3);
		imageView = (ImageView) findViewById(R.id.imageView1);

		// cargarDesdeRecurso();

		form.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				enviar();
			}
		});
		camera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Camara de fotos");
				camara();
			}
		});
		contactos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Contactos");
				contactos();
			}
		});

	}

	protected void camara() {
		/*
		 * Definimos la ruta donde guardaremos la imagen y obtenemos de dicha
		 * ruta una uri que debemos pasar como extra al Intent para que la
		 * imagen tomada de la camara se guarde en esa ubicacion. Comprobamos
		 * tambien que la memoria externa esta montada y disponible
		 */
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File directorio = new File(
					Environment.getExternalStorageDirectory() + "/misimagenes");
			// Si el directorio no existe lo creamos
			if (directorio.exists() == false) {
				directorio.mkdirs();
			}
			ruta = new File(directorio, "imagen1.jpg");
			// Obtenemos la uri
			Uri uriImagen = Uri.fromFile(ruta);
			// Creamos el Intent para llamar a la camara
			Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			i.putExtra(MediaStore.EXTRA_OUTPUT, uriImagen);
			/*
			 * En este caso lanzamos en Intent mediante un
			 * startActivityForResult para que se pueda proceder a realizar la
			 * tarea correspondiente una vez se haya tomado la foto. Debemos
			 * pasarle un codigo de identificacion de la peticion
			 */
			startActivityForResult(i, 1);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		 * Comprobamos de que Activity se esta volviendo mediante el uso del
		 * requestCode. En caso de que la peticion venga desde la camara hemos
		 * indicado que este codigo sera 1
		 */
		if (requestCode == CAMARA && resultCode == RESULT_OK) {
			/*
			 * Como disponemos de la ruta de la imagen que se ha tomado
			 * utilizamos esta ruta para cargar la imagen en el imageView
			 */
			// Uri uriImagen = Uri.fromFile(ruta);
			// imageView.setImageURI(uriImagen);

			Bitmap imagen = BitmapFactory.decodeFile(ruta.getAbsolutePath());
			Bitmap escalado = Bitmap.createScaledBitmap(imagen,
					imageView.getWidth(), imageView.getHeight(), false);
			imageView.setImageBitmap(escalado);
			/*
			 * Para que la imagen aparezca en la galeria debemos utilizar un
			 * MediaScannerConnectionClient
			 */
			new MediaScannerConnectionClient() {
				private MediaScannerConnection msc = null;
				{
					msc = new MediaScannerConnection(getApplicationContext(),
							this);
					msc.connect();
				}

				public void onMediaScannerConnected() {
					msc.scanFile(ruta.getAbsolutePath(), null);
				}

				public void onScanCompleted(String path, Uri uri) {
					msc.disconnect();
				}
			};
		} else if (requestCode == FORM && resultCode == RESULT_OK) {

			String text = data.getStringExtra("TEXT");
			titulo.setText(text);
			System.out.println(titulo);

		} else if (requestCode == CONTACTOS && resultCode == RESULT_OK) {
			uriContact = data.getData();

			retrieveContactName();
			retrieveContactNumber();
			retrieveContactPhoto();

		}
	}

	private void retrieveContactNumber() {

		String contactNumber = null;

		// getting contacts ID
		Cursor cursorID = getContentResolver().query(uriContact,
				new String[] { ContactsContract.Contacts._ID }, null, null,
				null);

		if (cursorID.moveToFirst()) {

			contactID = cursorID.getString(cursorID
					.getColumnIndex(ContactsContract.Contacts._ID));
		}

		cursorID.close();

		// Using the contact ID now we will get contact phone number
		Cursor cursorPhone = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },

				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
						+ ContactsContract.CommonDataKinds.Phone.TYPE + " = "
						+ ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
				new String[] { contactID }, null);

		if (cursorPhone.moveToFirst()) {
			contactNumber = cursorPhone
					.getString(cursorPhone
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		}
		titulo.append("   " + contactNumber);
		cursorPhone.close();

	}

	private void retrieveContactName() {

		String contactName = null;

		// querying contact data store
		Cursor cursor = getContentResolver().query(uriContact, null, null,
				null, null);

		if (cursor.moveToFirst()) {

			// DISPLAY_NAME = The display name for the contact.
			// HAS_PHONE_NUMBER = An indicator of whether this contact has at
			// least one phone number.

			contactName = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		}
		titulo.setText(contactName);
		cursor.close();

	}

	private void retrieveContactPhoto() {

		Bitmap photo = null;

		try {
			InputStream inputStream = ContactsContract.Contacts
					.openContactPhotoInputStream(getContentResolver(),
							ContentUris.withAppendedId(
									ContactsContract.Contacts.CONTENT_URI,
									Long.valueOf(contactID)),true);

			if (inputStream != null) {
				photo = BitmapFactory.decodeStream(inputStream);
				ImageView imageView = (ImageView) findViewById(R.id.imageView1);
				imageView.setImageBitmap(photo);
			}

			assert inputStream != null;
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void enviar() {
		/*
		 * Al pulsar el boton recogemos el valor del EditText
		 */
		String nombre = texto.getText().toString();
		/*
		 * Para pasar valores entre Activities debemos utilizar un Bundle. Este
		 * Bundle tiene metodos put para poner parejas clave valor
		 */
		Bundle datos = new Bundle();
		datos.putString("texto", nombre);
		/*
		 * Una vez tengamos el Bundle lo debemos asociar a un Intent que sera el
		 * que llame al otro Activity. En este tipo de Intent debemos pasarle
		 * una referencia al contexto actual (Este Activity) y a la clase del
		 * Activity que sera llamada
		 */
		Intent i = new Intent(this, SegundaActivity.class);
		i.putExtras(datos);
		// Por ultimo solo nos queda lanzar la peticion
		startActivityForResult(i, FORM);
	}

	private void contactos() {

		Uri uri = Uri.parse("content://contacts/people");
		Intent intent = new Intent(Intent.ACTION_PICK, uri);
		startActivityForResult(intent, 3);
	}

}
