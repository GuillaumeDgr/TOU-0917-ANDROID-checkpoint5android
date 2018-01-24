package fr.wcs.wcstravel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String TAG = "WCS_Travel";
    private Spinner mSpinnerDepartureFlight, mSpinnerDestinationFlight;
    private Calendar mStartCalendar, mEndCalendar;
    private TextView mTextViewDepartureDate, mTextViewReturnDate;
    private Button mButtonFindFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase hasContent Value
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

        // Spinners
        mSpinnerDepartureFlight = findViewById(R.id.spinnerDepartureFlight);
        mSpinnerDestinationFlight = findViewById(R.id.spinnerDestinationFlight);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.airports, R.layout.item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDepartureFlight.setAdapter(adapter);
        mSpinnerDestinationFlight.setAdapter(adapter);

        // Date Pickers
        mTextViewDepartureDate = findViewById(R.id.textViewDepartureDate);
        mTextViewReturnDate = findViewById(R.id.textViewReturnDate);

        mStartCalendar = Calendar.getInstance();
        mEndCalendar = Calendar.getInstance();

        chooseDepartureDate();
        chooseReturnDate();

        // Search Flight
        mButtonFindFlight = findViewById(R.id.buttonFindFlight);
        mButtonFindFlight.setOnClickListener(v -> {
            TravelModel travelModel = new TravelModel();
            travelModel.setTravel(mSpinnerDepartureFlight.getSelectedItem().toString()
                    + "_" + mSpinnerDestinationFlight.getSelectedItem().toString());
            travelModel.setDeparture_date(mTextViewDepartureDate.getText().toString());
            travelModel.setReturn_date(mTextViewReturnDate.getText().toString());

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("travelModel", travelModel);
            startActivity(intent);
        });
    }

    public void chooseDepartureDate() {
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {
                String year = String.format("%04d", mStartCalendar.get(Calendar.YEAR));
                String month = String.format("%02d", (mStartCalendar.get(Calendar.MONTH) + 1));
                String day = String.format("%02d", mStartCalendar.get(Calendar.DAY_OF_MONTH));
                mTextViewDepartureDate.setText(year + "-" + month + "-" + day);
            }
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mStartCalendar.set(Calendar.YEAR, year);
                mStartCalendar.set(Calendar.MONTH, monthOfYear);
                mStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        mTextViewDepartureDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    datePicker, mStartCalendar.get(Calendar.YEAR), mStartCalendar.get(Calendar.MONTH),
                    mStartCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis() - 1000));
            datePickerDialog.show();
        });
    }

    public void chooseReturnDate() {
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {
                String year = String.format("%04d", mEndCalendar.get(Calendar.YEAR));
                String month = String.format("%02d", (mEndCalendar.get(Calendar.MONTH) + 1));
                String day = String.format("%02d", mEndCalendar.get(Calendar.DAY_OF_MONTH));
                mTextViewReturnDate.setText(year + "-" + month + "-" + day);
            }
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mEndCalendar.set(Calendar.YEAR, year);
                mEndCalendar.set(Calendar.MONTH, monthOfYear);
                mEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        mTextViewReturnDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    datePicker, mEndCalendar.get(Calendar.YEAR), mEndCalendar.get(Calendar.MONTH),
                    mEndCalendar.get(Calendar.DAY_OF_MONTH) + 1);
            datePickerDialog.getDatePicker().setMinDate(mStartCalendar.getTime().getTime());
            datePickerDialog.show();
        });
    }
}
