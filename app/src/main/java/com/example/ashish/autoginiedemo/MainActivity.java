package com.example.ashish.autoginiedemo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NetworkImageView imageView;
    List<ImageInfo> imageInfos=new ArrayList<ImageInfo>();
    RecyclerView recyclerView;
    ProgressDialog dialog;
    String ONE_TAG="fag one";
    String TWO_TAG="fag two";
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBarDrawerToggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        showFaB();

        prepareDialog();

        fatchRequest();

        imageView= (NetworkImageView) findViewById(R.id.imageview);



       //showImage();





    }
    public void prepareDialog(){
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Network Data Loading..");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
    }
    public void showFaB(){
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_action_add);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.ic_image_brightness_1)
                .build();
        ImageView option_one = new ImageView(this);
        option_one.setImageResource(R.drawable.one);
        ImageView option_two = new ImageView(this);
        option_two.setImageResource(R.drawable.two);


        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton button1 = itemBuilder.setContentView(option_one).build();
        SubActionButton button2 = itemBuilder.setContentView(option_two).build();
        button1.setTag(ONE_TAG);
        button2.setTag(TWO_TAG);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .attachTo(actionButton)
                .build();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void setRecyclerView(){
        recyclerView= (RecyclerView) findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ImageListAdapter(getApplicationContext(), imageInfos));
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)

    public void fatchRequest(){

        RequestQueue requestQueue = VolleySingleton.getInstence().getvRequestQueue();



        String retriveUrl="https://api.gettyimages.com:443/v3/search/images/editorial?orientations=Horizontal&phrase=nature&sort_order=most_popular";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, retriveUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {

                //Toast.makeText(getApplicationContext(), "Response : " + jsonObject, Toast.LENGTH_LONG).show();
                try {
                    JSONArray images=jsonObject.getJSONArray("images");

                    for(int i=0;i<=15;i++){
                        JSONObject image=images.getJSONObject(i);

                        String uri=image.getJSONArray("display_sizes").getJSONObject(0).getString("uri");
                        String title=image.getString("title");
                        ImageInfo imageInfo=new ImageInfo(title,uri);
                        imageInfos.add(imageInfo);
                    }
                    setRecyclerView();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "error2 : " + volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Api-Key","bwhf3kakaau7e7ytk4a7m5rv");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_sort) {

            Collections.sort(imageInfos,new ObjectComparator());
            setRecyclerView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class ObjectComparator implements Comparator<ImageInfo> {


        @Override
        public int compare(ImageInfo lhs, ImageInfo rhs) {

            //if(lhs!=null){Toast.makeText(getApplicationContext(),"yo...",Toast.LENGTH_LONG).show();}
            //if(rhs!=null){Toast.makeText(getApplicationContext(),"yo yo...",Toast.LENGTH_LONG).show();}

            String one = lhs.getTitle();
            String two=rhs.getTitle();

            return one.compareTo(two);


        }
    }

    @Override
    public void onClick(View v) {
        if(v.getTag().equals(ONE_TAG)){
            Intent intent=new Intent(this,ContactActivity.class);
            startActivity(intent);
        }
        if(v.getTag().equals(TWO_TAG)){
            Intent intent=new Intent(this,ContactActivity.class);
            startActivity(intent);
        }

    }
}
