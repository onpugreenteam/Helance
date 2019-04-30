package com.ranpeak.ProjectX.networking.retrofit;

import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("getAllTasks")
    Observable<List<TaskDTO>> getAllTask();

    @GET("getAllResumes")
    Observable<List<ResumeDTO>> getAllResumes();

    @GET("getAllSocialNetworks/for/user/{login}")
    Observable<List<SocialNetworkDTO>> getAllUserNetworks(@Path("login") String login);
}
