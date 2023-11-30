package com.example.notatnik.data;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "data4")
public class OpcjeData extends OrmObject{
    @PrimaryKey(autoGenerate = true)
    private Integer OpcjeDataId;

    private Float textSize;

    private Integer NormalTytId, ListTytId, PrzycTloId, CheckedListId, SortowTyp;
    private String slot;
    public Integer getOpcjeDataId() {
        return OpcjeDataId;
    }

    public void setOpcjeDataId(Integer opcjeDataId) {
        OpcjeDataId = opcjeDataId;
    }


    public Float getTextSize() {
        return textSize;
    }

    public void setTextSize(Float textSize) {
        this.textSize = textSize;
    }

    public Integer getNormalTytId() {
        return NormalTytId;
    }

    public void setNormalTytId(Integer normalTytId) {
        NormalTytId = normalTytId;
    }

    public Integer getListTytId() {
        return ListTytId;
    }

    public void setListTytId(Integer listTytId) {
        ListTytId = listTytId;
    }

    public Integer getPrzycTloId() {
        return PrzycTloId;
    }

    public void setPrzycTloId(Integer przycTloId) {
        PrzycTloId = przycTloId;
    }

    public Integer getCheckedListId() {
        return CheckedListId;
    }

    public void setCheckedListId(Integer checkedListId) {
        CheckedListId = checkedListId;
    }

    public Integer getSortowTyp() {
        return SortowTyp;
    }

    public void setSortowTyp(Integer sortowTyp) {
        SortowTyp = sortowTyp;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}