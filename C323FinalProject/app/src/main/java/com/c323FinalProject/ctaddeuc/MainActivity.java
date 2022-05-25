package com.c323FinalProject.ctaddeuc;

//C323 Final Project
//Project 2
//Chris Taddeucci


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView profileImageView;
    EditText nameEditText, emailEditText;
    Button signButton;
    ArrayList<ArrayList<String>> users = new ArrayList<>(); //holds current user in db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImageView = findViewById(R.id.profileImageView);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        signButton = findViewById(R.id.signButton);


        profileImageView.setImageResource(R.mipmap.placeholder_img);
//        profileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //https://developer.android.com/guide/components/intents-common
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//
//                if(intent.resolveActivity(getPackageManager()) != null){
//
//                    startActivityForResult(intent, 1);
//                }
//
//            }
//        });

    }

    //https://developer.android.com/guide/components/intents-common
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //Bitmap thumbnail = data.getParcelable("data");
            Uri fullPhotoUri = data.getData();
            //profileImageView.setImageURI(fullPhotoUri);
            Toast.makeText(this, "image clicked!", Toast.LENGTH_LONG).show();

        }
    }

    public void verifyInfo(View view) {
        //view.getContext().deleteDatabase("reviews.db");
        if(nameEditText.getText().toString() != null && emailEditText.getText().toString() != null){
            //get users from DB
            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            //dbHandler.dropFavorite();

//            //String convertedImg = ImageUtil.convert(profileImageView.getDrawingCache());
//            String convertedImg = "";
//            User user = new User("test@test.com", "test", convertedImg);
//            dbHandler.addUserDB(user);
            Cursor res = dbHandler.getUsers();

            int i = 0;
            while(res.getCount() > 0 && res.moveToNext()){
                users.add(new ArrayList<>());
                users.get(i).add(res.getString(1)); //email
                users.get(i).add(res.getString(2)); //name
                users.get(i).add(res.getString(3)); //image
                i++;
                Log.i("user", "user add " + res.getString(1));
                //Toast.makeText(this, "user added :" + users.get(res.getString(1)), Toast.LENGTH_SHORT).show();
            }

        }else {
            //Toast.makeText(this, "Please provide both an email and name", Toast.LENGTH_LONG).show();
        }

        //verify login info if it already exists (i.e. the name is different, but the email has been used) (email must be unique to the system)
        //request correct info
        for(int j=0; j < users.size(); j++){

            if(emailEditText.getText().toString().isEmpty() == true && nameEditText.getText().toString().isEmpty() == true){
                //Toast.makeText(this, "Please provide an email and name", Toast.LENGTH_SHORT).show();
            }else if(emailEditText.getText().toString().equalsIgnoreCase(users.get(j).get(0)) && nameEditText.getText().toString().equalsIgnoreCase(users.get(j).get(1))) {
                //if email and username are found, the user is signed in
                Intent intent = new Intent(MainActivity.this, NavigationDrawer.class);
                String email = emailEditText.getText().toString();
                String name = nameEditText.getText().toString();
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                startActivity(intent);
               // Toast.makeText(this, "User found", Toast.LENGTH_SHORT).show();
            }else if(emailEditText.getText().toString().equalsIgnoreCase(users.get(j).get(0)) && nameEditText.getText().toString().equalsIgnoreCase(users.get(j).get(1)) == false) {
                //Toast.makeText(this, "Email is associated with another account", Toast.LENGTH_SHORT).show();
                emailEditText.setText("");
                nameEditText.setText("");
            }else if(emailEditText.getText().toString().equalsIgnoreCase(users.get(j).get(0)) == false && nameEditText.getText().toString().equalsIgnoreCase(users.get(j).get(1)) == false) {

                //String convertedImg = ImageUtil.convert(profileImageView.getDrawingCache());
                String convertedImg = "";
                User user = new User(emailEditText.getText().toString(), nameEditText.getText().toString(), convertedImg);
                DBHandler dbHandler = new DBHandler(this, null, null, 1);
                dbHandler.addUserDB(user);

                //Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
                emailEditText.setText("");
                nameEditText.setText("");
            }
        }
    }
}