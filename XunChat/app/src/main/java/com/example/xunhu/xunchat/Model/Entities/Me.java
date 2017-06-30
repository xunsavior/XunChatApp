package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 6/14/2017.
 */

public class Me {
    private int id;
    private String username;
    private String url;
    private String email;
    private String gender;
    private String region;
    private String QRCode;
    private String whatsup;
    private int age;
    private String birthday;
    private String nickname;

    public Me(int id, String username,String nickname,String url,String email,String gender,String region,String QRCode,String whatsup,int age,String birthday){
        this.id=id;
        this.username=username;
        this.url=url;
        this.gender=gender;
        this.region=region;
        this.QRCode=QRCode;
        this.email=email;
        this.whatsup=whatsup;
        this.age=age;
        this.birthday=birthday;
        this.nickname=nickname;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getQRCode() {
        return QRCode;
    }

    public String getRegion() {
        return region;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getWhatsup() {
        return whatsup;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setWhatsup(String whatsup) {
        this.whatsup = whatsup;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
