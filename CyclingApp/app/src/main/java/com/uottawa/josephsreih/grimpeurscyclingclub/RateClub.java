package com.uottawa.josephsreih.grimpeurscyclingclub;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.josephsreih.grimpeurscyclingclub.databinding.ActivityRateClubBinding;

import java.util.ArrayList;
import java.util.List;

public class RateClub extends AppCompatActivity {
    ActivityRateClubBinding binding;
    private DatabaseReference databaseRatings;
    private DatabaseReference databaseUsers;
    float numberRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateClubBinding.inflate(getLayoutInflater());
        databaseRatings = FirebaseDatabase.getInstance().getReference("ratings");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        setContentView(binding.getRoot());
        numberRating = binding.ratingBar.getRating();
        binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                numberRating = binding.ratingBar.getRating();
            }
        });
        binding.submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredClubName = binding.autoCompleteTextView.getText().toString();
                numberRating = binding.ratingBar.getRating();
                String comment = binding.comments.getText().toString();
                if(!enteredClubName.equals("")) {
                    checkClubName(enteredClubName, new ParticipantsHomePage.ClubNameCheckCallback() {
                        @Override
                        public void onClubNameChecked(boolean exists) {
                            if (exists) {
                                Toast.makeText(RateClub.this, "You have successfully made a rating", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ParticipantsHomePage.class);
                                addRating(numberRating, comment);

                                startActivity(intent);
                            } else {
                                Toast.makeText(RateClub.this, "Club does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
                }else{
                    Toast.makeText(RateClub.this, "Must enter valid club name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void checkClubName(String enteredClubName, ParticipantsHomePage.ClubNameCheckCallback callback) {
        Query query = databaseUsers.orderByChild("name").equalTo(enteredClubName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Club name exists in the database
                    callback.onClubNameChecked(true);
                } else {
                    // Club name does not exist in the database
                    callback.onClubNameChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addRating(float numberRating, String comment){
        String id = databaseRatings.push().getKey();
        Rating rating = new Rating(numberRating, comment);
        databaseRatings.child(id).setValue(rating);
    }



}
