package com.ranpeak.ProjectX.dataBase.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ranpeak.ProjectX.dto.MyResumeDTO;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsersResumes(List<MyResumeDTO> myResumeDTOS);

    @Update
    void update(MyResumeDTO resumeDTO);

    @Delete
    void delete(MyResumeDTO resumeDTO);

    @Query("DELETE FROM ResumeEntity")
    void deleteAllResumes();

    @Query("SELECT * FROM ResumeEntity")
    Flowable<List<ResumeDTO>> getAllResumes();

    @Query("SELECT * FROM MyResumeEntity WHERE userLogin=:userLogin")
    Flowable<List<MyResumeDTO>> getAllUserResumes(String userLogin);

    @Query("SELECT * FROM MyResumeEntity WHERE id=:id")
    Flowable<MyResumeDTO> getResumeById(long id);

    @Query("SELECT * FROM MyResumeEntity WHERE userLogin<>:userLogin")
    Flowable<List<MyResumeDTO>> getAllNotUserResumes(String userLogin);

    @Query("SELECT COUNT(*) FROM MyResumeEntity WHERE userLogin=:userLogin")
    LiveData<Integer> getCountOfUsersResumes(String userLogin);

    @Query("DELETE FROM MyResumeEntity")
    void deleteAllUsersResumes();


}
