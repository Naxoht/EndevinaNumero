package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText number;
    int tries = 1;
    Random rand = new Random();
    int randomNumber = rand.nextInt(100)+1;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = (EditText) findViewById(R.id.numUser);
        final Button button = findViewById(R.id.comprova);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String numberUser = number.getText().toString();
                int value = Integer.parseInt(numberUser);
                Context context = MainActivity.this;
                CharSequence text = "";

                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Felicidades!");
                alert.setMessage("Has acertado el numero\nIngresa tu nombre para el ranking");

                final EditText input = new EditText(context);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String userName = input.getText().toString();
                        Intent intent = new Intent(context,RankingActivity.class);
                        intent.putExtra("intentos",tries);
                        intent.putExtra("user",userName);
                        tries = 1;
                        randomNumber = rand.nextInt(100)+1;
                        startActivity(intent);


                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        tries = 1;
                        randomNumber = rand.nextInt(100)+1;
                    }
                });

                int duration = Toast.LENGTH_SHORT;

                if ( randomNumber > value) {
                    text = "El numero que buscas es mayor al insertado.";
                    tries++;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    number.setText("");
                }if ( randomNumber < value) {
                    text = "El numero que buscas es menor al insertado.";
                    tries++;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    number.setText("");
                }if (randomNumber == value){
                    alert.show();
                }

            }
        });
    }




}
