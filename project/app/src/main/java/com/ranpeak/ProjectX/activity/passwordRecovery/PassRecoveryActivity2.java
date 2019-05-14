package com.ranpeak.ProjectX.activity.passwordRecovery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.registration.viewModel.RegistrationViewModel;

import java.util.Objects;

public class PassRecoveryActivity2 extends AppCompatActivity implements Activity {

    private TextView codeTextView;
    private EditText codeEditText;
    private Button nextButton;
    private String email;
    private RegistrationViewModel registrationViewModel;

    private boolean codeIsCorrect = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        email = getIntent().getStringExtra("email");
        toolbar();
        findViewById();
        onListener();
    }

    @Override
    public void findViewById() {
        codeTextView = findViewById(R.id.password_recovery2_activity_text_view);
        codeEditText = findViewById(R.id.password_recovery2_activity_edit_text);
        nextButton = findViewById(R.id.password_recovery2_button);
    }


    private void toolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListener() {
        codeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCode();
//                attemptCode();
            }
        });
        nextButton.setOnClickListener((v)-> attemptCode());
    }

    private void checkCode() {
        final String code = codeEditText.getText().toString();
        if(code.length()==4) {
            if (!registrationViewModel.checkCode(email, code)) {
                codeIsCorrect = false;
            } else {
                codeIsCorrect = true;
            }
        }
    }

    private void attemptCode() {
        codeEditText.setError(null);
        boolean cancel = false;

        if (TextUtils.isEmpty(codeEditText.getText().toString())) {
            codeEditText.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (codeEditText.getText().toString().length()!=4){
            cancel = true;
        } else if (!TextUtils.isEmpty(codeEditText.getText().toString())
                && codeEditText.getText().toString().length() == 4) {
            checkCode();
            if (!codeIsCorrect) {
                cancel = true;

                codeEditText.setError(getString(R.string.error_code));
            }
        }
        if(!cancel) {
            Intent intent = new Intent(PassRecoveryActivity2.this, PassRecoveryActivity3.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
    }
}
