package com.ranpeak.ProjectX.dataBase.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ranpeak.ProjectX.dataBase.local.dao.NetworkDAO;
import com.ranpeak.ProjectX.dataBase.local.dao.ResumeDAO;
import com.ranpeak.ProjectX.dataBase.local.dao.ResumeDAO2;
import com.ranpeak.ProjectX.dataBase.local.dao.TaskDAO;

import com.ranpeak.ProjectX.dataBase.local.dao.TaskDAO2;
import com.ranpeak.ProjectX.dataBase.local.dto.Resume;
import com.ranpeak.ProjectX.dataBase.local.dto.Task;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;

@Database(entities = {TaskDTO.class, ResumeDTO.class, SocialNetworkDTO.class, Task.class, Resume.class}, version = 1, exportSchema = false)
public abstract class LocalDB extends RoomDatabase {

    private static  LocalDB instance;

    public abstract TaskDAO taskDao();

    public abstract TaskDAO2 taskDao2();

    public abstract ResumeDAO resumeDAO();

    public abstract ResumeDAO2 resumeDAO2();

    public abstract NetworkDAO networkDAO();

    public static synchronized LocalDB getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LocalDB.class,"_task_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}