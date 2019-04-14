package com.ranpeak.ProjectX.dataBase.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<TaskDTO> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskDTO taskDTO);

    @Update
    void update(TaskDTO taskDTO);

    @Query("DELETE FROM TaskEntity")
    void deleteAllTasks();

    @Query("SELECT * FROM TaskEntity")
    Flowable<List<TaskDTO>> getAllTasks();

    @Query("SELECT * FROM TaskEntity WHERE subject = :subject")
    Flowable<List<TaskDTO>> getAllTasksForYou(String subject);

}
