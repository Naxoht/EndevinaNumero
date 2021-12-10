package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText number;
    int tries;
    Random rand = new Random();
    Intent intent;
    String userName;
    Context context = MainActivity.this;
    int duration = Toast.LENGTH_SHORT;
    CharSequence text = "";
    int randomNumber = rand.nextInt(100)+1;
    File image = null;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tries = 0;
        number = (EditText) findViewById(R.id.numUser);
        final Button button = findViewById(R.id.comprova);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String numberUser = number.getText().toString();
                    int value = Integer.parseInt(numberUser);

                    if ( value > 100 || value < 1 ) {
                        text = "El numero tiene que estar entre 1 y 100";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }else {
                        if (randomNumber > value) {
                            text = "El numero que buscas es mayor al insertado.";
                            tries++;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            number.setText("");
                        }
                        if (randomNumber < value) {
                            text = "El numero que buscas es menor al insertado.";
                            tries++;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            number.setText("");
                        }
                        if (randomNumber == value) {
                            tries++;
                            intent = new Intent(context,RankingActivity.class);

                            callDialog(intent);
                            randomNumber = rand.nextInt(100)+1;
                        }
                    }
                }catch (Exception e){
                    text = "El campo del numero no puede estar vacio";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }
        });
    }

    private void callDialog(Intent intent){

        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Felicidades!");
        alert.setMessage("Has acertado el numero en "+tries+" intentos\nIngresa tu nombre para el ranking");

        final EditText input = new EditText(context);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                userName = input.getText().toString();
                if(!userName.isEmpty()){
                    try {
                        camera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(context,"No se puede elegir un nombre vacio",duration).show();
                    callDialog(intent);
                }
//                intent.putExtra("intentos",tries);
//                intent.putExtra("user",userName);
//                tries = 1;
//                randomNumber = rand.nextInt(100)+1;
//                startActivity(intent);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                tries = 0;
                randomNumber = rand.nextInt(100)+1;
            }
        });
        alert.show();
    }

    private File createImageFile() throws IOException {
        String timeStamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void camera() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = createImageFile();

        Uri photoURI = FileProvider.getUriForFile(this,
                "com.endevinaNumero.android.fileprovider",
                photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intent.putExtra("intentos",(tries));
        intent.putExtra("user",userName);
        intent.putExtra("image",image.toString());
        tries = 0;
        startActivity(intent);
    }

}
