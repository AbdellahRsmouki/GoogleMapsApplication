package com.rsmouki.zed.tp3;

import android.graphics.Bitmap;

public class ItemData {

    public String item_title;
    public String item_desc;
    public Bitmap item_imageUrl;

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }



        public ItemData(String item_title,Bitmap item_imageUrl,String item_desc){

            this.item_title = item_title;
            this.item_imageUrl = item_imageUrl;
            this.item_desc = item_desc;
        }

        public String getitem_title() {
            return item_title;
        }

        public void setitem_title(String item_title) {
            this.item_title = item_title;
        }

        public Bitmap getitem_imageUrl() {
            return item_imageUrl;
        }

        public void setitem_imageUrl(Bitmap item_imageUrl) {
            this.item_imageUrl = item_imageUrl;
        }
}

