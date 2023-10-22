package com.example.notatnik.data;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "data")
public class Data extends OrmObject {
    @PrimaryKey(autoGenerate = true)
    private Integer DataId;

    /*private Date data;*/
    private String nazwa;
    private String typ;

    private Boolean alarm;

   /* public void setData(Date data) {
        this.data = data;
    }*/

    public void setDataId(Integer dataId) {
        DataId = dataId;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void setAlarm(Boolean alarm){this.alarm = alarm;}
    public String getNazwa() {
        return nazwa;
    }
    /*public Date getData() {
        return data;
    }*/

    public Integer getDataId() {
        return DataId;
    }

    public String getTyp() {
        return typ;
    }

    public Boolean getAlarm(){return alarm;}

}
