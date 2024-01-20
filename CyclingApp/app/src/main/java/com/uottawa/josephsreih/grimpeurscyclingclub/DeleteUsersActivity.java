package com.uottawa.josephsreih.grimpeurscyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteUsersActivity extends AppCompatActivity {
    Admin admin = new Admin();
    DatabaseReference databaseUsers;

    ListView listViewUsers;

    List<User> users;

    Button buttonDeleteUser;
    User selectedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        buttonDeleteUser = (Button) findViewById(R.id.buttonDeleteUser);
        users =  new ArrayList<>();
        selectedUser = null;
        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUser = users.get(i);
                view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                return true;
            }
        });

        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedUser != null) {
                    admin.deleteUser(selectedUser.getId());
                    selectedUser = null;
                    clearSelection();
                }
            }
        });
    }

    private void clearSelection() {
        for (int i = 0; i < listViewUsers.getChildCount(); i++) {
            listViewUsers.getChildAt(i).setBackgroundColor(0);
        }
    }
    protected void onStart() {
        super.onStart();
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }
                UserList productsAdapter = new UserList(DeleteUsersActivity.this, users);
                listViewUsers.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
