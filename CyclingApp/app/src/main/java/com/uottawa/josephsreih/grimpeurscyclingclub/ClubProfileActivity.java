package com.uottawa.josephsreih.grimpeurscyclingclub;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityClubProfileBinding;

public class ClubProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseUsers;
    private DatabaseReference databaseProfiles;
    User currentUser;
    Profile currentProfile;

    ActivityClubProfileBinding clubProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_profile);

        // Reference to the users and profiles in the database
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        databaseProfiles = FirebaseDatabase.getInstance().getReference("profiles");

        String email = getIntent().getStringExtra("EMAIL");
        doesProfileExist(email);

        clubProfile = ActivityClubProfileBinding.inflate(getLayoutInflater());
        setContentView(clubProfile.getRoot());

        clubProfile.confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String socialMedia = clubProfile.socialMediaLink.getText().toString();
                String phoneNumber = clubProfile.phoneNumber.getText().toString();
                String name = clubProfile.name.getText().toString();

                if (TextUtils.isEmpty(socialMedia) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(name)) {
                    Toast.makeText(ClubProfileActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else if (!isValidSocialMediaLink(socialMedia)) {
                    Toast.makeText(ClubProfileActivity.this, "Invalid social media link", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(phoneNumber)) {
                    Toast.makeText(ClubProfileActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ClubHomeActivity.class);
                    addProfile(name, socialMedia, phoneNumber, currentUser);
                    intent.putExtra("EMAIL", email);
                    startActivity(intent);
                }
            }
        });
    }

    private void addProfile(String name, String mediaLink, String phoneNumber, User user) {
        String id = databaseProfiles.push().getKey();
        currentProfile = new Profile(phoneNumber, mediaLink, name, user);
        databaseProfiles.child(id).setValue(currentProfile);
    }

    private boolean isValidSocialMediaLink(String socialMedia) {
        return Patterns.WEB_URL.matcher(socialMedia).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
    }



    public void initializeCurrentUser(String email) {
        Query query = databaseUsers.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        currentUser = snapshot.getValue(User.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });

    }

    public void doesProfileExist(String email) {
        initializeCurrentUser(email);
        databaseProfiles.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Iterate through all profiles
                    for (DataSnapshot profileSnapshot : dataSnapshot.getChildren()) {
                        Profile profile = profileSnapshot.getValue(Profile.class);
                        if (profile != null) {
                            User comparableUser = profile.getUser();
                            if (comparableUser.getId().equals(currentUser.getId())) {
                                Intent intent = new Intent(getApplicationContext(), ClubHomeActivity.class);
                                currentProfile = profile;
                                String email = getIntent().getStringExtra("EMAIL");
                                intent.putExtra("EMAIL", email);
                                startActivity(intent);

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
