package com.rsmouki.zed.tp3.GererItem;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.rsmouki.zed.tp3.MainActivity;
import com.rsmouki.zed.tp3.R;
import com.rsmouki.zed.tp3.SettingsActivity;

public class Apropos extends AppCompatActivity {

    private static final String TAG = "apropos d'un etabl" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);
        Log.d(TAG, "on create");
        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "onClick: navigating to settings.");
                /*setResult(2);
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);*/
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
