package com.example.notatnik.data;

public class SmallDataHolder{
    private static SmallDataHolder instance;
    private Data data;
    private NormalNot normalNot;
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
}