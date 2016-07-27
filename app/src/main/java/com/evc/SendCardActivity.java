package com.evc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evc.models.Card;
import com.evc.models.Company;
import com.evc.models.HistoryEntry;
import com.evc.models.User;
import com.evc.tasks.historytasks.HistoryServiceTask;
import com.evc.tasks.historytasks.ServerAddHistoryTask;
import com.evc.tasks.usertasks.ServerGetAllUsersTask;
import com.evc.tasks.usertasks.UserServiceUsersTask;
import com.evc.transport.NetworkEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khrak on 7/26/16.
 */
public class SendCardActivity extends AppCompatActivity implements View.OnClickListener, NetworkEventListener{
    private String[] USERS = new String[] {
//            "Mamuka Sakhelashvili", "Anna Gorozia", "Anano Bodokia"
    };

    private FloatingActionButton floatingActionButton;
    private ImageView editIcon;
    private ProgressDialog progressDialog;
    private AutoCompleteTextView userField;
    private String cardid;
    private String cardPath;
    private ImageView selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_card);

        Intent intent = getIntent();
        cardid = intent.getStringExtra("card_id");
        cardPath = intent.getStringExtra("card_path");

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        editIcon = (ImageView) findViewById(R.id.edit_icon);

        editIcon.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);


        selectedImage = (ImageView) findViewById(R.id.selected_image);
        Picasso.with(getApplicationContext()).load(cardPath).into(selectedImage);



        userField = (AutoCompleteTextView) findViewById(R.id.enter_user_name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, USERS);

        userField.setAdapter(adapter);

        progressDialog = new ProgressDialog(SendCardActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Refreshing user list...");

        userField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserServiceUsersTask usersTask = new ServerGetAllUsersTask();
                usersTask.setNetworkEventListener(SendCardActivity.this);
                usersTask.execute();
                progressDialog.show();
            }
        });
    }

//    public void showPopup(View v) {
//        PopupMenu popup = new PopupMenu(this, v);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.send_card_menu, popup.getMenu());
//        popup.show();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.send_card_menu, menu);
        return true;
    }


    public void bluetoothClick(View view) {
        System.out.println("bluetooth man!");
//        Intent intent = new Intent(this, BluetoothScanActivity.class);
//        startActivity(intent);
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

    private Map<String, String> map;

    @Override
    public void onAllUsersDownloaded(List<User> users) {
        map = new HashMap<String, String>();
        for (User user : users) {
            map.put(user.getFirstName() + " " + user.getLastName(), user.getid());
        }
        USERS = new String[map.size()];
        USERS = map.keySet().toArray(USERS);
        progressDialog.dismiss();
    }

    @Override
    public void onUserCardsDownloaded(List<Card> cards) {

    }

    @Override
    public void onCardByIdDownloaded(Card card) {

    }

    @Override
    public void onUserCardAdded(String message) {

    }

    @Override
    public void onHistoryAdded(String message) {
        Toast.makeText(MainActivity.getContext(), "Card Sent", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onSentHistoryDownloaded(List<HistoryEntry> histories) {

    }

    @Override
    public void onReceivedHistoryDownloaded(List<HistoryEntry> histories) {

    }

    @Override
    public void onClick(View v) {
        if (v == floatingActionButton) {
            String userId = MainActivity.getUser().getid();
            String receiverId = map != null ? map.get(userField.getText().toString()) : "1";
            HistoryServiceTask historyServiceTask = new ServerAddHistoryTask();
            historyServiceTask.setNetworkEventListener(this);
            String[] taskParams = {userId, receiverId, cardid};
            historyServiceTask.execute(taskParams);
            progressDialog.setMessage("Sending Card...");
            progressDialog.show();
        }

        if(v == editIcon) {
            System.out.println("bluetooth clicked");

            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.test_linear);

            TextView viewToBeConverted = (TextView) findViewById(R.id.first_name);
            linearLayout.setDrawingCacheEnabled(true);

            //Toast.makeText(MainActivity.this, file.toString(), Toast.LENGTH_LONG).show();

            File file = new File(getFilesDir() + "/","sample.png");

            System.out.println(file.getAbsolutePath());

            linearLayout.setDrawingCacheEnabled(true);
            linearLayout.buildDrawingCache();


            Bitmap bm = linearLayout.getDrawingCache();

            System.out.println(bm);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            viewToBeConverted.setText("olala");
            viewToBeConverted.setDrawingCacheEnabled(true);


            Bitmap viewBitmap = linearLayout.getDrawingCache();


            linearLayout.setVisibility(View.VISIBLE);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            viewBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object

            byte[] b = baos.toByteArray();

            try
            {

                out.write(b);
                out.flush();
                out.close();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file) );
                startActivity(intent);
            }
            catch (Exception e)
            {

            }
        }
    }
}
