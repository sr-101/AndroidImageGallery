package cs213.photoalbum;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.R.attr.bitmap;

public class MainActivity extends AppCompatActivity {

    ArrayList<Album> albums=new ArrayList<Album>();

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        albumviewupdate();
        albums.add(new Album("newalbum","srk.jpg",new ArrayList<Image>()));
        albumviewupdate();
    }

    public void albumviewupdate(){
        LinearLayout ll= (LinearLayout) findViewById(R.id.albumlist);

        LinearLayout ll2= (LinearLayout) findViewById(R.id.albumrow);

        for(int i=0;i<albums.size();i++){
            LinearLayout newalbum=new LinearLayout(this);
            newalbum.setLayoutParams(ll2.getLayoutParams());
            ImageView img=new ImageView(this);
            img.setLayoutParams((findViewById(R.id.imageView)).getLayoutParams());
            img.setImageResource(R.drawable.srk);
            TextView title=new TextView(this);
            title.setLayoutParams((findViewById(R.id.textView)).getLayoutParams());
            title.setText(albums.get(i).getAlbum_name());
            newalbum.addView(img);
            newalbum.addView(title);
            ll.addView(newalbum);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu=menu;
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
        } else if (id == R.id.new_image) {
            setContentView(R.layout.addimage);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void addnewalbum(View v){
        EditText et= (EditText) findViewById(R.id.new_album_name);
        albums.add(new Album(et.getText().toString(),"",new ArrayList<Image>()));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        albumviewupdate();
    }

    public void deletealbum(View v){

        /*ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v)
            {
                View delete = findViewById(R.id.textView);
                albums.remove(delete.toString());
                setContentView(R.layout.albumview);
            }
        }

        );*/

       // EditText et= (EditText) findViewById(R.id.new_album_name);
       // albums.add(new Album(et.getText().toString(),"",new ArrayList<Image>()));
        LinearLayout parent=
        for(i=0;i<)
        System.out.println(R.id.albumrow);
        setContentView(R.layout.addimage);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
       // getMenuInflater().inflate(R.menu.menu_main, menu);
       // albumviewupdate();
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
                // Set the Image in ImageView after decoding the String
                imgView.setImageURI(selectedImage);
                System.out.println(imgView.getScaleType().name());

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


/*
  private String saveToInternalStorage(String album_name,String image_name){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      InputStream is = classloader.getResourceAsStream("/srk.jpg");

        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("Images", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }*/

}

