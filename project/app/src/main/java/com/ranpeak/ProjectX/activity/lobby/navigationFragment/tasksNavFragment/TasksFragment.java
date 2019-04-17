package com.ranpeak.ProjectX.activity.lobby.navigationFragment.tasksNavFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranpeak.ProjectX.R;
import com.ranpeak.ProjectX.activity.creatingTask.CreatingTaskActivity;
import com.ranpeak.ProjectX.activity.interfaces.Activity;
import com.ranpeak.ProjectX.activity.lobby.navigationFragment.tasksNavFragment.adapter.TaskListAdapter;
import com.ranpeak.ProjectX.networking.ApiService;
import com.ranpeak.ProjectX.networking.Constants;
import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.TaskDAO;

import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.networking.RetrofitClient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;



public class TasksFragment extends Fragment implements Activity {


    private View view;
    private List<TaskDTO> data = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskListAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LocalDB localDB;
    private TaskDAO taskDAO;
    private FloatingActionButton fab;

    private ApiService apiService = RetrofitClient.getInstance()
            .create(ApiService.class);


    public TasksFragment() {
    }


    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tasks, container, false);
        findViewById();
        onListener();
        initImageBitmaps();

        localDB = App.getInstance().getLocalDB();
        taskDAO = localDB.taskDao();

        taskDAO.getAllTasks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskDTOS ->
                        Log.d("Data size in LocalDB", String.valueOf(taskDTOS.size())));

//        new GetFreeTask().execute();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskListAdapter(data, imageUrls, recyclerView, getActivity());
        recyclerView.setAdapter(adapter);

        getTasksFromServer();

//        adapter.setLoadMore(new ILoadMore() {
//            @Override
//            public void onLoadMore() {
//                if (data.size() <= 5) {
//                    data.add(null);
//                    adapter.notifyItemInserted(data.size() - 1);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            data.remove(data.size() - 1);
//                            adapter.notifyItemRemoved(data.size());
//                            adapter.notifyDataSetChanged();
//                            adapter.setLoaded();
//                        }
//                    }, 4000);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        return view;
    }


    private void initImageBitmaps() {
        imageUrls.add("https://cdn.fishki.net/upload/post/2017/03/19/2245758/01-beautiful-white-cat-imagescar-wallpaper.jpg");
        imageUrls.add("https://usionline.com/wp-content/uploads/2016/02/12-4.jpg");
        imageUrls.add("http://bm.img.com.ua/nxs/img/prikol/images/large/1/2/308321_879390.jpg");
        imageUrls.add("http://bm.img.com.ua/nxs/img/prikol/images/large/1/2/308321_879389.jpg");
        imageUrls.add("http://www.radionetplus.ru/uploads/posts/2013-05/1369460621_panda-26.jpg");
        imageUrls.add("http://v.img.com.ua/b/1100x999999/1/fc/409a3eebc81a4d8dc4a2437cbe07afc1.jpg");
        imageUrls.add("http://ztb.kz/media/imperavi/59cb70c479d20.jpg");
        imageUrls.add("https://bryansktoday.ru/uploads/common/dcbf021231e742e6_XL.jpg");
        imageUrls.add("https://ki.ill.in.ua/m/670x450/24227758.jpg");
        imageUrls.add("https://cs9.pikabu.ru/post_img/2017/10/06/7/1507289738144386744.jpg");
        imageUrls.add("https://placepic.ru/uploads/posts/2014-03/1396234652_podborka-realnyh-pacanov-2.jpg");
        imageUrls.add("http://2queens.ru/Uploads/Yelizaveta/%D1%80%D0%B5%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9%20%D0%BF%D0%B0%D1%86%D0%B0%D0%BD%20%D1%80%D0%B6%D0%B0%D0%B2%D1%8B%D0%B9.jpg");
        imageUrls.add("http://bidla.net/uploads/posts/2017-06/thumbs/1496943807_urodru20170608ku7564.jpeg");
        imageUrls.add("https://www.baikal-daily.ru/upload/resize_cache/iblock/ca6/600_500_1/vovan.png");
        imageUrls.add("https://www.vokrug.tv/pic/person/b/3/6/d/b36d3d2f4c263fc18eba1a464eb942d2.jpeg");
        imageUrls.add("https://i.mycdn.me/image?id=877079192648&t=35&plc=WEB&tkn=*85PLfcQAXU8Glv9V8-xzIyJxZF4");
    }


    @Override
    public void findViewById() {
        recyclerView = view.findViewById(R.id.fragment_tasks_recycleView_tasks);
        mSwipeRefreshLayout = view.findViewById(R.id.fragment_tasks_swipeRefresh);
        fab = view.findViewById(R.id.fragment_tasks_floatingActionButton);
    }


    @Override
    public void onListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTasksFromServer();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreatingTaskActivity.class));
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }


    public static TasksFragment getInstance(List<TaskDTO> data) {
        Bundle args = new Bundle();
        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        fragment.setData(data);

        return fragment;
    }


    public static TasksFragment newInstance() {
        return new TasksFragment();
    }


    public void setData(List<TaskDTO> data) {
        this.data = data;
    }


    public class GetFreeTask extends AsyncTask<Void, Void, List<TaskDTO>> {

        @Override
        protected List<TaskDTO> doInBackground(Void... params) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<TaskDTO>> response = restTemplate.exchange(
                    Constants.URL.GET_ALL_TASK,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TaskDTO>>() {
                    });
            List<TaskDTO> taskDTOS = response.getBody();

            return taskDTOS;
        }

        @Override
        protected void onPostExecute(List<TaskDTO> taskDTOS) {
            data = taskDTOS;
            Log.d("Data Size", String.valueOf(data.size()));
            addTasksToLocalDB(data);
            adapter = new TaskListAdapter(data, imageUrls, recyclerView, getActivity());
            recyclerView.setAdapter(adapter);
        }
    }


    @SuppressLint("CheckResult")
    private void  getTasksFromServer(){

        apiService.getAllTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<TaskDTO>>() {
                    @Override
                    public void onNext(List<TaskDTO> taskDTOS) {
                        data.clear();
                        data.addAll(taskDTOS);
                        addTasksToLocalDB(data);
                        adapter.notifyDataSetChanged();
                        Log.d("Data size from server", String.valueOf(taskDTOS.size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error

                    }

                    @Override
                    public void onComplete() {
                        // Received all notes

                    }
                });
    }


    public void addTasksToLocalDB(List<TaskDTO> tasksDTOS) {
        Observable.fromCallable(() -> localDB.taskDao().insertAll(tasksDTOS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Long>>(){
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Long> longs) {
                        super.onNext(longs);
                        Timber.d("insert countries transaction complete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        Timber.d("error storing countries in db"+e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("insert countries transaction complete");
                    }
                });
    }

    public static class DefaultSubscriber<T> implements Observer<T> {

        Disposable disposable;

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(@NonNull T t) {

        }

        @Override
        public void onError(@NonNull Throwable e) {
            Timber.e(e);
        }

        @Override
        public void onComplete() {

        }

        public void unsubscribe(){
            if(disposable!=null && !disposable.isDisposed()){
                disposable.dispose();
            }
        }
    }
}
