package cs213.photoalbum;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    ArrayList<Album> albums=new ArrayList<Album>();

    Menu menu;

    int selectedAlbumIndex;

    int selectedImageIndex;

    String temp;

    int state;

    ViewGroup.LayoutParams txtlp;

    ViewGroup.LayoutParams imglp;

    LinearLayout.LayoutParams ll2lp;

    // Storage Permissions
    static final int REQUEST_EXTERNAL_STORAGE = 1;
    static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        //PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
        albums= loadSharedPreferencesLogList(this);
        setContentView(R.layout.activity_main);
        state=R.id.group_album;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtlp=findViewById(R.id.textView).getLayoutParams();
        imglp=findViewById(R.id.imageView).getLayoutParams();
        ll2lp= (LinearLayout.LayoutParams) findViewById(R.id.albumrow).getLayoutParams();

        albumviewupdate();
    }

    public void albumviewupdate(){
        LinearLayout ll= (LinearLayout) findViewById(R.id.albumlist);

        ll.removeAllViewsInLayout();

        for(int i=0;i<albums.size();i++){
            final LinearLayout newalbum=new LinearLayout(this);
            newalbum.setLayoutParams(ll2lp);
            ImageView img=new ImageView(this);
            img.setLayoutParams(imglp);
            img.setImageResource(R.drawable.srk);
            img.setTag("Image");

            TextView title=new TextView(this);
            title.setLayoutParams(txtlp);
            title.setText(albums.get(i).getAlbum_name());
            title.setTag("Title");

            newalbum.addView(img);
            newalbum.addView(title);
            newalbum.setTag("New Album");
            final int finalI = i;
            newalbum.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Snackbar.make(view, "New Album clicked", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    TextView titleview = (TextView) newalbum.findViewWithTag("Title");
                    Snackbar.make(view, "Selected Album: "+titleview.getText().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selectedAlbumIndex= finalI;
                    return true;
                }
            });
            newalbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "New Album clicked", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    TextView titleview = (TextView) newalbum.findViewWithTag("Title");
                    Snackbar.make(view, "Selected Album: "+titleview.getText().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selectedAlbumIndex= finalI;
                    gotoImages();
                }
            });
            ll.addView(newalbum);
        }
    }

    @Override
    protected void onDestroy() {
        saveSharedPreferencesLogList(this,albums);
        super.onDestroy();
    }

    public void imageviewupdate(int album_index){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Going to Albums", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                gotoAlbums();
            }
        });
        LinearLayout ll= (LinearLayout) findViewById(R.id.imagelist);

        ll.removeAllViewsInLayout();

        ArrayList<Image> images = albums.get(album_index).getImages();

        for(int i=0;i<images.size();i++){
            final LinearLayout newimage=new LinearLayout(this);
            newimage.setLayoutParams(ll2lp);
            ImageView img=new ImageView(this);
            img.setLayoutParams(imglp);
            img.setImageURI(Uri.parse(images.get(i).getImage_uri()));
            img.setTag("Image");

            TextView title=new TextView(this);
            title.setLayoutParams(txtlp);
            title.setText(images.get(i).getImage_name());
            title.setTag("Title");

            newimage.addView(img);
            newimage.addView(title);
            newimage.setTag("New Image");
            final int finalI = i;
            newimage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Snackbar.make(view, "Image clicked", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    TextView titleview = (TextView) newimage.findViewWithTag("Title");
                    Snackbar.make(view, "Selected Image: "+titleview.getText().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    selectedImageIndex= finalI;
                    return true;
                }
            });
            newimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedImageIndex= finalI;
                }
            });
            ll.addView(newimage);
        }
    }

    public void gotoImages(){
        setContentView(R.layout.photoview);
        state=R.id.group_photo;
        //Populate
        imageviewupdate(selectedAlbumIndex);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Going to Albums", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                gotoAlbums();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(state==R.id.group_album) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            menu.setGroupVisible(R.id.group_album, true);
            menu.setGroupVisible(R.id.group_photo, false);
            this.menu = menu;
        }
        else if(state==R.id.group_photo){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            menu.setGroupVisible(R.id.group_album, false);
            menu.setGroupVisible(R.id.group_photo, true);
            this.menu = menu;
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_album) {
            setContentView(R.layout.addalbum);
            return true;
        }
        else if (id == R.id.delete_album) {
            deletealbum();
            return true;
        }
        else if (id == R.id.rename_album) {
            setContentView(R.layout.renamealbum);
            EditText et= (EditText) findViewById(R.id.rename_album_name);
            et.setText(albums.get(selectedAlbumIndex).getAlbum_name());
            return true;
        }
        else if (id == R.id.add_photo) {
            setContentView(R.layout.addimage);
            return true;
        }
        else if(id == R.id.delete_photo){
            deletephoto();
            return true;
        } else if(id == R.id.edit_photo){
            setContentView(R.layout.editimage);
            EditText et= (EditText) findViewById(R.id.edit_image_name);
            EditText et2= (EditText) findViewById(R.id.edit_image_tags);
            et.setText(albums.get(selectedAlbumIndex).getImages().get(selectedImageIndex).getImage_name());
            HashMap<String,String> hm=albums.get(selectedAlbumIndex).getImages().get(selectedImageIndex).getImage_tags();
            String tags=hm.toString().replaceAll("/,\\s*/",",");
            tags=tags.replaceAll("=",":");
            tags=tags.substring(1,tags.length()-1);
            et2.setText(tags);
            ImageView imgview= (ImageView) findViewById(R.id.imgView);
            imgview.setImageURI(Uri.parse(albums.get(selectedAlbumIndex).getImages().get(selectedImageIndex).getImage_uri()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotoAlbums(){
        state=R.id.group_album;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        albumviewupdate();
    }

    public void addnewalbum(View v){
        EditText et= (EditText) findViewById(R.id.new_album_name);
        albums.add(new Album(et.getText().toString(),"",new ArrayList<Image>()));
        gotoAlbums();
    }

    public void deletealbum(){
        albums.remove(selectedAlbumIndex);
        albumviewupdate();
    }

    public void renamealbum(View v){
        EditText et= (EditText) findViewById(R.id.rename_album_name);
        albums.get(selectedAlbumIndex).setAlbum_name(et.getText().toString());
        gotoAlbums();
    }

    private static int RESULT_LOAD_IMG = 1;
    //String imgDecodableString;

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();

                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                imgView.setImageURI(Uri.parse(getRealPathFromURI(selectedImage)));
                temp=getRealPathFromURI(selectedImage);
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                this,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void addnewphoto(View view){
        Image newimg=new Image();
        newimg.setImage_uri(temp);
        EditText et= (EditText) findViewById(R.id.new_image_name);
        EditText et2= (EditText) findViewById(R.id.new_image_tags);
        newimg.setImage_name(et.getText().toString());
        HashMap<String,String> hm=new HashMap<>();
        String[] split=et2.getText().toString().split(",");
        for(String t:split){
            String[] keyValue=t.split(":");
            hm.put(keyValue[0], keyValue[1]);
        }
        newimg.setImage_tags(hm);

        albums.get(selectedAlbumIndex).getImages().add(newimg);

        gotoImages();
    }

    public void deletephoto(){
        albums.get(selectedAlbumIndex).getImages().remove(selectedImageIndex);
        imageviewupdate(selectedAlbumIndex);
    }

    public void editphoto(View v){
        Image newimg=albums.get(selectedAlbumIndex).getImages().get(selectedImageIndex);
        if(temp!=null) {
            newimg.setImage_uri(temp);
        }
        EditText et= (EditText) findViewById(R.id.edit_image_name);
        EditText et2= (EditText) findViewById(R.id.edit_image_tags);
        newimg.setImage_name(et.getText().toString());
        HashMap<String,String> hm=new HashMap<>();
        String[] split=et2.getText().toString().split(",");
        for(String t:split){
            String[] keyValue=t.split(":");
            hm.put(keyValue[0], keyValue[1]);
        }
        newimg.setImage_tags(hm);

        gotoImages();
    }

    public static void saveSharedPreferencesLogList(Context context, List<Album> albumlist) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(albumlist);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }
    public static ArrayList<Album> loadSharedPreferencesLogList(Context context) {
        ArrayList<Album> albumlist = new ArrayList<Album>();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            albumlist = new ArrayList<Album>();
        } else {
            Type type = new TypeToken<List<Album>>() {
            }.getType();
            albumlist = gson.fromJson(json, type);
        }
        return albumlist;
    }
}

