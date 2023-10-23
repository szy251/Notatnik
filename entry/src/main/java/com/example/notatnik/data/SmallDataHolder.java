package com.example.notatnik.data;

import java.util.List;

public class SmallDataHolder{
    private static SmallDataHolder instance;
    private Data data;
    private NormalNot normalNot;

    private List<ListNot> listNots;
    SmallDataHolder(){

    }
    public static SmallDataHolder getInstance(){
        if(instance == null){
            instance = new SmallDataHolder();
        }
        return instance;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setNormalNot(NormalNot normalNot) {
        this.normalNot = normalNot;
    }

    public Data getData() {
        return data;
    }

    public NormalNot getNormalNot() {
        return normalNot;
    }

    public List<ListNot> getListNots() {
        return listNots;
    }

    public void setListNots(List<ListNot> listNots) {
        this.listNots = listNots;
    }
}