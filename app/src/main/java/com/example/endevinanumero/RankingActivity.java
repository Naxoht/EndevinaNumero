package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    static ArrayList<Integer> usersTries = new ArrayList<Integer>();
    static ArrayList<String> users = new ArrayList<String>();
    static ArrayList<Uri> userImage = new ArrayList<Uri>();
    // Model: Record (intents=puntuació, nom)
    class Record {
        public int intents;
        public String nom;
        public Uri foto;

        public Record(String _nom, Uri uri,int _intents ) {
            intents = _intents;
            nom = _nom;
            foto = uri;

        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        users.add(getIntent().getStringExtra("user"));
        usersTries.add((getIntent().getIntExtra("intentos",1)));
        userImage.add(Uri.fromFile(new File(getIntent().getStringExtra("image"))));
        int i, j, auxTries;
        for (i = 0; i < usersTries.size() - 1; i++) {
            for (j = 0; j < usersTries.size()-i- 1; j++) {
                if (usersTries.get(j + 1) < usersTries.get(j)) {
                    auxTries = usersTries.get(j + 1);
                    usersTries.set(j + 1,usersTries.get(j));
                    System.out.println(j);
                    usersTries.set(j,auxTries);

                    String auxUsers = users.get(j + 1);
                    users.set(j + 1,users.get(j));
                    users.set(j,auxUsers);

                    Uri auxUri = userImage.get(j + 1);
                    userImage.set(j + 1,userImage.get(j));
                    userImage.set(j,auxUri);
                }
            }
        }
        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        for(int z = 0; z < users.size() ; z++){
            records.add(new Record(users.get(z),userImage.get(z),usersTries.get(z)));
        }


        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageURI(getItem(pos).foto);

                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);
    }
    protected File getFile(){
        File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = new File(path,"imatge.jpg");
        return photo;
    }

}
