package com.oss.kookmin.gps;
//회원목록에서 각 회원에게 들어갈 5개의 값들을 넣어 줄 수 있도록 만든 클레스
public class User {

    String userID;
    String userName;
    String userAge;
    String userLanguage;
    String userResidence;

    public User(String userID, String userName, String userAge, String userLanguage, String userResidence) {
        this.userID = userID;
        this.userName = userName;
        this.userAge = userAge;
        this.userLanguage = userLanguage;
        this.userResidence = userResidence;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getUserResidence() {
        return userResidence;
    }

    public void setUserResidence(String userResidence) {
        this.userResidence = userResidence;
    }
}
