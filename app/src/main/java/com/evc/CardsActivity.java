package com.evc;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khrak on 7/25/16.
 */
public class CardsActivity extends AppCompatActivity{
    Cloudinary cloudinary;
    Map Result;
    String file_path;
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template1);


        HashMap config = new HashMap();
        config.put("cloud_name", "dpavqa5hs");
        config.put("api_key", "522248491562654");//I have changed the key and secret
        config.put("api_secret", "M0bj-qjuGXXftQ_vlmGUmVIVXg4");
        cloudinary = new Cloudinary(config);

    }

    public void click(View view) throws IOException {
        System.out.println("clicked!");

        file = new File(this.getFilesDir() + "/","sample.png");

        System.out.println(file.getAbsolutePath());

        RelativeLayout v = (RelativeLayout)findViewById(R.id.template1);
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();

        FileOutputStream out = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);

        out.flush();
        out.close();


        ImageView rl = (ImageView) findViewById(R.id.go);

        rl.setImageBitmap(bm);

        Upload task = new Upload( cloudinary );
        task.execute(new String[] { "http://www.vogella.com" });

    }


    private class Upload extends AsyncTask<String, Void, String> {
        private Cloudinary mCloudinary;

        public Upload( Cloudinary cloudinary ) {
            super();
            mCloudinary = cloudinary;
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            try {

                System.out.println(file.getName());
                Result = mCloudinary.uploader().upload(file,
                        ObjectUtils.emptyMap());

                for(Object key: Result.keySet()) {
                    System.out.println(key + " " + Result.get(key));
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("uploaded");
        }
    }
}
