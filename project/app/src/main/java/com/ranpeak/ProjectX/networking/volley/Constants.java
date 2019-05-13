package com.ranpeak.ProjectX.networking.volley;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.regex.Pattern;

public class Constants {

    /**Запросы который которые принимает сервер**/
   public static class URL {

       private static final String HOST = "https://projectx174.herokuapp.com/";

//       private static final String HOST = "http://192.168.0.113:8080/";

       public static final String GET_USER = HOST + "getallusers";
       public static final String POST_USER = HOST + "add/registered";
       public static final String ACTIVATE_USER = HOST + "activateAccount";
       public static final String LOGIN_USER = HOST + "login";
       public static final String CHECK_LOGIN = HOST + "checkLogin";
       public static final String CHECK_EMAIL = HOST + "checkEmail";
       public static final String UPLOAD_AVATAR = HOST + "upload/avatar";
       public static final String GET_AVATAR = HOST + "aaa/";
       public static final String GET_ALL_TASK = HOST + "getAllTasks";
       public static final String ADD_TASK = HOST + "addTask";
       public static final String DELETE_TASK = HOST + "deleteTask";
       public static final String ADD_RESUME = HOST + "addResume";
   }

    public static class Values {

        private static final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[0-9])" +         //at least 1 digit
//                        "(?=.*[a-z])" +         //at least 1 lower case letter
//                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
//                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{8,}" +               //at least 8 characters
                        "$");

        public static Pattern getPasswordPattern() {
            return PASSWORD_PATTERN;
        }

        public static final String[] LESSONS = new String[]{
                "Art",
                "Maths", "Physics", "Programming", "Economic",
                "Philosophy", "English", "History", "Other"
        };

        public static final String TELEGRAM = "Telegram";
        public static final String INSTAGRAM = "Instagram";
        public static final String FACEBOOK = "Facebook";
    }

    public static final class Codes {

        public static final int EMPTY_CODE = 0;
        public static final int DELETE_CODE = 1;
        public static final int EDIT_CODE = 2;
        public static final int DELETE_OR_EDIT_CODE = 3;

    }

    public static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false; }
    }
}
