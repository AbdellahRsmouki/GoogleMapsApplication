package com.rsmouki.zed.tp3;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
        private List<ItemData> itemsData;

        public myAdapter(List<ItemData> itemsData) {
            this.itemsData = itemsData;
        }
        // Create new views (invoked by the layout manager)
        @Override
        public myAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.img_row, null);

            // create ViewHolder
            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            // - get data from your itemsData at this position
            // - replace the contents of the view with that itemsData

            viewHolder.txtViewTitle.setText(itemsData.get(position).item_title);
            viewHolder.imgViewIcon.setImageBitmap(itemsData.get(position).item_imageUrl);
            viewHolder.txtViewDesc.setText(itemsData.get(position).item_desc);
        }

        // inner class to hold a reference to each item of RecyclerView
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txtViewTitle;
            public ImageView imgViewIcon;
            public TextView txtViewDesc;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.textView);
                imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.imageView);
                txtViewDesc = (TextView) itemLayoutView.findViewById(R.id.text_view_desc);
            }
        }


        // Return the size of your itemsData (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return itemsData.size();
        }

}

