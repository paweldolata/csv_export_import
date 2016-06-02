package com.egnyte.utils.auditreporter;


public class Record {
    public String fileName;
    public String fileSize;
    public String fileOwner;
    public String userID;
    public String fileID;

    Record(String iD, String size, String name,String useriD, String owner){
        this.fileID=iD;
        this.fileSize=size;
        this.fileName=name;
        this.userID=useriD;
        this.fileOwner=owner;
    }
}
