package com.ranpeak.ProjectX.dataBase.local.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.ResumeDAO;
import com.ranpeak.ProjectX.dto.ResumeDTO;

import java.util.List;

import io.reactivex.Flowable;

public class ResumeRepository {
    private ResumeDAO resumeDAO;
    private Flowable<List<ResumeDTO>> allResumes;
    private static LiveData<List<ResumeDTO>> allUsersResumes;
    private static LiveData<List<ResumeDTO>> allNotUsersResumes;
    private static LiveData<Integer> countOfUsersResumes;

    public ResumeRepository(Application application) {
        LocalDB database = LocalDB.getInstance(application);
        resumeDAO = database.resumeDAO();
        allResumes = resumeDAO.getAllResumes();
    }

    public void insert(ResumeDTO resume) {
        new InsertResumeAsyncTask(resumeDAO).execute(resume);
    }

    public void update(ResumeDTO resume) {
        new UpdateResumeAsyncTask(resumeDAO).execute(resume);
    }

    public void delete(ResumeDTO resume) {
        new DeleteResumeAsyncTask(resumeDAO).execute(resume);
    }

    public Flowable<List<ResumeDTO>> getAllResumes() {
        return allResumes;
    }

    public LiveData<Integer> getCountOfUsersResumes(String userLogin) {
        countOfUsersResumes = resumeDAO.getCountOfUsersResumes(userLogin);
        return countOfUsersResumes;
    }

    public LiveData<List<ResumeDTO>> getAllUsersTask (String userLogin) {
        new GetAllUsersResumesAsyncTask(resumeDAO).execute(userLogin);
        return allUsersResumes;
    }

    public LiveData<List<ResumeDTO>> getAllNotUsersTask (String userLogin) {
        new GetAllNotUsersResumesAsyncTask(resumeDAO).execute(userLogin);
        return allNotUsersResumes;
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

    private static class GetAllUsersResumesAsyncTask extends AsyncTask<String, Void, LiveData<List<ResumeDTO>>> {
        private ResumeDAO resumeDAO;

        private GetAllUsersResumesAsyncTask(ResumeDAO resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected LiveData<List<ResumeDTO>> doInBackground(String... userLogin) {
            return resumeDAO.getAllUserResumes(userLogin[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<ResumeDTO>> data) {
            allUsersResumes = data;
        }
    }

    private static class GetAllNotUsersResumesAsyncTask extends AsyncTask<String, Void, LiveData<List<ResumeDTO>>> {
        private ResumeDAO resumeDAO;

        private GetAllNotUsersResumesAsyncTask(ResumeDAO resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected LiveData<List<ResumeDTO>> doInBackground(String... userLogin) {
            return resumeDAO.getAllNotUserResumes(userLogin[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<ResumeDTO>> data) {
            allNotUsersResumes = data;
        }
    }
}
