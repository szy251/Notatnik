package com.example.notatnik.data;

import java.time.LocalDateTime;
import java.util.List;

public class SmallDataHolder{
    private static SmallDataHolder instance;
    private Data data;
    private NormalNot normalNot;

    private List<ListNot> listNots;
    private Byte state;
    //1 - brak zmian
    //2 - zmiana tytul
    //3 - zmiana tresc
    //4 - zmiana alarm

    private Byte state2;
    //1 - brak zmian
    //2 - zmien notatke
    private Integer godzina, minuty;

    private LocalDateTime dzien;

    private boolean[] wybrane;
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

    public Integer getGodzina() {
        return godzina;
    }

    public void setGodzina(Integer godzina) {
        this.godzina = godzina;
    }

    public Integer getMinuty() {
        return minuty;
    }

    public void setMinuty(Integer minuty) {
        this.minuty = minuty;
    }

    public boolean[] getWybrane() {
        return wybrane;
    }

    public void setWybrane(boolean[] wybrane) {
        this.wybrane = wybrane;
    }

    public LocalDateTime getDzien() {
        return dzien;
    }

    public void setDzien(LocalDateTime dzien) {
        this.dzien = dzien;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Byte getState2() {
        return state2;
    }

    public void setState2(Byte state2) {
        this.state2 = state2;
    }
}