package com.ranpeak.ProjectX.dataBase.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;

import java.util.List;

import bolts.Task;
import io.reactivex.Flowable;

@Dao
public interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<TaskDTO> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskDTO taskDTO);

    @Update
    void update(TaskDTO taskDTO);

    @Delete
    void delete(TaskDTO taskDTO);

    @Query("DELETE FROM TaskEntity")
    void deleteAllTasks();

    @Query("SELECT * FROM TaskEntity")
    Flowable<List<TaskDTO>> getAllTasks();

    @Query("SELECT * FROM TaskEntity WHERE subject = :subject")
    Flowable<List<TaskDTO>> getAllTasksForYou(String subject);

    @Query("SELECT * FROM TaskEntity WHERE userLogin=:userLogin")
    LiveData<List<TaskDTO>> getAllUserTasks(String userLogin);

    @Query("SELECT * FROM TaskEntity WHERE userLogin<>:userLogin")
    LiveData<List<TaskDTO>> getAllNotUserTasks(String userLogin);

    @Query("SELECT COUNT(*) FROM TaskEntity WHERE userLogin=:userLogin")
    LiveData<Integer> getCountOfUsersTask(String userLogin);
}
