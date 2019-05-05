package com.rsmouki.zed.tp3;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rsmouki.zed.tp3.GererItem.AjouterItem;
import com.rsmouki.zed.tp3.GererItem.Apropos;
import com.rsmouki.zed.tp3.GererItem.ModifierItem;
import com.rsmouki.zed.tp3.GererItem.supprimerItem;
import com.rsmouki.zed.tp3.log.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN page";

    ViewPager viewPager1;
    TabLayout tabLayout1;
    TabAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate tablayout.");

        setupTablayout();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // On peut créer le menu via le code
            case R.id.item1:
                Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onClick: navigating to settings.");
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item2:
                Toast.makeText(this, "Ajouter un etablissement",
                        Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(MainActivity.this, AjouterItem.class);
                startActivity(intent2);
                finish();
                 break;
            case R.id.item3:
                Toast.makeText(this, "supprimer un etablissement",
                        Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(MainActivity.this, supprimerItem.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.item4:
                Toast.makeText(this, "modifer un etablissement",
                        Toast.LENGTH_LONG).show();
                Intent intent4 = new Intent(MainActivity.this, ModifierItem.class);
                startActivity(intent4);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTablayout()
    {
        tabLayout1 = findViewById(R.id.tablayout1);
        viewPager1 = findViewById(R.id.viewpager1);

        tabLayout1.addTab(tabLayout1.newTab().setText("welcome"));
        tabLayout1.addTab(tabLayout1.newTab().setText("home"));
        tabLayout1.setTabGravity(TabLayout.GRAVITY_FILL);


        adapter = new TabAdapter(getSupportFragmentManager(), tabLayout1.getTabCount());
        viewPager1.setAdapter(adapter);
        viewPager1.setOffscreenPageLimit(2);
        viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout1));
        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager1.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
