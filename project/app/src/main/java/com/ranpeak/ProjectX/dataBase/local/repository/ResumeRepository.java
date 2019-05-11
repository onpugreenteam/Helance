package com.ranpeak.ProjectX.dataBase.local.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.ResumeDAO;
import com.ranpeak.ProjectX.dto.MyResumeDTO;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.pojo.ResumePOJO;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeRepository {
    private ResumeDAO resumeDAO;
    private static LiveData<Integer> countOfUsersResumes;
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);


    public ResumeRepository(Application application) {
        LocalDB database = App.getInstance().getLocalDB();
        resumeDAO = database.resumeDAO();
    }

    public void insert(ResumeDTO resume) {
//        new InsertResumeAsyncTask(resumeDAO).execute(resume);
        Completable.fromRunnable(() -> {
            resumeDAO.insert(resume);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void insertAll(List<ResumeDTO>resume) {
//        new InsertResumeAsyncTask(resumeDAO).execute(resume);
        Completable.fromRunnable(() -> {
            resumeDAO.insertAll(resume);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void update(MyResumeDTO resume) {
//        new UpdateResumeAsyncTask(resumeDAO).execute(resume);
        Completable.fromRunnable(() -> {
            resumeDAO.update(resume);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        Call<ResumePOJO> call = apiService.updateResume(
                new ResumePOJO(resume.getId(),
                        resume.getSubject(),
                        resume.getDateStart(),
                        resume.getStatus(),
                        resume.getOpportunities(),
                        resume.getUserLogin(),
                        resume.getViews(),
                        resume.getTelephone()
                )
        );
        call.enqueue(new Callback<ResumePOJO>() {
            @Override
            public void onResponse(Call<ResumePOJO> call, Response<ResumePOJO> response) {

            }

            @Override
            public void onFailure(Call<ResumePOJO> call, Throwable t) {

            }
        });
    }

    public void delete(MyResumeDTO resume) {
//        new DeleteResumeAsyncTask(resumeDAO).execute(resume);
        Completable.fromRunnable(() -> resumeDAO.delete(resume))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        Call<MyResumeDTO> deleteReq = apiService.deleteResume(resume.getId());
        deleteReq.enqueue(new Callback<MyResumeDTO>() {
            @Override
            public void onResponse(Call<MyResumeDTO> call, Response<MyResumeDTO> response) {

            }

            @Override
            public void onFailure(Call<MyResumeDTO> call, Throwable t) {

            }
        });
    }
    public LiveData<Integer> getCountOfUsersResumes(String userLogin) {
        countOfUsersResumes = resumeDAO.getCountOfUsersResumes(userLogin);
        return countOfUsersResumes;
//        return resumeDAO.getCountOfUsersResumes(userLogin);
    }

    @SuppressLint("CheckResult")
    public Flowable<List<MyResumeDTO>> getAllUsersResume(String userLogin) {
        apiService.getAllUsersResumes(userLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<MyResumeDTO>>() {
                    @Override
                    public void onNext(List<MyResumeDTO> taskDTOS) {
                        refreshAllUsersResumes(taskDTOS);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Received all notes
                    }
                });
        return resumeDAO.getAllUserResumes(userLogin);
    }

    public Flowable<MyResumeDTO> getResumeById (long userLogin) {
//        new GetAllUsersTasksAsyncTask(taskDao).execute(userLogin);
//        return allUsersTasks;
        return resumeDAO.getResumeById(userLogin);
    }

    public Flowable<List<MyResumeDTO>> getAllNotUsersResume (String userLogin) {
//        new GetAllNotUsersResumesAsyncTask(resumeDAO).execute(userLogin);
//        return allNotUsersResumes;
        return resumeDAO.getAllNotUserResumes(userLogin);

    }

    private void refreshAllUsersResumes(List<MyResumeDTO> myResumeDTOS) {
        Completable.fromRunnable(() -> {
            resumeDAO.deleteAllResumes();
            resumeDAO.insertAllUsersResumes(myResumeDTOS);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
