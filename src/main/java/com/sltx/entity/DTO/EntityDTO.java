package com.sltx.entity.DTO;


import java.util.ArrayList;

public class EntityDTO {

    String id;

    String content;

    String uploader;

    String uptime;

    ArrayList cooperator;

    ArrayList uploadPermission;

    ArrayList downloadPermission;

    String CID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public ArrayList getCooperator() {
        return cooperator;
    }

    public void setCooperator(ArrayList cooperator) {
        this.cooperator = cooperator;
    }

    public ArrayList getUploadPermission() {
        return uploadPermission;
    }

    public void setUploadPermission(ArrayList uploadPermission) {
        this.uploadPermission = uploadPermission;
    }

    public ArrayList getDownloadPermission() {
        return downloadPermission;
    }

    public void setDownloadPermission(ArrayList downloadPermission) {
        this.downloadPermission = downloadPermission;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }
}
