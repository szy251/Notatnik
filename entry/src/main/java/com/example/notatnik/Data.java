package com.example.notatnik;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "data")
public class Data extends OrmObject {
    @PrimaryKey(autoGenerate = true)
    private Integer DataId;

    /*private Date data;*/
    private String nazwa;
    private String tresc;

   /* public void setData(Date data) {
        this.data = data;
    }*/

    public void setDataId(Integer dataId) {
        DataId = dataId;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }
    public String getNazwa() {
        return nazwa;
    }
    /*public Date getData() {
        return data;
    }*/

    public Integer getDataId() {
        return DataId;
    }

    public String getTresc() {
        return tresc;
    }
}
