package com.ranpeak.ProjectX.activity.registration;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.registration.fragment.CountryListFragment;
import com.ranpeak.ProjectX.constant.Constants;


public class RegistrationActivity2 extends AppCompatActivity implements Activity {

    private final static int REGISTRATION_ACTIVITY2 = R.layout.activity_registration2;

    private EditText register_name;
    private TextView register_country;
    private static String email;
    private Button nextButton;
    private final FragmentManager fm = getFragmentManager();
    private final CountryListFragment countryListFragment = new CountryListFragment();


    public void setCountry(String country) {
        this.register_country.setText(country);
        this.register_country.setTextColor(Color.parseColor("#D81B60"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTRATION_ACTIVITY2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById();
        onListener();
        email = getIntent().getStringExtra("email");


        // start fragmentActivity to choose country


        nextButton.setEnabled(false);
    }

    @Override
    public void findViewById() {
        register_name = findViewById(R.id.register_name);
        nextButton = findViewById(R.id.registration_button_2);
        register_country = findViewById(R.id.register_country);
    }

    @Override
    public void onListener() {
        register_name.addTextChangedListener(textWatcher);
        register_country.addTextChangedListener(textWatcher);
        register_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryListFragment.show(fm, "Country lists");
            }
        });
        nextButton.addTextChangedListener(textWatcher);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity3.class);
                intent.putExtra("email", email);
                intent.putExtra("name", register_name.getText().toString().trim());
                intent.putExtra("country", register_country.getText().toString().trim());
//                intent.putExtra("country", "Nigeria");
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            nextButton.setEnabled(!register_name.getText().toString().trim().isEmpty()
                    && stringContainsItemFromList(register_country.getText().toString(),
                    Constants.Values.COUNTRIES)
            );
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    private static boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (int i = 0; i < items.length; i++) {
            if (inputStr.contains(items[i])) {
                return true;
            }
        }
        return false;
    }
}
