package com.example.etaxpointapplication;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceGrades;
    private List<Meetings> list= new ArrayList<>();
    private FirebaseUser user;
    private String userID;

    public interface DataStatus {
        void DataIsLoaded(List<Meetings> list, List<String> keys);
        void DataIsInserted();

        void DataIsUpdated();

        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceGrades = mDatabase.getReference("Meetings");
    }

    public void viewMeetings(final DataStatus dataStatus) {
        mReferenceGrades.child(userID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        List<String> keys = new ArrayList<>();
                        for (DataSnapshot keyNode : snapshot.getChildren()) {
                            keys.add(keyNode.getKey());
                            Meetings meetings = keyNode.getValue(Meetings.class);
                            list.add(meetings);
                        }
                        dataStatus.DataIsLoaded(list, keys);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
}
