package com.ranpeak.ProjectX.activity.logIn;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.forAuthorizedUsers.LobbyActivity;
import com.ranpeak.ProjectX.activity.logIn.commands.LoginNavigator;
import com.ranpeak.ProjectX.activity.logIn.viewmodel.LoginViewModel;
import com.ranpeak.ProjectX.activity.passwordRecovery.PassRecoveryActivity1;
import com.ranpeak.ProjectX.activity.registration.activities.RegistrationActivity1;
import com.ranpeak.ProjectX.activity.registration.activities.RegistrationActivity5;
import com.ranpeak.ProjectX.databinding.ActivityLoginBinding;
import com.ranpeak.ProjectX.networking.volley.Constants;

import java.util.ArrayList;
import java.util.List;
import static android.Manifest.permission.READ_CONTACTS;

public class LogInActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, Activity, LoginNavigator {

    private final static int LOGIN_ACTIVITY = R.layout.activity_login;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button facebook;
    private Button google;
    private TextView registrationButton;
    private TextView forgotPassword;

    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        activityLoginBinding = DataBindingUtil.setContentView(this,LOGIN_ACTIVITY);
        loginViewModel = new LoginViewModel(this);
        loginViewModel.setNavigator(this);
        activityLoginBinding.setViewModel(loginViewModel);

        findViewById();
        onListener();
        populateAutoComplete();
//        animationBackground();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LogInActivity.class);
    }

    @Override
    public void findViewById() {
        mEmailView = findViewById(R.id.login_activity_email);
        mPasswordView = findViewById(R.id.login_activity_password);
        registrationButton = findViewById(R.id.login_activity_registration_button);
        forgotPassword = findViewById(R.id.login_activity_forgot_password);
        facebook = findViewById(R.id.login_activity_registration_facebook);
        google = findViewById(R.id.login_activity_registration_google);
    }

    @Override
    public void handleError(Throwable throwable) {
        Log.d("ERROR",throwable.getMessage());
        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginClicked() {
        attemptLogin();
    }

    @Override
    public void openLobbyActivity() {
        Intent intent = LobbyActivity.newIntent(LogInActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
    }

    @Override
    public void openRegistrationActivity(
            String login,
            String email,
            String name,
            String country,
            String avatar,
            String phone) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity5.class);
        intent.putExtra("login", login);
        intent.putExtra("email", email);
        intent.putExtra("name", name);
        intent.putExtra("country", country);
        intent.putExtra("avatar", avatar);
        intent.putExtra("phone", phone);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"You account not active",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onListener() {
        registrationButton.setOnClickListener(view -> {
                    if (Constants.isOnline()) {
                        startActivity(
                                new Intent(LogInActivity.this, RegistrationActivity1.class));
                    } else {
                        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        forgotPassword.setOnClickListener(view -> {
                    if (Constants.isOnline()) {
                        startActivity(
                                new Intent(LogInActivity.this, PassRecoveryActivity1.class));
                    } else {
                        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }

        );
        google.setOnClickListener(view -> {

        });
        facebook.setOnClickListener(view -> {

        });
    }

    // Попытка залогинится
    private void attemptLogin() {
        if(Constants.isOnline()) {
            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);
            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(password)) {
                mPasswordView.setError(getString(R.string.error_field_required));
                focusView = mPasswordView;
                cancel = true;
            } else if (!isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                final String log = activityLoginBinding.loginActivityEmail.getText().toString();
                final String pass = activityLoginBinding.loginActivityPassword.getText().toString();
                loginViewModel.sendLoginRequest(log, pass, getApplicationContext());
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmailValid(String email) {
        return email.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, v -> requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS));
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), LogInActivity.ProfileQuery.PROJECTION,
                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},
                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(LogInActivity.ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LogInActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    public interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}

