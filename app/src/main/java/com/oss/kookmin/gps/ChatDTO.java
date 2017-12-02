package com.oss.kookmin.gps;
//체팅에서 유저아이디와 메세지를 묶어서 보여줄 수 있도록 클레스 생성
public class ChatDTO {

    private String userName;
    private String message;

    public ChatDTO() {}
    public ChatDTO(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }
}