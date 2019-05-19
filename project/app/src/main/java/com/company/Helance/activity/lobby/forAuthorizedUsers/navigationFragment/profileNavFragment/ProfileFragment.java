package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.company.Helance.R;
import com.company.Helance.activity.editProfile.EditProfileActivity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.adapter.ProfileFragmentPagerAdapter;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.ProfileViewModel;
import com.company.Helance.activity.settings.SettingsActivity;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.interfaces.navigators.ProfileNavigator;
import com.company.Helance.networking.IsOnline;
import com.company.Helance.settingsApp.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;


public class ProfileFragment extends Fragment implements Activity, ProfileNavigator {

    private View view;
    private PullRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ImageView editProfile;
    private CircleImageView avatar;
    private ImageView settings;
    private ViewPager viewPager;
    private TextView tasksCount;
    private TextView resumesCount;

    private MyTaskViewModel myTaskViewModel;
    private MyResumeViewModel resumeViewModel;
    private ProfileViewModel profileViewModel;

    private int selectedTab = 0;
    // user info
    private TextView name;
    private TextView login;
    private TextView country;

    private final int GALLERY = 1;
    private static final int REQUEST_PERMISSION = 200;

    private io.reactivex.disposables.CompositeDisposable mDisposable = new CompositeDisposable();

    private final int[] imageResId = {
            R.drawable.my_profile, R.drawable.my_task, R.drawable.my_resume
    };

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileViewModel = new ProfileViewModel(getContext());
        profileViewModel.setNavigator(this);

        findViewById();
        typeRefresh();
        onListener();

        initData();
        initFragments(viewPager);

        // Спрашмвает пользователя разрешение на доступ к галерее(если он его не давал еще)
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

        return view;
    }

    @Override
    public void findViewById() {
        appBarLayout = view.findViewById(R.id.fragment_profile_main_appbar);
        swipeRefreshLayout = view.findViewById(R.id.fragment_profile_swipeRefreshLayout);
        tasksCount = view.findViewById(R.id.fragment_profile_task_count);
        resumesCount = view.findViewById(R.id.fragment_profile_resumes_count);
        viewPager = view.findViewById(R.id.fragment_profile_viewPager);
        tabLayout = view.findViewById(R.id.fragment_profile_tabLayout);
        editProfile = view.findViewById(R.id.fragment_profile_edit_profile);
        settings = view.findViewById(R.id.fragment_profile_settings);
        name = view.findViewById(R.id.fragment_profile_name);
        login = view.findViewById(R.id.fragment_profile_login);
        avatar = view.findViewById(R.id.fragment_profile_avatar);
        country = view.findViewById(R.id.fragment_profile_country);
    }

    @Override
    public void onListener() {
        settings.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), SettingsActivity.class)));
        editProfile.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), EditProfileActivity.class)));
        avatar.setOnClickListener(v -> startActivityForResult(
                new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY));
        tabLayout.setupWithViewPager(viewPager);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (IsOnline.getInstance().isConnectingToInternet(Objects.requireNonNull(getContext()))) {
                    initData();
                    initFragments(viewPager);
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }, 1500);
        });
        appBarLayout.addOnOffsetChangedListener((appBarLayout, i) -> {
            if ((appBarLayout.getHeight() - appBarLayout.getBottom()) != 0) {
                selectedTab = tabLayout.getSelectedTabPosition();
                swipeRefreshLayout.setEnabled(false);
            } else {
                swipeRefreshLayout.setEnabled(true);
            }
        });
    }

    private void typeRefresh() {
        swipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
    }

    private void initData() {
        myTaskViewModel = ViewModelProviders.of(this).get(MyTaskViewModel.class);
        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);

        myTaskViewModel.getCountOfUsersTask(
                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin())
        ).observe(this, integer -> {
            tasksCount.setText(String.valueOf(integer));
        });
        resumeViewModel.getCountOfUsersResumes(
                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin())
        ).observe(this, integer -> {
            resumesCount.setText(String.valueOf(integer));
        });
        getSavedInfoAboutUser();
    }

    // Записывание данных о пользователе в нужные поля профиля
    private void getSavedInfoAboutUser() {
        login.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin()));
        name.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserName()));
        country.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserCountry()));

        Log.d("AVATAR URL in profile",String.valueOf(SharedPrefManager.getInstance(getContext()).getUserAvatar()));

        if(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserAvatar()).equals("null")){
            avatar.setImageResource(R.drawable.ic_helance_logo_item_svg);
        }else {
            Glide.with(getContext())
                    .load(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserAvatar()))
                    .into(avatar);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Glide.with(getContext())
                            .load(contentURI)
                            .into(avatar);
                    avatar.setVisibility(View.VISIBLE);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    uploadAvatar(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void initFragments(ViewPager viewPager) {
        ProfileFragmentPagerAdapter adapter = new ProfileFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        for (int i = 0; i < imageResId.length; i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setIcon(imageResId[i]);
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "Error load", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadAvatar(Bitmap bitmap) {
        profileViewModel.uploadImage(bitmap);
    }

    @Override
    public void onComplete() {
        Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
    }
}
