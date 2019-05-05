package com.rsmouki.zed.tp3.GererItem;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rsmouki.zed.tp3.MainActivity;
import com.rsmouki.zed.tp3.db.Etablissement;
import com.rsmouki.zed.tp3.db.MyDatabase;
import com.rsmouki.zed.tp3.R;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AjouterItem extends AppCompatActivity {

    EditText etabName ;
    EditText etabDesc ;
    Button addImage ;
    Button addEtab;
    ImageView imageView;
    ImageView returnToMain;

    MyDatabase EInstantce;
    static Etablissement etablissement= new Etablissement();
    private static final int PICK_IMAGE = 100 ;
    private static final int PERMISSION_CODE = 1001 ;
    private static final String TAG = "Ajouter un etabl" ;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_item);

        Log.d(TAG, "on create");

        etabName = (EditText)findViewById(R.id.input_etab_nom_ajout) ;
        etabDesc = (EditText)findViewById(R.id.input_etab_desc);
        addImage = (Button) findViewById(R.id.btn_pick_img);
        addEtab = (Button) findViewById(R.id.btn_ajouter);
        imageView = (ImageView) findViewById(R.id.result_image);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else{
                        openGallery();
                    }
                }else{
                    openGallery();
                }
            }
        });

        EInstantce = Room.databaseBuilder(this,
                MyDatabase.class, "Etablissement")
                .allowMainThreadQueries()
                .build();

        addEtab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouter(v);

            }
        });

        returnToMain = (ImageView)findViewById(R.id.backArrow);
        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void ajouter(View v) {
        Log.d(TAG, "ajouter");

        if (!validate()) {
            return;
        }

        addEtab.setEnabled(false);

        Intent intent = new Intent(getApplicationContext(),GetLocalisation.class);
        intent.putExtra("name",etablissement.nom);
        intent.putExtra("desc",etablissement.desc);
        startActivity(intent);
        finish();
    }

    private void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if((resultCode==RESULT_OK)&&(requestCode==PICK_IMAGE))
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //pick image
                    openGallery();
                }else{
                    Toast.makeText(getBaseContext(), "Permission denied ..", Toast.LENGTH_LONG).show();

                }

            }
        }

    }

    public boolean validate() {
        boolean valid = true;

        etablissement.nom = etabName.getText().toString();
        etablissement.desc = etabDesc.getText().toString();
        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(imageUri);
            byte[] inputData = new byte[0];
            try {
                inputData = getBytes(iStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            etablissement.image = inputData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        if (etablissement.nom.length() < 4 || etablissement.nom.length() > 30 ) {
            etabName.setError("enter a valid etablissement name ");
            valid = false;
        } else {
            etabName.setError(null);
        }

        if ((etablissement.desc.length() < 4 || etablissement.desc.length() > 100)) {
            etabDesc.setError("between 4 and 100 alphanumeric characters");
            valid = false;
        } else {
            etabDesc.setError(null);
        }
        if(etablissement.image==null)
        {
            Log.d(TAG, "image n'existe pas ");
            Toast.makeText(this,"There is no image",
                    Toast.LENGTH_SHORT).show();

            valid = false;
        }
        return valid;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
