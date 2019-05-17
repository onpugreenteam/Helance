package com.company.Helance.activity.viewTaskOrResume.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.Helance.R;
import com.company.Helance.interfaces.Activity;

import org.jetbrains.annotations.NotNull;

public class ContactDialogFragment extends BottomSheetDialogFragment implements Activity {

    View view;
    private String userEmail;
    private String userPhone;
    private String userName;
    private String userTelegram;
    private String userInstagram;
    private String userFacebook;
    private TextView email;
    private TextView phone;
    private TextView name;
    private TextView telegram;
    private TextView instagram;
    private TextView facebook;
    private ConstraintLayout phoneField;
    private ConstraintLayout emailField;
    private ConstraintLayout telegramField;
    private ConstraintLayout instagramField;
    private ConstraintLayout facebokField;

    private final String DEF_VAL="no";

    public ContactDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.item_contact_user, container, false);
//        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        Bundle bundle = getArguments();

        userEmail = bundle.getString(getString(R.string.email),"no email(");
        userPhone = bundle.getString(getString(R.string.phone),"no phone number(");
        userName = bundle.getString(getString(R.string.name),"no name(");
        userTelegram = bundle.getString(getString(R.string.telegram),DEF_VAL);
        userInstagram = bundle.getString(getString(R.string.instagram),DEF_VAL);
        userFacebook = bundle.getString(getString(R.string.facebook),DEF_VAL);

        findViewById();
        onListener();

        phone.setText(userPhone);
        email.setText(userEmail);
        name.setText(getString(R.string.contact)+" "+userName);
        if (!userTelegram.equals(DEF_VAL)) {
            telegramField.setVisibility(View.VISIBLE);
            telegram.setText(userTelegram);
        }
        if (!userInstagram.equals(DEF_VAL)) {
            instagramField.setVisibility(View.VISIBLE);
            instagram.setText(userInstagram);
        }
//        if (!userFacebook.equals(DEF_VAL)) {
//            facebokField.setVisibility(View.VISIBLE);
//            facebook.setText(userFacebook);
//        }

        return view;
    }

    @Override
    public void findViewById() {
        phone = view.findViewById(R.id.fragment_contact_dialog_phone);
        email = view.findViewById(R.id.fragment_contact_dialog_email);
        name = view.findViewById(R.id.fragment_contact_dialog_name);
        telegram = view.findViewById(R.id.fragment_contact_dialog_telegram);
        instagram = view.findViewById(R.id.fragment_contact_dialog_instagram);
        facebook = view.findViewById(R.id.fragment_contact_dialog_facebook);
        phoneField = view.findViewById(R.id.fragment_contact_dialog_phoneField);
        emailField = view.findViewById(R.id.fragment_contact_dialog_emailField);
        telegramField = view.findViewById(R.id.fragment_contact_dialog_telegramField);
        instagramField = view.findViewById(R.id.fragment_contact_dialog_instagramField);
        facebokField = view.findViewById(R.id.fragment_contact_dialog_facebookField);
    }

    @Override
    public void onListener() {
        phoneField.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", userPhone, null));
            startActivity(intent);
        });

        emailField.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", userEmail, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Helance contact");
            startActivity(Intent.createChooser(emailIntent, null));

        });

        telegramField.setOnClickListener(c -> {
            Intent telegramIntent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/"+userTelegram));
            startActivity(telegramIntent);
        });
        instagramField.setOnClickListener(c -> {
            Intent telegramIntent = new Intent(Intent.ACTION_VIEW , Uri.parse("http://instagram.com/"+userInstagram));
            startActivity(telegramIntent);
        });
        facebokField.setOnClickListener(c -> {
            Intent telegramIntent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://www.facebook.com/"+userFacebook));
            startActivity(telegramIntent);
        });
    }
}
