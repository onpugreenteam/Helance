package com.ranpeak.ProjectX.networking.retrofit;

import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.dto.pojo.ResumePOJO;
import com.ranpeak.ProjectX.dto.pojo.TaskPOJO;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("getAllTasks")
    Observable<List<TaskDTO>> getAllTask();

    @GET("getAllResumes")
    Observable<List<ResumeDTO>> getAllResumes();

    @GET("getAllSocialNetworks/for/user/{login}")
    Observable<List<SocialNetworkDTO>> getAllUserNetworks(@Path("login") String login);

    @DELETE("deleteTask/{taskId}")
    Call<TaskDTO> deleteTask(@Path("taskId") long id);

    @DELETE("deleteResume/{taskId}")
    Call<ResumeDTO> deleteResume(@Path("taskId") long id);

    @POST("updateTask")
    Call<TaskPOJO> updateTask (@Body TaskPOJO taskDTO);

    @POST("updateResume")
    Call<ResumePOJO> updateResume (@Body ResumePOJO resumePOJO);
}
