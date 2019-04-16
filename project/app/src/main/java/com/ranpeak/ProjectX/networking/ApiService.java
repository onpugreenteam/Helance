package com.ranpeak.ProjectX.networking;

import com.ranpeak.ProjectX.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("getAllTasks")
    Observable<List<TaskDTO>> getAllTask();
}
