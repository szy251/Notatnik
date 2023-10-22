package com.example.notatnik.data;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "data2")
public class ListNot extends OrmObject {
    @PrimaryKey(autoGenerate = true)
    private Integer listNotId;

    private Integer dataParentId;
    private String nazwa;

    public ListNot(ListNot listNot) {
        this.dataParentId = listNot.dataParentId;
        this.listNotId = listNot.listNotId;
        this.nazwa = listNot.nazwa;
    }

    public void setListNotId(Integer listNotId) {this.listNotId = listNotId;}
    public void setDataParentId(Integer dataParentId) {this.dataParentId = dataParentId;}
    public void setNazwa(String nazwa){this.nazwa = nazwa;}

    public Integer getListNotId(){return this.listNotId;}
    public Integer getDataParentId(){return this.dataParentId;}
    public String getNazwa(){return this.nazwa;}
}