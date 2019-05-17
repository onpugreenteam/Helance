package com.company.Helance.dataBase.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.company.Helance.dto.SocialNetworkDTO;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ProfileDAO {

    @Query("select * from NetworksEntity n")
    Flowable<List<SocialNetworkDTO>> getAllSocialNetworks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SocialNetworkDTO socialNetworkDTO);

    @Query("DELETE FROM NetworksEntity")
    void deleteAllSocialNetworks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllSocialNetworks(List<SocialNetworkDTO> items);

}
