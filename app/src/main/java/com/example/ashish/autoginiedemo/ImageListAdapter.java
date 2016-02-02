package com.example.ashish.autoginiedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish on 2/1/2016.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {
    LayoutInflater inflater;
    List<ImageInfo> infos=new ArrayList<ImageInfo>();
    ImageLoader imageLoader;
    public ImageListAdapter(Context context,List<ImageInfo> infos){
        inflater=LayoutInflater.from(context);
        this.infos=infos;
        imageLoader=VolleySingleton.getInstence().getImageLoader();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=inflater.inflate(R.layout.custom_row, viewGroup, false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        holder.title.setText(infos.get(i).getTitle());
        holder.image.setImageUrl(infos.get(i).getUrl(),imageLoader);
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder{
        //CardView cv;
        NetworkImageView image;
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            image= (NetworkImageView) itemView.findViewById(R.id.imageview);
            title= (TextView) itemView.findViewById(R.id.textView);

        }
    }
}
