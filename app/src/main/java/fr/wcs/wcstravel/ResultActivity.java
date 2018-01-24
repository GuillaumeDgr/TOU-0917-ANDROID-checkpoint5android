package fr.wcs.wcstravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewFlightList;
    private TravelAdapter mTravelAdapter;
    private List<TravelModel> mTravelModelList = new ArrayList<>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private TravelModel mTravelModel;
    private TextView mTextViewNoResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mRecyclerViewFlightList = findViewById(R.id.recyclerViewFlightList);
        mTextViewNoResults = findViewById(R.id.textViewNoResults);

        mTravelAdapter = new TravelAdapter(mTravelModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewFlightList.setLayoutManager(mLayoutManager);
        mRecyclerViewFlightList.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewFlightList.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        mRecyclerViewFlightList.setAdapter(mTravelAdapter);

        loadTravels();
    }

    private void loadTravels() {
        TravelModel travelModel = getIntent().getParcelableExtra("travelModel");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("checkpoint5");
        mDatabaseReference.child("travels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    mTravelModel = snap.getValue(TravelModel.class);

                    String departureDateString = mTravelModel.getDeparture_date();
                    String returnDateString = mTravelModel.getReturn_date();
                    String departureDateSearchString = travelModel.getDeparture_date();
                    String returnDateSearchString = travelModel.getReturn_date();

                    Date mTravelModelDepartureDate = null, mTravelModelReturnDate = null,
                            travelModelDepartureDate = null, travelModelReturnDate = null;

                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        mTravelModelDepartureDate = sdf.parse(departureDateString);
                        mTravelModelReturnDate = sdf.parse(returnDateString);
                        travelModelDepartureDate = sdf.parse(departureDateSearchString);
                        travelModelReturnDate = sdf.parse(returnDateSearchString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if ((mTravelModel.getTravel().equals(travelModel.getTravel()))
                            && ((mTravelModelDepartureDate.after(travelModelDepartureDate)
                            || mTravelModelDepartureDate.equals(travelModelDepartureDate))
                            && (mTravelModelReturnDate.before(travelModelReturnDate)
                            || mTravelModelReturnDate.equals(travelModelReturnDate)))) {
                        mTravelModelList.add(mTravelModel);
                    }

                    if (mTravelModelList.isEmpty()) {
                        mTextViewNoResults.setVisibility(View.VISIBLE);
                    } else {
                        mTextViewNoResults.setVisibility(View.GONE);
                    }
                }
                mTravelAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
