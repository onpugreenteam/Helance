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
public interface ResumeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<ResumeDTO> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertResumes(ResumeDTO resumeDTO);

    @Update
    void update(ResumeDTO resumeDTO);

    @Query("DELETE FROM ResumeEntity")
    void deleteAllResumes();


    @Query("SELECT * FROM ResumeEntity")
    Flowable<List<ResumeDTO>> getAllResumes();
}
