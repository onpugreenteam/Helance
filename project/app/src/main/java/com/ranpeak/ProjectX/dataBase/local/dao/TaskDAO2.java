package com.ranpeak.ProjectX.dataBase.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ranpeak.ProjectX.dataBase.local.dto.Task;

import java.util.List;

@Dao
public interface TaskDAO2 {

    @Insert
    void insert(Task taskDTO);

    @Update
    void update(Task taskDTO);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();

    @Delete
    void delete(Task taskDTO);

    @Query("SELECT * FROM Task WHERE userLogin=:userLogin")
    LiveData<List<Task>> getAllUserTasks(String userLogin);

    @Query("SELECT * FROM Task WHERE userLogin<>:userLogin")
    LiveData<List<Task>> getAllNotUserTasks(String userLogin);

    @Query("SELECT COUNT(*) FROM Task WHERE userLogin=:userLogin")
    LiveData<Integer> getCountOfUsersTask(String userLogin);
}
