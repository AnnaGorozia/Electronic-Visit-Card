package com.evc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import com.evc.models.Card;
import com.evc.models.Company;
import com.evc.models.History;
import com.evc.models.HistoryEntry;
import com.evc.models.User;
import com.evc.tasks.cardtasks.CardServiceTask;
import com.evc.tasks.cardtasks.ServerCardAddedTask;
import com.evc.transport.NetworkEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khrak on 7/26/16.
 */
public class AddCardActivity extends AppCompatActivity implements NetworkEventListener{

    Cloudinary cloudinary;
    Map Result;
    String file_path;
    File file;

    private ProgressDialog progressDialog;
    private static String path;

    RelativeLayout layout;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = this;

        Intent intent = getIntent();

        String template_id = intent.getStringExtra("template_id");

        int id = Integer.parseInt(template_id);


        HashMap config = new HashMap();
        config.put("cloud_name", "dpavqa5hs");
        config.put("api_key", "522248491562654");//I have changed the key and secret
        config.put("api_secret", "M0bj-qjuGXXftQ_vlmGUmVIVXg4");
        cloudinary = new Cloudinary(config);

        switch (id) {
            case 1: {
                setContentView(R.layout.template1);
                layout = (RelativeLayout) findViewById(R.id.template1);
                break;
            }
            case 2: {
                setContentView(R.layout.template2);
                layout = (RelativeLayout) findViewById(R.id.template2);
                break;
            }
            case 3: {
                setContentView(R.layout.template3);
                layout = (RelativeLayout) findViewById(R.id.template3);
                break;
            }
            case 4: {
                setContentView(R.layout.template4);
                layout = (RelativeLayout) findViewById(R.id.template4);
                break;
            }
        }

        progressDialog = new ProgressDialog(AddCardActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Card...");

    }

    public void cardCreated(View view) throws IOException {
        System.out.println("Click");

        file = new File(this.getFilesDir() + "/","sample.png");

        System.out.println(file.getAbsolutePath());

        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap bm = layout.getDrawingCache();

        System.out.println(bm);
        FileOutputStream out = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);

        out.flush();
        out.close();



        Upload task = new Upload( cloudinary );
        task.execute(new String[] { "http://www.freeuni.edu.ge" });
        progressDialog.show();

    }

    @Override
    public void onUserRegistered(String message) {

    }

    @Override
    public void onUserLoggedIn(String message) {

    }

    @Override
    public void onUserObjectReturned(User user) {

    }

    @Override
    public void onUserObjectByMail(User user) {

    }

    @Override
    public void onUserCompanies(List<Company> companies) {

    }

    @Override
    public void onCompanyRegistered(String message) {

    }

    @Override
    public void onUserUpdated(String message) {

    }

    @Override
    public void onAllUsersDownloaded(List<User> users) {

    }

    @Override
    public void onUserCardsDownloaded(List<Card> cards) {

    }

    @Override
    public void onCardByIdDownloaded(Card card) {

    }

    @Override
    public void onUserCardAdded(String message) {
        MainActivity.switchToCardsTab();
        progressDialog.dismiss();
        Toast.makeText(MainActivity.getContext(), "New Cards has been added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onHistoryAdded(String message) {

    }

    @Override
    public void onSentHistoryDownloaded(List<HistoryEntry> histories) {

    }

    @Override
    public void onReceivedHistoryDownloaded(List<HistoryEntry> histories) {

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

//                for(Object key: Result.keySet()) {
//                    System.out.println(key + " " + Result.get(key));
//                }
                path = Result.get("url").toString();



            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            String[] taskParams = {MainActivity.getUser().getid(), path};

            CardServiceTask cardServiceTask = new ServerCardAddedTask();
            cardServiceTask.setNetworkEventListener(AddCardActivity.this);
            cardServiceTask.execute(taskParams);

        }
    }
}
