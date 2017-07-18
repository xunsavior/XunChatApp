package com.example.xunhu.xunchat.Model.Entities;

import java.io.Serializable;

/**
 * Created by xunhu on 6/21/2017.
 */

public class User implements Serializable {
    private int userID;
    private String username;
    private String url;
    private String gender;
    private String region;
    private String whatsup;
    private int age;
    private String nickname;
    private int relationship_type;
    private String remark= "";

    public User(int userID,String username,String nickname,String url,String gender,String region,
                String whatsup,int age, int relationship_type){
        this.userID=userID;
        this.username=username;
        this.url=url;
        this.gender=gender;
        this.region=region;
        this.whatsup=whatsup;
        this.age=age;
        this.nickname=nickname;
        this.relationship_type=relationship_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
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

    public String getWhatsup() {
        return whatsup;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWhatsup(String whatsup) {
        this.whatsup = whatsup;
    }

    public int getRelationship_type() {
        return relationship_type;
    }
    public void setRelationship_type(int relationship_type) {
        this.relationship_type = relationship_type;
    }
    public int getUserID() {
        return userID;
    }
}
