package com.company.Helance.dataBase.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import com.company.Helance.dto.SocialNetworkDTO;

import java.util.List;

@Dao
public interface NetworkDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertNetworks(List<SocialNetworkDTO> socialNetworkDTOS);

}
