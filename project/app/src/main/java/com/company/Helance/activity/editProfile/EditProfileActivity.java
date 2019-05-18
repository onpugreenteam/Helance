package com.company.Helance.activity.editProfile;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.hbb20.CountryCodePicker;
import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyProfileViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;
import com.company.Helance.dto.SocialNetworkDTO;
import com.company.Helance.networking.IsOnline;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.networking.volley.RequestHandler;
import com.company.Helance.settingsApp.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EditProfileActivity extends AppCompatActivity implements Activity {

    private EditText name;
    private CountryCodePicker country;
    private EditText email;
    private EditText telephone;
    private EditText telegram;
    private EditText instgram;
    private EditText facebook;
    private Button save;
    private Button password;

    private String beforeEditingName;
    private String beforeEditingCountry;
    private String beforeEditingEmail;
    private String beforeEditingTelephone;
    private String beforeEditingTelegram = "";
    private String beforeEditingInstagram = "";
    private String beforeEditingFacebook = "";

    private SocialNetworkDTO socialNetworkDTOTg = new SocialNetworkDTO();
    private SocialNetworkDTO socialNetworkDTOInst = new SocialNetworkDTO();
    private SocialNetworkDTO socialNetworkDTOFacebook = new SocialNetworkDTO();

    private boolean email_exists = false;
    private boolean login_exists = false;

    private MyProfileViewModel profileViewModel;
    private MyTaskViewModel taskViewModel;
    private MyResumeViewModel resumeViewModel;
    private CompositeDisposable disposable = new CompositeDisposable();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        findViewById();
        setText();
        onListener();
        toolbar();
    }

    @Override
    public void findViewById() {
        name = findViewById(R.id.edit_profile_name_edit_text);
        country = findViewById(R.id.edit_profile_country);
        email = findViewById(R.id.edit_profile_email_edit_text);
        telephone = findViewById(R.id.edit_profile_telephone_edit_text);
        telegram = findViewById(R.id.edit_profile_social_network_telegramm_edit_text);
        instgram = findViewById(R.id.edit_profile_social_network_instagramm_edit_text);
        facebook = findViewById(R.id.edit_profile_social_network_facebook_edit_text);
        save = findViewById(R.id.edit_profile_save_button);
        password = findViewById(R.id.edit_profile_password_button);
    }

    @Override
    public void onListener() {
        password.setOnClickListener(v -> {

        });
        save.setOnClickListener(v -> {
            checkFields();
        });
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
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setText() {
        profileViewModel = ViewModelProviders.of(this).get(MyProfileViewModel.class);
        taskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);


        name.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserName()));
        beforeEditingName = name.getText().toString();

        beforeEditingCountry = country.getSelectedCountryName();
        email.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserEmail()));
        beforeEditingEmail = email.getText().toString();
        telephone.setText(String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserTelephone()));
        beforeEditingTelephone = telephone.getText().toString();
        disposable.add(profileViewModel.receiveNetworks(
                String.valueOf(SharedPrefManager.getInstance(this).getUserLogin())
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(socialNetworkDTOS -> {
                            for (SocialNetworkDTO socialNetworkDTO : socialNetworkDTOS) {
                                switch (socialNetworkDTO.getNetworkName()) {
                                    case Constants.Values.TELEGRAM:
                                        telegram.setText(socialNetworkDTO.getNetworkLogin());
                                        beforeEditingTelegram = telegram.getText().toString();
                                        socialNetworkDTOTg = socialNetworkDTO;
                                        break;
                                    case Constants.Values.INSTAGRAM:
                                        instgram.setText(socialNetworkDTO.getNetworkLogin());
                                        beforeEditingInstagram = instgram.getText().toString();
                                        socialNetworkDTOInst = socialNetworkDTO;
                                        break;
                                    case Constants.Values.FACEBOOK:
                                        facebook.setText(socialNetworkDTO.getNetworkLogin());
                                        beforeEditingFacebook = facebook.getText().toString();
                                        socialNetworkDTOFacebook = socialNetworkDTO;
                                        break;
                                }
                            }
                        })
        );
    }

    private void checkFields() {
        name.setError(null);
        email.setError(null);
        telephone.setError(null);
        telegram.setError(null);
        instgram.setError(null);
        facebook.setError(null);

        boolean cancel = false;
        View focusView = null;


        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError(getString(R.string.error_field_required));
            focusView = name;
            cancel = true;
        } else if (!isNameValid(name.getText().toString())) {
            name.setError(getString(R.string.error_invalid_name));
            focusView = name;
            cancel = true;
        }

        // Check for a valid email, if the user entered one.
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(email.getText().toString())) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        } else if (!TextUtils.isEmpty(email.getText().toString()) && isEmailValid(email.getText().toString())) {
            checkEmail();
            if (email_exists) {
                email.setError(getString(R.string.error_exist_email));
                focusView = email;
                cancel = true;
            }
        }
        if (!cancel) {
            attemptEdit();
        } else {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        }
    }

    private void attemptEdit() {
        if(IsOnline.getInstance().isConnectingToInternet(getApplicationContext())) {
            if (
                    name.getText().toString().equals(
                            beforeEditingName)
                            && country.getSelectedCountryName().toString().equals(
                            beforeEditingCountry)
                            && email.getText().toString().equals(
                            beforeEditingEmail)
                            && telegram.getText().toString().equals(
                            beforeEditingTelegram)
                            && instgram.getText().toString().equals(
                            beforeEditingInstagram)
                            && facebook.getText().toString().equals(
                            beforeEditingFacebook)
            ) {
                Log.d("update_not", "update_not");
                finish();
            } else /*if (*/
//                !name.getText().toString().equals(
//                        beforeEditingName)
//                        && !country.getText().toString().equals(
//                        beforeEditingCountry)
//                        && !email.getText().toString().equals(
//                        beforeEditingEmail)
//                        && !telegram.getText().toString().equals(
//                        beforeEditingTelegram)
//                        && !instgram.getText().toString().equals(
//                        beforeEditingInstagram)
//                        && !facebook.getText().toString().equals(
//                        beforeEditingFacebook)
                /*)*/ {
                Log.d("update_all", "update_all");
//            String login, String name, String country, String email, String telephone
                updateUserInfoOnServer(
                        java.lang.String.valueOf(SharedPrefManager.getInstance(this).getUserLogin()),
                        name.getText().toString(), country.getSelectedCountryName().toString(),
                        email.getText().toString(), telephone.getText().toString()
                );

                socialNetworkDTOTg.setNetworkLogin(telegram.getText().toString());
                socialNetworkDTOTg.setNetworkName(getString(R.string.telegram));
                socialNetworkDTOTg.setUserLogin(String.valueOf(SharedPrefManager.getInstance(this).getUserLogin()));
                if (telegram.getText().toString().length() != 0) {

                    if (beforeEditingTelegram.isEmpty()) {
                        socialNetworkDTOTg.setIdNetwork((int) (Math.random() * 100) + 1);
                        insertNetworkOnServer(socialNetworkDTOTg);
                    } else {

                        updateNetworksOnServer(
                                socialNetworkDTOTg.getUserLogin(),
                                socialNetworkDTOTg.getNetworkName(),
                                socialNetworkDTOTg.getNetworkLogin()
                        );
                    }
                } else {
                    if (!beforeEditingTelegram.isEmpty()) {
                        socialNetworkDTOTg.setIdNetwork((int) (Math.random() * 100) + 1);
                        deleteNetworksOnServer(socialNetworkDTOTg);
                    }
                }

                socialNetworkDTOInst.setNetworkLogin(instgram.getText().toString());
                socialNetworkDTOInst.setNetworkName(getString(R.string.instagram));
                socialNetworkDTOInst.setUserLogin(String.valueOf(SharedPrefManager.getInstance(this).getUserLogin()));
                if (instgram.getText().toString().length() != 0) {

                    if (beforeEditingInstagram.isEmpty()) {
                        socialNetworkDTOInst.setIdNetwork((int) (Math.random() * 100) + 1);
                        insertNetworkOnServer(socialNetworkDTOInst);
                    } else {
                        updateNetworksOnServer(
                                socialNetworkDTOInst.getUserLogin(),
                                socialNetworkDTOInst.getNetworkName(),
                                socialNetworkDTOInst.getNetworkLogin()
                        );
                    }
                } else {
                    if (!beforeEditingInstagram.isEmpty()) {
                        socialNetworkDTOInst.setIdNetwork((int) (Math.random() * 100) + 1);

                        deleteNetworksOnServer(socialNetworkDTOInst);
                    }
                }

                socialNetworkDTOFacebook.setNetworkLogin(facebook.getText().toString());
                socialNetworkDTOFacebook.setNetworkName(getString(R.string.facebook));
                socialNetworkDTOFacebook.setUserLogin(String.valueOf(SharedPrefManager.getInstance(this).getUserLogin()));
                if (facebook.getText().toString().length() != 0) {

                    if (beforeEditingFacebook.isEmpty()) {
                        socialNetworkDTOFacebook.setIdNetwork((int) (Math.random() * 100) + 1);
                        insertNetworkOnServer(socialNetworkDTOFacebook);
                    } else {

                        updateNetworksOnServer(
                                socialNetworkDTOFacebook.getUserLogin(),
                                socialNetworkDTOFacebook.getNetworkName(),
                                socialNetworkDTOFacebook.getNetworkLogin()
                        );
                    }
                } else {
                    if (!beforeEditingFacebook.isEmpty()) {
                        socialNetworkDTOFacebook.setIdNetwork((int) (Math.random() * 100) + 1);

                        deleteNetworksOnServer(socialNetworkDTOFacebook);
                    }
                }

                String login = String.valueOf(SharedPrefManager.getInstance(this).getUserLogin());
                String avatar = String.valueOf(SharedPrefManager.getInstance(this).getUserAvatar());
                SharedPrefManager.getInstance(this).userLogin(
                        login,
                        name.getText().toString(),
                        email.getText().toString(),
                        country.getSelectedCountryName().toString(),
                        avatar,
                        telephone.getText().toString()
                );
                /** update social networks*/

                finish();
            }

//        else if (
//                !name.getText().toString().equals(
//                        beforeEditingName)
//                        && !country.getText().toString().equals(
//                        beforeEditingCountry)
//                        && !email.getText().toString().equals(
//                        beforeEditingEmail)
//        ) {
//            Log.d("update_serv", "update_serv");
//
//            updateUserInfoOnServer();
//        } else if (!telegram.getText().toString().equals(beforeEditingTelegram)) {
//            socialNetworkDTOTg.setNetworkLogin(telegram.getText().toString());
//            Log.d("update_tg", "update_tg");
//
//            updateNetworksOnServer(socialNetworkDTOTg);
//        }
//
//        else if(!instgram.getText().toString().equals(beforeEditingInstagram)) {
//            socialNetworkDTOInst.setNetworkLogin(instgram.getText().toString());
//            Log.d("update_int", "update_int");
//            updateNetworksOnServer(socialNetworkDTOInst);
//        }
//
//        else if(!facebook.getText().toString().equals(beforeEditingFacebook)) {
//            socialNetworkDTOFacebook.setNetworkLogin(facebook.getText().toString());
//            Log.d("update_fb", "update_fb");
//            updateNetworksOnServer(socialNetworkDTOFacebook);
//        }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkEmail() {
        String email = this.email.getText().toString();
        if (!email.equals(beforeEditingEmail)) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL.CHECK_EMAIL,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("no")) {
                                this.email.setError(getString(R.string.error_exist_email));
                                email_exists = true;

                            } else if (jsonObject.getString("message").equals("ok")) {
                                this.email.setError(null);
                                email_exists = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), getString(R.string.internet_error), Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    return params;
                }
            };
            RequestHandler.getmInstance(this).addToRequestQueue(stringRequest);
        }
    }

    private boolean isNameValid(String name) {
        return name.length() > 4;
    }

    private boolean isEmailValid(String email) {
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void updateUserInfoOnServer(String login, String name, String country, String email, String telephone) {

        Log.d("beforeUpdate", login);
        Log.d("beforeUpdate", name);
        Log.d("beforeUpdate", country);
        Log.d("beforeUpdate", email);
        Log.d("beforeUpdate", telephone);

        profileViewModel.updateUserInfo(login,
                name, country, email, telephone
        );
    }

    private void updateNetworksOnServer(String login, String networkName, String networkLogin) {
        profileViewModel.updateNetworks(
                login, networkName, networkLogin
        );
    }

    private void insertNetworkOnServer(SocialNetworkDTO socialNetworkDTO) {
        profileViewModel.addUserSocialNetwork(socialNetworkDTO);
    }

    private void deleteNetworksOnServer(SocialNetworkDTO socialNetworkDTO) {
        Log.d("getIdNetwork", String.valueOf(socialNetworkDTO.getIdNetwork()));
        Log.d("getNetworkName", String.valueOf(socialNetworkDTO.getNetworkName()));
        Log.d("getNetworkLogin", String.valueOf(socialNetworkDTO.getNetworkLogin()));
        Log.d("getUserLogin", String.valueOf(socialNetworkDTO.getUserLogin()));
        profileViewModel.deleteNetwork(
                socialNetworkDTO.getNetworkName(),
                String.valueOf(SharedPrefManager.getInstance(this).getUserLogin())
        );
    }
}
