package fragments;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rsmouki.zed.tp3.ItemData;
import com.rsmouki.zed.tp3.MapsActivity;
import com.rsmouki.zed.tp3.R;
import com.rsmouki.zed.tp3.db.Etablissement;
import com.rsmouki.zed.tp3.db.MyDatabase;
import com.rsmouki.zed.tp3.myAdapter;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class homepage extends Fragment {

    private  static final String TAG="homepage";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset;
    MyDatabase itemInstance;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        // this is data fro recycler view
        /*ItemData itemsData[] = {new ItemData("ENSIAS clubs : ", R.drawable.ensias_logo),
                new ItemData("club japonais", R.drawable.club_japonais),
                new ItemData("club curan", R.drawable.club_curan),
                new ItemData("club robotics ", R.drawable.club_robotic)};*/


        itemInstance = Room.databaseBuilder(view.getContext(),
                MyDatabase.class, "Etablissement")
                .allowMainThreadQueries()
                .build();
        List<Etablissement> etb = itemInstance.etabDao().getEtablissement();
        final List<ItemData> itemsData = new ArrayList<>();
        //ItemData i = new ItemData("ENSIAS",BitmapFactory.decodeResource(getResources(),R.drawable.ensias_logo),"Our school") ;
        Etablissement e = new Etablissement("ENSIAS",BitmapFactory.decodeResource(getResources(),R.drawable.ensias_logo).toString().getBytes(),"Our school",33.9843281433038,-6.867613792419434) ;
        itemInstance.etabDao().addEtablissement(e);
        //itemsData.add(i);
        for(Etablissement item : etb) {
                byte[] blob = item.image;
                ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ItemData itm = new ItemData(item.nom,bitmap,item.desc);
                itemsData.add(itm);
                Log.d(TAG,itm.getitem_title() + "" + itm.getItem_desc() );
        }
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        myAdapter mAdapter = new myAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new myAdapter(itemsData);
        mRecyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        ItemData i  = itemsData.get(position);
                        Log.d(TAG, "onClick: navigating to maps activity.");
                        Intent intent = new Intent(view.getContext(), MapsActivity.class);
                        intent.putExtra("descr",i.item_desc);
                        intent.putExtra("title",i.item_title);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever => i wont do anything here
                    }
                })
        );


        return view;
    }

}
