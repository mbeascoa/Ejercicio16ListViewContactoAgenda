package com.example.ejercicio16listviewcontactoagenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //  Contacto contacto = new Contacto();  si lo creo aqui en el MainActivity entonces siempre me crea el mismo contacto
    List<Contacto> contactos = new ArrayList<Contacto>();

    //  ArrayList<Contacto> lista = new ArrayList()    esta es la linea del profesor


    private TextView txtllamadas;
    private ListView lsv1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtllamadas = (TextView) findViewById(R.id.lblNumLlamadas);
        this.lsv1 = (ListView) findViewById((R.id.lvContactos));

        this.lsv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //lo ponemos aqui porque si lo ponemos en el boton cada vez que le llamamos lo cargamos
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Contacto c = (Contacto) lsv1.getItemAtPosition(position);
                txtllamadas.setText( "Al contacto " + lsv1.getItemAtPosition(position).toString() + " le he realizado estas " + c.getLlamadas()+ " llamadas. " );
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


    }




    public void leerContactos(View view) {


        String sColumnas[] = { ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.TIMES_CONTACTED };
        Cursor cursorContactos = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, sColumnas, null, null,
                ContactsContract.Contacts.DISPLAY_NAME);

        cursorContactos.moveToFirst();


        while (!cursorContactos.isAfterLast()) {
            String nombrecontacto = cursorContactos.getString(cursorContactos
                    .getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
            int numerollamadas = cursorContactos.getInt(cursorContactos
                    .getColumnIndexOrThrow(ContactsContract.Contacts.TIMES_CONTACTED));

            Contacto contacto = new Contacto();  //si lo pongo en el main entonces me sale siempre el mismo contacto
            contacto.setNombre(nombrecontacto) ;
            contacto.setLlamadas(numerollamadas);
            this.contactos.add(contacto);

            cursorContactos.moveToNext();
        }
        cursorContactos.close();

        try {
            ArrayAdapter<Contacto> datoscontacto = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);  //le paso un array de contactos y debe coincidir en vez de string

            this.lsv1.setAdapter(datoscontacto);


        }  catch (Exception ex)
        {
            txtllamadas.setText("Error: " + ex);
        }

    }
}