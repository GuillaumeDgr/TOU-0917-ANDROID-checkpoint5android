package fr.wcs.wcstravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    String TAG = "WCSTravel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textViewFirebaseContent = findViewById(R.id.textViewFirebaseContent);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("checkpoint5");
        mDatabaseReference.child("students").child("GuillaumeDgr").child("hasContent")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean content = snapshot.getValue(Boolean.class);
                textViewFirebaseContent.setText(String.valueOf(content));
                Log.d(TAG, "hasContent [" + content + "]");
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
