package com.ranpeak.ProjectX.activity.viewTaskOrResume.contact;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranpeak.ProjectX.R;
import org.jetbrains.annotations.NotNull;

public class ContactDialogFragment extends BottomSheetDialogFragment {

    private String userEmail;
    private String userPhone;
    private String userName;
    private TextView email;
    private TextView phone;
    private TextView name;

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
        View view = inflater.inflate(R.layout.fragment_contact_dialog, container, false);
//        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        Bundle bundle = getArguments();

        userEmail = bundle.getString(getString(R.string.email),"no email(");
        userPhone = bundle.getString(getString(R.string.phone),"no phone number(");
        userName = bundle.getString(getString(R.string.name),"no name(");

        phone = view.findViewById(R.id.fragment_contact_dialog_phone);
        email = view.findViewById(R.id.fragment_contact_dialog_email);
        name = view.findViewById(R.id.fragment_contact_dialog_name);

        phone.setText(userPhone);
        email.setText(userEmail);
        name.setText(getString(R.string.contact)+" "+userName);

        return view;
    }
}
