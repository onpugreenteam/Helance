package com.ranpeak.ProjectX.activity.viewTaskOrResume.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.interfaces.Activity;

import org.jetbrains.annotations.NotNull;

public class ContactDialogFragment extends BottomSheetDialogFragment implements Activity {

    View view;
    private String userEmail;
    private String userPhone;
    private String userName;
    private TextView email;
    private TextView phone;
    private TextView name;
    private ConstraintLayout phoneField;
    private ConstraintLayout emailField;

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

        findViewById();
        onListener();

        phone.setText(userPhone);
        email.setText(userEmail);
        name.setText(getString(R.string.contact)+" "+userName);

        return view;
    }

    @Override
    public void findViewById() {
        phone = view.findViewById(R.id.fragment_contact_dialog_phone);
        email = view.findViewById(R.id.fragment_contact_dialog_email);
        name = view.findViewById(R.id.fragment_contact_dialog_name);
        phoneField = view.findViewById(R.id.phoneField);
        emailField = view.findViewById(R.id.emailField);
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
    }
}
