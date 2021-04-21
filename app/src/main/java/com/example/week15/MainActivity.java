package com.example.week15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    EditText nameEt;
    EditText idET;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        nameEt = findViewById(R.id.name_ET);
        idET = findViewById(R.id.id_et);
        database = FirebaseDatabase.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();



    }

    public void addData(View view) {


        String userName = nameEt.getText().toString();
        String userId = idET.getText().toString();

        DatabaseReference myRef = database.getReference("users");

        User user = new User(userId, userName,userName + "@yahoo.com");



        myRef.child(userId).setValue(user);

    }

    public void getData(View view) {

        DatabaseReference myRef = database.getReference();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                List<User> list = new ArrayList<>();


                for( DataSnapshot c : dataSnapshot.getChildren()) {
                    User user = c.getValue(User.class);
                    list.add(user);
                    Log.d("MainActivity", "User name is: " + user.getName());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });
    }

}