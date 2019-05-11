package com.ranpeak.ProjectX.dataBase.local.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.ranpeak.ProjectX.dataBase.App;
import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.ResumeDAO;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.pojo.ResumePOJO;
import com.ranpeak.ProjectX.networking.retrofit.ApiService;
import com.ranpeak.ProjectX.networking.retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeRepository {
    private ResumeDAO resumeDAO;
    private Flowable<List<ResumeDTO>> allResumes;
    private static LiveData<Integer> countOfUsersResumes;
    private ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);


    public ResumeRepository(Application application) {
        LocalDB database = App.getInstance().getLocalDB();
        resumeDAO = database.resumeDAO();
        allResumes = resumeDAO.getAllResumes();
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

    public void update(ResumeDTO resume) {
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

    public void delete(ResumeDTO resume) {
//        new DeleteResumeAsyncTask(resumeDAO).execute(resume);
        Completable.fromRunnable(() -> resumeDAO.delete(resume))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        Call<ResumeDTO> deleteReq = apiService.deleteResume(resume.getId());
        deleteReq.enqueue(new Callback<ResumeDTO>() {
            @Override
            public void onResponse(Call<ResumeDTO> call, Response<ResumeDTO> response) {

            }

            @Override
            public void onFailure(Call<ResumeDTO> call, Throwable t) {

            }
        });
    }

    public Flowable<List<ResumeDTO>> getAllResumes() {
        return allResumes;
    }

    public LiveData<Integer> getCountOfUsersResumes(String userLogin) {
        countOfUsersResumes = resumeDAO.getCountOfUsersResumes(userLogin);
        return countOfUsersResumes;
//        return resumeDAO.getCountOfUsersResumes(userLogin);
    }

    public Flowable<List<ResumeDTO>> getAllUsersResume(String userLogin) {
//        new GetAllUsersResumesAsyncTask(resumeDAO).execute(userLogin);
//        return allUsersResumes;
        return resumeDAO.getAllUserResumes(userLogin);
    }

    public Flowable<ResumeDTO> getResumeById (long userLogin) {
//        new GetAllUsersTasksAsyncTask(taskDao).execute(userLogin);
//        return allUsersTasks;
        return resumeDAO.getResumeById(userLogin);
    }

    public Flowable<List<ResumeDTO>> getAllNotUsersResume (String userLogin) {
//        new GetAllNotUsersResumesAsyncTask(resumeDAO).execute(userLogin);
//        return allNotUsersResumes;
        return resumeDAO.getAllNotUserResumes(userLogin);

    }
}
