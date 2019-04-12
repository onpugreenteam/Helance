package com.ranpeak.ProjectX.activity.logIn;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.LobbyActivity;
import com.ranpeak.ProjectX.activity.registration.RegistrationActivity1;
import com.ranpeak.ProjectX.activity.registration.RegistrationActivity5;
import com.ranpeak.ProjectX.constant.Constants;
import com.ranpeak.ProjectX.request.RequestHandler;
import com.ranpeak.ProjectX.settings.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

public class LogInActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, Activity {

    private final static int LOGIN_ACTIVITY = R.layout.activity_login;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private ProgressDialog progressDialog;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView textViewError;
    private Button mEmailSignInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LOGIN_ACTIVITY);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LobbyActivity.class));
        }

        findViewById();
        onListener();
        populateAutoComplete();
//        animationBackground();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

    }

    @Override
    public void findViewById() {
        mEmailView = findViewById(R.id.login_activity_email);
        mPasswordView = findViewById(R.id.login_activity_password);
        mEmailSignInButton = findViewById(R.id.login_activity_login_button);
        textViewError = findViewById(R.id.login_activity_text_view_error);
    }

    @Override
    public void onListener() {
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        mEmailSignInButton.setOnClickListener(view -> attemptLogin());

    }

    // Попытка залогинится
    private void attemptLogin() {
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
            loginUser();
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

    // Запрос на аунтификацию по (логину или почте) с паролем
    private void loginUser() {

        final String login = mEmailView.getText().toString().trim();
        final String password = mPasswordView.getText().toString().trim();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL.LOGIN_USER,
                response -> {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        // если аккаунт не активирован, то открывается активность где надо ввести код
                        if ((jsonObject.getString("login").equals(login)
                                || jsonObject.getString("email").equals(login))
                                && !jsonObject.getBoolean("active")) {
                            Intent intent = new Intent(getApplicationContext(), RegistrationActivity5.class);
                            intent.putExtra("registration_username", jsonObject.getString("login"));
                            intent.putExtra("password", jsonObject.getString("password"));
                            intent.putExtra("name", jsonObject.getString("name"));
                            intent.putExtra("email", jsonObject.getString("email"));
                            intent.putExtra("country", jsonObject.getString("country"));
                            intent.putExtra("avatar", jsonObject.getString("avatar"));
                            intent.putExtra("aboutMyself", jsonObject.getString("aboutMyself"));
                            startActivity(intent);
                        } else if ((jsonObject.getString("login").equals(login)
                                || jsonObject.getString("email").equals(login))
                                && jsonObject.getBoolean("active")) {

                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(
                                            jsonObject.getString("login"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("email"),
                                            jsonObject.getString("country"),
                                            jsonObject.getString("avatar")
                                    );
                            startActivity(new Intent(getApplicationContext(), LobbyActivity.class));
                            finish();

                        } else if (jsonObject.getString("message").equals("error")) {
                            mEmailView.getText().clear();
                            mPasswordView.getText().clear();

                            textViewError.setVisibility(View.VISIBLE);
                            textViewError.setText(getText(R.string.invalidEmailOrPassword));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), "Please on Internet",
                            Toast.LENGTH_LONG).show();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
    }
}

