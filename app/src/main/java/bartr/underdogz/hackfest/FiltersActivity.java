package bartr.underdogz.hackfest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;



public class FiltersActivity extends AppCompatActivity {

    private static final String TAG = "FiltersActivity";


    private Button mSave;
    private EditText mLoc, mCity, mState;
    private ImageView mBackArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        mSave = (Button) findViewById(R.id.btnSave);
        mLoc = (EditText) findViewById(R.id.input_city);
        mCity = (EditText) findViewById(R.id.input_state_province);
        mState = (EditText) findViewById(R.id.input_country);
        mBackArrow = (ImageView) findViewById(R.id.backArrow);

        init();

    }

    private void init(){

        getFilterPreferences();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving...");

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FiltersActivity.this);
                SharedPreferences.Editor editor = preferences.edit();

                Log.d(TAG, "onClick: city: " + mLoc.getText().toString());
                editor.putString(getString(R.string.preferences_city), mLoc.getText().toString());
                editor.commit();

                Log.d(TAG, "onClick: state/province: " + mCity.getText().toString());
                editor.putString(getString(R.string.preferences_state_province), mCity.getText().toString());
                editor.commit();

                Log.d(TAG, "onClick: country: " + mState.getText().toString());
                editor.putString(getString(R.string.preferences_country), mState.getText().toString());
                editor.commit();
            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back.");
                finish();
            }
        });
    }

    private void getFilterPreferences(){
        Log.d(TAG, "getFilterPreferences: retrieving saved preferences.");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String country = preferences.getString(getString(R.string.preferences_country), "");
        String state_province = preferences.getString(getString(R.string.preferences_state_province), "");
        String city = preferences.getString(getString(R.string.preferences_city), "");

        mState.setText(country);
        mCity.setText(state_province);
        mLoc.setText(city);
    }
}
















