package com.example.ashish.autoginiedemo;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ashish on 2/1/2016.
 */
class VolleySingleton {
    private static VolleySingleton vInstance=null;
    private static RequestQueue vRequestQueue;
    private static ImageLoader vImageLoader;
    private VolleySingleton(){
        vRequestQueue= Volley.newRequestQueue(MyApplication.getContext());
        vImageLoader = new ImageLoader(this.vRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }
    public static VolleySingleton getInstence(){
        if(vInstance==null){
            vInstance=new VolleySingleton();
        }
        return vInstance;
    }
    public static RequestQueue getvRequestQueue(){
        return vRequestQueue;
    }
    public ImageLoader getImageLoader(){
        return this.vImageLoader;
    }
}
