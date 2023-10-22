package com.example.notatnik.data;

import ohos.aafwk.ability.Ability;

import java.util.List;
import java.util.stream.Collectors;

public class DataHolder {
    private static DataHolder instance;
    private byte state;

    private Integer lastId;

    private String nazwa;

    private String tresc;
    private List<Data> dane, usuwane;

    private List<ListNot> listy,edytowane;

    private Data nowy;
    private Ability ability;


    private DataHolder() {
        // Prywatny konstruktor Singletona
    }

    public static DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Data getNowy() {
        return nowy;
    }

    public void setNowy(Data nowy) {
        this.nowy = nowy;
    }
    public  List<Data> getDane(){return dane;}

    public void setDane(List<Data> dane) {this.dane = dane;}

    public void addDane(Data dane) {this.dane.add(dane);}

    public void removeDane(Data dane){this.dane.remove(dane);}

    public void setListy(List<ListNot> listy){this.listy = listy;}

    public List<ListNot> getListy(){return listy;}

    public List<Data> getUsuwane(){return usuwane;}

    public void setUsuwane(List<Data> usuwane) {
        this.usuwane = usuwane;
    }

    public void addUsuwane(Data elem){
        this.usuwane.add(elem);
    }

    public void removeUsuwane(Data elem){
        this.usuwane.remove(elem);
    }
    public void setLastId(Integer lastId) {this.lastId = lastId;}
    public Integer getLastId() { return lastId;}

    public void incLastId(){ lastId++;}
    public void setAbility(Ability ability){this.ability = ability;}
    public Ability getAbility(){return ability;}

    public List<ListNot> getEdytowane() {return edytowane;}

    public void setEdytowane(List<ListNot> edytowane) {this.edytowane = edytowane;}

    public void kopiaListy(){
        edytowane = listy.stream().map(ListNot::new).collect(Collectors.toList());
    }
}