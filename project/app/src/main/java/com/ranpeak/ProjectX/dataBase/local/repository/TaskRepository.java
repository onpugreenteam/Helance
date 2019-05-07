package com.ranpeak.ProjectX.dataBase.local.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.TaskDAO;
import com.ranpeak.ProjectX.dto.TaskDTO;

import java.util.List;

import io.reactivex.Flowable;

public class TaskRepository {
    private TaskDAO taskDao;
    private Flowable<List<TaskDTO>> allTasks;
    private static LiveData<List<TaskDTO>> allUsersTasks;
    private static LiveData<List<TaskDTO>> allNotUsersTasks;
    private static LiveData<Integer> countOfUsersTask;

    public TaskRepository(Application application) {
        LocalDB database = LocalDB.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public void insert(TaskDTO taskDTO) {
        new InsertTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public void update(TaskDTO taskDTO) {
        new UpdateTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public void delete(TaskDTO taskDTO) {
        new DeleteTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public Flowable<List<TaskDTO>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Integer> getCountOfUserTask(String userLogin) {
        countOfUsersTask = taskDao.getCountOfUsersTask(userLogin);
        return countOfUsersTask;
    }

    public LiveData<List<TaskDTO>> getAllUsersTask (String userLogin) {
//        new GetAllUsersTasksAsyncTask(taskDao).execute(userLogin);
//        return allUsersTasks;
        return taskDao.getAllUserTasks(userLogin);
    }

    public LiveData<List<TaskDTO>> getAllNotUsersTask (String userLogin) {
        new GetAllNotUsersTasksAsyncTask(taskDao).execute(userLogin);
        return allNotUsersTasks;
    }

    private static class InsertTaskAsyncTask extends AsyncTask<TaskDTO, Void, Void> {
        private TaskDAO taskDao;

        private InsertTaskAsyncTask(TaskDAO taskDAO) {
            this.taskDao = taskDAO;
        }

        @Override
        protected Void doInBackground(TaskDTO... taskDTOS) {
            taskDao.insert(taskDTOS[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<TaskDTO, Void, Void> {
        private TaskDAO taskDAO;

        private UpdateTaskAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(TaskDTO... taskDTOS) {
            taskDAO.update(taskDTOS[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<TaskDTO, Void, Void> {
        private TaskDAO taskDAO;

        private DeleteTaskAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(TaskDTO... taskDTOS) {
            taskDAO.delete(taskDTOS[0]);
            return null;
        }
    }

    private static class GetAllUsersTasksAsyncTask extends AsyncTask<String, Void, LiveData<List<TaskDTO>>> {
        private TaskDAO taskDAO;

        private GetAllUsersTasksAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected LiveData<List<TaskDTO>> doInBackground(String... userLogin) {
            return taskDAO.getAllUserTasks(userLogin[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<TaskDTO>> data) {
            allUsersTasks = data;
        }
    }

    private static class GetAllNotUsersTasksAsyncTask extends AsyncTask<String, Void, LiveData<List<TaskDTO>>> {
        private TaskDAO taskDAO;

        private GetAllNotUsersTasksAsyncTask(TaskDAO taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected LiveData<List<TaskDTO>> doInBackground(String... userLogin) {
            return taskDAO.getAllNotUserTasks(userLogin[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<TaskDTO>> data) {
            allNotUsersTasks = data;
        }
    }
}
