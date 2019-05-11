package com.ranpeak.ProjectX.activity.viewTaskOrResume.contact;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ranpeak.ProjectX.R;
import org.jetbrains.annotations.NotNull;

public class ContactDialogFragment extends BottomSheetDialogFragment {

    public ContactDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_dialog, container, false);
    }
}
