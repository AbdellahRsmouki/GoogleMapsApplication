package com.rsmouki.zed.tp3.GererItem;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rsmouki.zed.tp3.MainActivity;
import com.rsmouki.zed.tp3.R;
import com.rsmouki.zed.tp3.db.Etablissement;
import com.rsmouki.zed.tp3.db.MyDatabase;

import java.util.List;

import static com.rsmouki.zed.tp3.GererItem.AjouterItem.etablissement;

public class supprimerItem extends AppCompatActivity {

    MyDatabase EInstantce;
    static Etablissement etablissement= new Etablissement();

    EditText etablissementName ;
    Button supprimerEtab ;
    ImageView returnToMain;

    private static final String TAG = "supprimer un etabl" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_item);

        etablissementName = (EditText)findViewById(R.id.input_etab_nom);
        supprimerEtab = (Button)findViewById(R.id.btn_supprimer);



        EInstantce = Room.databaseBuilder(this,
                MyDatabase.class, "Etablissement")
                .allowMainThreadQueries()
                .build();

        supprimerEtab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimer(v);
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

    public void supprimer(View v) {
        Log.d(TAG, "ajouter");

        if (!validate()) {
            return;
        }

        supprimerEtab.setEnabled(false);

        if(!checkDatabase())
        {
            onAddingEtabFailed();
            return;
        }

        Intent intent = new Intent(supprimerItem.this, MainActivity.class);
        startActivity(intent);
        onAddingEtabSuccess();


    }

    public void onAddingEtabSuccess() {
        supprimerEtab.setEnabled(true);
        finish();
    }

    public void onAddingEtabFailed() {
        Toast.makeText(getBaseContext(), "deleting etablissement failed", Toast.LENGTH_LONG).show();

        supprimerEtab.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        etablissement.nom = etablissementName.getText().toString();

        if (etablissement.nom.length() < 4 || etablissement.nom.length() > 30 ) {
            supprimerEtab.setError("enter a valid etablissement name ");
            valid = false;
        } else {
            supprimerEtab.setError(null);
        }
        return valid;
    }


    boolean checkDatabase()
    {
        Log.d(TAG, "checkdatabase");

        List<Etablissement> etab =
                EInstantce.etabDao().getEtablissement(etablissement.nom);
        Log.d(TAG, "etablissementlist");
        for(Etablissement et : etab) {
            Log.d(TAG, et.nom+ et.desc);
            if(etablissement.nom.equals(et.nom))
            {
                Log.d(TAG, "etablissementdeja existe");
                EInstantce.etabDao().deletEtablissement(etablissement);
                return true;
            }

        }
            Toast.makeText(this,"cette etablissement n'existe pas",
                Toast.LENGTH_SHORT).show();
        return false;
    }
}
