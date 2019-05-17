package com.company.Helance.dataBase.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.company.Helance.dto.MyTaskDTO;
import com.company.Helance.dto.TaskDTO;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<TaskDTO> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsersTasks(List<MyTaskDTO> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskDTO taskDTO);

    @Update
    void update(MyTaskDTO myTaskDTO);

    @Delete
    void delete(MyTaskDTO taskDTO);

    @Query("DELETE FROM TaskEntity")
    void deleteAllTasks();

    @Query("DELETE FROM MyTaskEntity")
    void deleteAllUsersTasks();

    @Query("SELECT * FROM TaskEntity")
    Flowable<List<TaskDTO>> getAllTasks();

    @Query("SELECT * FROM TaskEntity WHERE subject = :subject")
    Flowable<List<TaskDTO>> getAllTasksForYou(String subject);

    @Query("SELECT * FROM MyTaskEntity")
    Flowable<List<MyTaskDTO>> getAllUsersTasks();

    @Query("SELECT * FROM MyTaskEntity WHERE id=:id")
    Flowable<MyTaskDTO> getTaskById(long id);

    @Query("SELECT * FROM TaskEntity WHERE userLogin<>:userLogin")
    Flowable<List<TaskDTO>> getAllNotUserTasks(String userLogin);

    @Query("SELECT COUNT(*) FROM MyTaskEntity WHERE userLogin=:userLogin")
    LiveData<Integer> /* Flowable<Integer>*/ getCountOfUsersTask(String userLogin);
}
