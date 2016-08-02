package com.infotech.androidloadimage;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URL;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Button load_img;
    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load_img = (Button)findViewById(R.id.load);
        img = (ImageView)findViewById(R.id.img);
        load_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadImage().execute("https://firebasestorage.googleapis.com/v0/b/mastering-android-application.appspot.com/o/images%2Finfotech.jpg?alt=media&token=703ae6e4-b38d-437d-b6d8-a5dd2aecddd7");
            }
        });
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Bitmap doInBackground(String... args) {
            try{
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
