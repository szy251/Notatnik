package com.example.notatnik.data;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "data")
public class Data extends OrmObject {
    @PrimaryKey(autoGenerate = true)
    private Integer DataId;

    private Integer alarmId;
    /*private Date data;*/
    private String nazwa, typ, dzien;
    private Boolean pon, wt,sr, czw, pt,sob, nd, alarm, powtorz;

    public Data() {
    }


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

    public Integer getDataId() {
        return DataId;
    }

    public String getTyp() {
        return typ;
    }

    public Boolean getAlarm(){return alarm;}


    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public Boolean getPon() {
        return pon;
    }

    public void setPon(Boolean pon) {
        this.pon = pon;
    }

    public Boolean getWt() {
        return wt;
    }

    public void setWt(Boolean wt) {
        this.wt = wt;
    }

    public Boolean getSr() {
        return sr;
    }

    public void setSr(Boolean sr) {
        this.sr = sr;
    }

    public Boolean getCzw() {
        return czw;
    }

    public void setCzw(Boolean czw) {
        this.czw = czw;
    }

    public Boolean getPt() {
        return pt;
    }

    public void setPt(Boolean pt) {
        this.pt = pt;
    }

    public Boolean getSob() {
        return sob;
    }

    public void setSob(Boolean sob) {
        this.sob = sob;
    }

    public Boolean getNd() {
        return nd;
    }

    public void setNd(Boolean nd) {
        this.nd = nd;
    }

    public String getDzien() {
        return dzien;
    }

    public void setDzien(String dzien) {
        this.dzien = dzien;
    }

    public Boolean getPowtorz() {
        return powtorz;
    }

    public void setPowtorz(Boolean powtorz) {
        this.powtorz = powtorz;
    }
}
