package com.ranpeak.ProjectX.dataBase.local.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ranpeak.ProjectX.dataBase.local.dto.Resume;

import java.util.List;

@Dao
public interface ResumeDAO2 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Resume> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Resume resume);

    @Update
    void update(Resume resume);

    @Delete
    void delete(Resume resume);

    @Query("DELETE FROM Resume")
    void deleteAllResumes();

    @Query("SELECT * FROM Resume")
    LiveData<List<Resume>> getAllResumes();

    @Query("SELECT * FROM Resume WHERE employee=:userLogin")
    LiveData<List<Resume>> getAllUserResumes(String userLogin);

    @Query("SELECT * FROM Resume WHERE employee<>:userLogin")
    LiveData<List<Resume>> getAllNotUserResumes(String userLogin);

    @Query("SELECT COUNT(*) FROM Resume WHERE employee=:userLogin")
    LiveData<Integer> getCountOfUsersResumes(String userLogin);
}
