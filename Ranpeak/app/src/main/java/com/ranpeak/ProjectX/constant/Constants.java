package com.ranpeak.ProjectX.constant;

import org.springframework.http.HttpStatus;

public class Constants {

    /**Запросы который которые принимает сервер**/
   public static class URL {

//       private static final String HOST = "https://projectx174.herokuapp.com/";

       private static final String HOST = "http://192.168.0.103:8080/";

       public static final String GET_USER = HOST + "getallusers";
       public static final String POST_USER = HOST + "add/registered";
       public static final String LOGIN_USER = HOST + "login";
   }
}
