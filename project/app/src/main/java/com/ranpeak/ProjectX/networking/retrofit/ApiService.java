package com.ranpeak.ProjectX.networking.retrofit;

import com.ranpeak.ProjectX.dto.MyResumeDTO;
import com.ranpeak.ProjectX.dto.MyTaskDTO;
import com.ranpeak.ProjectX.dto.ResumeDTO;
import com.ranpeak.ProjectX.dto.SocialNetworkDTO;
import com.ranpeak.ProjectX.dto.TaskDTO;
import com.ranpeak.ProjectX.dto.pojo.ResumePOJO;
import com.ranpeak.ProjectX.dto.pojo.SocialNetworkPOJO;
import com.ranpeak.ProjectX.dto.pojo.TaskPOJO;
import com.ranpeak.ProjectX.dto.pojo.UserPOJO;

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

    @GET("getAllUsersTasks/{login}")
    Observable<List<MyTaskDTO>> getAllUsersTasks(@Path("login") String userLogin);

    @GET("getAllResumes")
    Observable<List<ResumeDTO>> getAllResumes();

    @GET("getAllUsersResumes/{login}")
    Observable<List<MyResumeDTO>> getAllUsersResumes(@Path("login") String userLogin);

    @GET("getAllSocialNetworks/for/user/{login}")
    Observable<List<SocialNetworkDTO>> getAllUserNetworks(@Path("login") String login);

    @POST("addNetwork")
    Call<SocialNetworkPOJO> addUserNetwork(@Body SocialNetworkPOJO socialNetworkPOJO);

    @POST("sendMessage/{email}")
    Call<Void> sendCodeOnEmail(@Path("email") String email);

    @POST("changePassword/{email}/{password}")
    Call<Void> changePassword(@Path("email") String email, @Path("password") String password);

    @DELETE("deleteTask/{taskId}")
    Call<MyTaskDTO> deleteTask(@Path("taskId") long id);

    @DELETE("deleteResume/{taskId}")
    Call<MyResumeDTO> deleteResume(@Path("taskId") long id);

    @POST("updateTask")
    Call<TaskPOJO> updateTask (@Body TaskPOJO taskDTO);

    @POST("updateResume")
    Call<ResumePOJO> updateResume (@Body ResumePOJO resumePOJO);

    @GET("updateTask/views/{id}")
    Observable<TaskDTO> updateTaskViews(@Path("id") long id);

    @POST("updateUser/{login}/{name}/{country}/{email}/{telephone}")
    Call<UserPOJO> updateUserInfo (@Path("login") String login, @Path("name") String name,
                                   @Path("country")String country, @Path("email") String email,
                                   @Path("telephone") String telephone);

    @POST("updateSocialNetwork/{login}/{networkName}/{networkLogin}")
    Call<SocialNetworkPOJO> updateSocialNetwork (
            @Path("login") String userLogin,
            @Path("networkName") String networkName,
            @Path("networkLogin") String networkLogin);


    @DELETE("deleteSocialNetwork/{login}/{networkName}")
    Call<SocialNetworkPOJO> deleteSocialNetwork (
            @Path("networkName") String networkName, @Path("login") String login
//            @Body SocialNetworkPOJO socialNetworkPOJO
    );

    @GET("updateResume/views/{id}")
    Observable<TaskDTO> updateResumeViews(@Path("id") long id);

}
