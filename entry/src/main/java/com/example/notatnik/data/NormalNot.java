package com.example.notatnik.data;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "data3")
public class NormalNot extends OrmObject {
    @PrimaryKey()
    private Integer dataParentId;

    private String tresc;

    public void setDataParentId(Integer dataParentId) {this.dataParentId = dataParentId;}
    public void setTresc(String tresc){this.tresc = tresc;}

    public Integer getDataParentId(){return this.dataParentId;}
    public String getTresc(){return this.tresc;}
}