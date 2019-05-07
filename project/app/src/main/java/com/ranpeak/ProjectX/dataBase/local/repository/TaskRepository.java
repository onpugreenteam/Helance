package com.ranpeak.ProjectX.dataBase.local.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ranpeak.ProjectX.dataBase.local.LocalDB;
import com.ranpeak.ProjectX.dataBase.local.dao.TaskDAO2;
import com.ranpeak.ProjectX.dataBase.local.dto.Task;

import java.util.List;

public class TaskRepository {
    private TaskDAO2 taskDao;
    private LiveData<List<Task>> allTasks;
    private static LiveData<List<Task>> allUsersTasks;
    private static LiveData<List<Task>> allNotUsersTasks;
    private static LiveData<Integer> countOfUsersTask;

    public TaskRepository(Application application) {
        LocalDB database = LocalDB.getInstance(application);
        taskDao = database.taskDao2();
        allTasks = taskDao.getAllTasks();
    }

    public void insert(Task taskDTO) {
        new InsertTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public void update(Task taskDTO) {
        new UpdateTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public void delete(Task taskDTO) {
        new DeleteTaskAsyncTask(taskDao).execute(taskDTO);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Integer> getCountOfUserTask(String userLogin) {
        countOfUsersTask = taskDao.getCountOfUsersTask(userLogin);
        return countOfUsersTask;
    }

    public LiveData<List<Task>> getAllUsersTask (String userLogin) {
        new GetAllUsersTasksAsyncTask(taskDao).execute(userLogin);
        return allUsersTasks;
    }

    public LiveData<List<Task>> getAllNotUsersTask (String userLogin) {
        new GetAllNotUsersTasksAsyncTask(taskDao).execute(userLogin);
        return allNotUsersTasks;
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO2 taskDao;

        private InsertTaskAsyncTask(TaskDAO2 taskDAO) {
            this.taskDao = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... taskDTOS) {
            taskDao.insert(taskDTOS[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO2 taskDAO;

        private UpdateTaskAsyncTask(TaskDAO2 taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... taskDTOS) {
            taskDAO.update(taskDTOS[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDAO2 taskDAO;

        private DeleteTaskAsyncTask(TaskDAO2 taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected Void doInBackground(Task... taskDTOS) {
            taskDAO.delete(taskDTOS[0]);
            return null;
        }
    }

    private static class GetAllUsersTasksAsyncTask extends AsyncTask<String, Void, LiveData<List<Task>>> {
        private TaskDAO2 taskDAO;

        private GetAllUsersTasksAsyncTask(TaskDAO2 taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(String... userLogin) {
            return taskDAO.getAllUserTasks(userLogin[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<Task>> data) {
            allUsersTasks = data;
        }
    }

    private static class GetAllNotUsersTasksAsyncTask extends AsyncTask<String, Void, LiveData<List<Task>>> {
        private TaskDAO2 taskDAO;

        private GetAllNotUsersTasksAsyncTask(TaskDAO2 taskDAO) {
            this.taskDAO = taskDAO;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(String... userLogin) {
            return taskDAO.getAllNotUserTasks(userLogin[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<Task>> data) {
            allNotUsersTasks = data;
        }
    }
}
