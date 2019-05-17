package com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.company.Helance.R;
import com.company.Helance.activity.settings.SettingsActivity;
import com.company.Helance.activity.editProfile.EditProfileActivity;
import com.company.Helance.interfaces.Activity;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.adapter.ProfileFragmentPagerAdapter;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyResumeViewModel;
import com.company.Helance.activity.lobby.forAuthorizedUsers.navigationFragment.profileNavFragment.viewModel.MyTaskViewModel;
import com.company.Helance.networking.IsOnline;
import com.company.Helance.networking.volley.Constants;
import com.company.Helance.networking.volley.request.VolleyMultipartRequest;
import com.company.Helance.settingsApp.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;


public class ProfileFragment extends Fragment implements Activity {

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
//    private View line;
    private io.reactivex.disposables.CompositeDisposable mDisposable = new CompositeDisposable();

    private final int[] imageResId = {
            R.drawable.my_profile, R.drawable.my_task, R.drawable.my_resume
    };
    private int selectedTab = 0;

    // user info
    private TextView name;
    private TextView login;
    private TextView country;

    private final int GALLERY = 1;
    private RequestQueue rQueue;
    private static final int REQUEST_PERMISSION = 200;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        findViewById();
        typeRefresh();
        onListener();

        initData();
        initFragments(viewPager);
        requestMultiplePermissions();

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
//        line = view.findViewById(R.id.fragment_profile_view);
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
//            line.setVisibility(View.GONE);
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (IsOnline.getInstance().isConnectingToInternet(Objects.requireNonNull(getContext()))) {
                    initData();
                    initFragments(viewPager);
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
//                line.setVisibility(View.VISIBLE);
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
        myTaskViewModel.getCountOfUsersTask(
                String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin())
        ).observe(this, integer -> {
            tasksCount.setText(String.valueOf(integer));
        });
        resumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);
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
        avatar.setVisibility(View.VISIBLE);

        if (!SharedPrefManager.getInstance(getContext()).getUserAvatar().equals("nullk")) {
            byte[] decodedString = Base64.decode(String.valueOf(SharedPrefManager.getInstance(getContext()).getUserAvatar()), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            avatar.setImageBitmap(decodedByte);
        } else {
            avatar.setVisibility(View.VISIBLE);
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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
//                    avatar.setImageBitmap(bitmap);
                    avatar.setVisibility(View.VISIBLE);
                    SharedPrefManager.getInstance(getContext()).userUpdateImage(encodeToBase64(bitmap));
                    uploadImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap image1 = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image1.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    // Преобразует картинку пользователя в массив байтов(для передачи на сервер)
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // Запрашивает у пользователя разрешение на доступ к галерее
    private void requestMultiplePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error ->
                        Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    // Запрос на загрузку, на сервер...
    private void uploadImage(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.URL.UPLOAD_AVATAR,
                response -> {
                    Log.d("ressssssoo", new String(response.data));
                    rQueue.getCache().clear();

                    try {
                        JSONObject jsonObject = new JSONObject(new String(response.data));
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error ->
                        Toast.makeText(getContext(), "Please on Internet", Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login", String.valueOf(SharedPrefManager.getInstance(getContext()).getUserLogin()));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imageName = System.currentTimeMillis();
                params.put("filename", new VolleyMultipartRequest.DataPart(imageName + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(volleyMultipartRequest);
    }

    private void initFragments(ViewPager viewPager) {
        ProfileFragmentPagerAdapter adapter = new ProfileFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        for (int i = 0; i < imageResId.length; i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setIcon(imageResId[i]);
        }
    }
}
