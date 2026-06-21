package com.itheima.model;

public class Size {
    private int id;        //编号
    private String sizeName;  //尺码名称
    private int typeId;      //服装类别

    public Size() {
    }

    public Size(int id, String sizeName, int typeId) {
        this.id = id;
        this.sizeName = sizeName;
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
