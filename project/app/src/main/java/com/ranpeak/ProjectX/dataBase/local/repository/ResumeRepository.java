package com.ranpeak.ProjectX.dataBase.local.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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
    private static Flowable<List<ResumeDTO>> allUsersResumes;
    private static Flowable<List<ResumeDTO>> allNotUsersResumes;
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

    private static class InsertResumeAsyncTask extends AsyncTask<ResumeDTO, Void, Void> {
        private ResumeDAO resumeDAO;

        private InsertResumeAsyncTask(ResumeDAO resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected Void doInBackground(ResumeDTO... resumes) {
            resumeDAO.insert(resumes[0]);
            return null;
        }
    }

    private static class UpdateResumeAsyncTask extends AsyncTask<ResumeDTO, Void, Void> {
        private ResumeDAO resumeDAO;

        private UpdateResumeAsyncTask(ResumeDAO resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected Void doInBackground(ResumeDTO... resumes) {
            resumeDAO.update(resumes[0]);
            return null;
        }
    }

    private static class DeleteResumeAsyncTask extends AsyncTask<ResumeDTO, Void, Void> {
        private ResumeDAO resumeDAO;

        private DeleteResumeAsyncTask(ResumeDAO resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected Void doInBackground(ResumeDTO... resumes) {
            resumeDAO.delete(resumes[0]);
            return null;
        }
    }

//    private static class GetAllUsersResumesAsyncTask extends AsyncTask<String, Void, LiveData<List<ResumeDTO>>> {
//        private ResumeDAO resumeDAO;
//
//        private GetAllUsersResumesAsyncTask(ResumeDAO resumeDAO) {
//            this.resumeDAO = resumeDAO;
//        }
//
//        @Override
//        protected LiveData<List<ResumeDTO>> doInBackground(String... userLogin) {
//            return resumeDAO.getAllUserResumes(userLogin[0]);
//        }
//
//        @Override
//        protected void onPostExecute(LiveData<List<ResumeDTO>> data) {
//            allUsersResumes = data;
//        }
//    }

    private static class GetAllNotUsersResumesAsyncTask extends AsyncTask<String, Void, Flowable<List<ResumeDTO>>> {
        private ResumeDAO resumeDAO;

        private GetAllNotUsersResumesAsyncTask(ResumeDAO resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected Flowable<List<ResumeDTO>> doInBackground(String... userLogin) {
            return resumeDAO.getAllNotUserResumes(userLogin[0]);
        }

        @Override
        protected void onPostExecute(Flowable<List<ResumeDTO>> data) {
            allNotUsersResumes = data;
        }
    }
}
