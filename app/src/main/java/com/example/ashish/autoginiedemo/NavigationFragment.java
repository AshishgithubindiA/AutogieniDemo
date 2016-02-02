package com.example.ashish.autoginiedemo;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends android.support.v4.app.Fragment {

    RecyclerView recyclerView;
    ActionBarDrawerToggle drawerToggle;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_navigation, container, false);
        DrawerLayout drawerLayout= (DrawerLayout) view.findViewById(R.id.drawer_layout);
        recyclerView= (RecyclerView) view.findViewById(R.id.drawer_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new DrawerAdapter(getActivity()));

        return view;
    }



}
class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder>{
    int[] icons={R.drawable.ic_action_search,R.drawable.ic_action_gmail,R.drawable.ic_action_users,R.drawable.ic_action_location};
    String[] title={"Search","Gmail","Profile","Location"};
    LayoutInflater inflater;
    public Context context;
    public DrawerAdapter(Context context){
        inflater=LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.drawer_rv_row,parent,false);
        DrawerViewHolder holder=new DrawerViewHolder(view,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, final int position) {
        holder.icon.setImageResource(icons[position]);
        holder.title.setText(title[position]);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,title[position]+" item selected",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return icons.length;
    }
    static class DrawerViewHolder extends RecyclerView.ViewHolder{
        //CardView cv;
        ImageView icon;
        TextView title;
        public DrawerViewHolder(View itemView,Context context) {
            super(itemView);
            icon= (ImageView) itemView.findViewById(R.id.imageicon);
            title= (TextView) itemView.findViewById(R.id.text_option);

        }

    }
}
