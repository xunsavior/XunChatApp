package com.example.xunhu.xunchat.Model.Entities;

/**
 * Created by xunhu on 8/18/2017.
 */

public class Post {
    int postID;
    String nickName;
    String imageURL;
    String postContent;
    String caption;
    int postType;
    String timestamp;
    String location;
    int numberLikes;
    int isLiked=0;
    public Post(int postID,String nickName,
                String imageURL,String postContent,String caption,int postType
                ,String timestamp,String location){
            this.postID=postID;
            this.nickName=nickName;
            this.imageURL=imageURL;
            this.postContent=postContent;
            this.caption=caption;
            this.postType=postType;
            this.timestamp=timestamp;
            this.location=location;
    }

    public int getIsLiked() {
        return isLiked;
    }
    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }
    public int getPostID() {
        return postID;
    }
    public int getNumberLikes() {
        return numberLikes;
    }
    public String getCaption() {
        return caption;
    }

    public void setNumberLikes(int numberLikes) {
        this.numberLikes = numberLikes;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getPostType() {
        return postType;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getLocation() {
        return location;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }
}
