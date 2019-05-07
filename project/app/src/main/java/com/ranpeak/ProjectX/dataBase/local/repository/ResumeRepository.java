package com.ranpeak.ProjectX.dataBase.local.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.ResumeDAO2;
import com.ranpeak.ProjectX.dataBase.local.dto.Resume;

import java.util.List;

public class ResumeRepository {
    private ResumeDAO2 resumeDAO;
    private LiveData<List<Resume>> allResumes;
    private static LiveData<List<Resume>> allUsersResumes;
    private static LiveData<List<Resume>> allNotUsersResumes;
    private static LiveData<Integer> countOfUsersResumes;

    public ResumeRepository(Application application) {
        LocalDB database = LocalDB.getInstance(application);
        resumeDAO = database.resumeDAO2();
        allResumes = resumeDAO.getAllResumes();
    }

    public void insert(Resume resume) {
        new InsertResumeAsyncTask(resumeDAO).execute(resume);
    }

    public void update(Resume resume) {
        new UpdateResumeAsyncTask(resumeDAO).execute(resume);
    }

    public void delete(Resume resume) {
        new DeleteResumeAsyncTask(resumeDAO).execute(resume);
    }

    public LiveData<List<Resume>> getAllResumes() {
        return allResumes;
    }

    public LiveData<Integer> getCountOfUsersResumes(String userLogin) {
        countOfUsersResumes = resumeDAO.getCountOfUsersResumes(userLogin);
        return countOfUsersResumes;
    }

    public LiveData<List<Resume>> getAllUsersTask (String userLogin) {
        new GetAllUsersResumesAsyncTask(resumeDAO).execute(userLogin);
        return allUsersResumes;
    }

    public LiveData<List<Resume>> getAllNotUsersTask (String userLogin) {
        new GetAllNotUsersResumesAsyncTask(resumeDAO).execute(userLogin);
        return allNotUsersResumes;
    }

    private static class InsertResumeAsyncTask extends AsyncTask<Resume, Void, Void> {
        private ResumeDAO2 resumeDAO;

        private InsertResumeAsyncTask(ResumeDAO2 resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected Void doInBackground(Resume... resumes) {
            resumeDAO.insert(resumes[0]);
            return null;
        }
    }

    private static class UpdateResumeAsyncTask extends AsyncTask<Resume, Void, Void> {
        private ResumeDAO2 resumeDAO;

        private UpdateResumeAsyncTask(ResumeDAO2 resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected Void doInBackground(Resume... resumes) {
            resumeDAO.update(resumes[0]);
            return null;
        }
    }

    private static class DeleteResumeAsyncTask extends AsyncTask<Resume, Void, Void> {
        private ResumeDAO2 resumeDAO;

        private DeleteResumeAsyncTask(ResumeDAO2 resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected Void doInBackground(Resume... resumes) {
            resumeDAO.delete(resumes[0]);
            return null;
        }
    }

    private static class GetAllUsersResumesAsyncTask extends AsyncTask<String, Void, LiveData<List<Resume>>> {
        private ResumeDAO2 resumeDAO;

        private GetAllUsersResumesAsyncTask(ResumeDAO2 resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected LiveData<List<Resume>> doInBackground(String... userLogin) {
            return resumeDAO.getAllUserResumes(userLogin[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<Resume>> data) {
            allUsersResumes = data;
        }
    }

    private static class GetAllNotUsersResumesAsyncTask extends AsyncTask<String, Void, LiveData<List<Resume>>> {
        private ResumeDAO2 resumeDAO;

        private GetAllNotUsersResumesAsyncTask(ResumeDAO2 resumeDAO) {
            this.resumeDAO = resumeDAO;
        }

        @Override
        protected LiveData<List<Resume>> doInBackground(String... userLogin) {
            return resumeDAO.getAllNotUserResumes(userLogin[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<Resume>> data) {
            allNotUsersResumes = data;
        }
    }
}
