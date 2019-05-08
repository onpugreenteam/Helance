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

import io.reactivex.Flowable;
import retrofit2.http.GET;

@Dao
public interface ResumeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<ResumeDTO> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ResumeDTO resumeDTO);

    @Update
    void update(ResumeDTO resumeDTO);

    @Delete
    void delete(ResumeDTO resumeDTO);

    @Query("DELETE FROM ResumeEntity")
    void deleteAllResumes();

    @Query("SELECT * FROM ResumeEntity")
    Flowable<List<ResumeDTO>> getAllResumes();

    @Query("SELECT * FROM ResumeEntity WHERE userLogin=:userLogin")
    Flowable<List<ResumeDTO>> getAllUserResumes(String userLogin);

    @Query("SELECT * FROM ResumeEntity WHERE id=:id")
    Flowable<ResumeDTO> getResumeById(long id);

    @Query("SELECT * FROM ResumeEntity WHERE userLogin<>:userLogin")
    Flowable<List<ResumeDTO>> getAllNotUserResumes(String userLogin);

    @Query("SELECT COUNT(*) FROM ResumeEntity WHERE userLogin=:userLogin")
    LiveData<Integer> getCountOfUsersResumes(String userLogin);
}
