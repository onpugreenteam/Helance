package com.ranpeak.ProjectX.networking.retrofit;

import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("getAllTasks")
    Observable<List<TaskDTO>> getAllTask();

    @GET("getAllResumes")
    Observable<List<ResumeDTO>> getAllResumes();
}
