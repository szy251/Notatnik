package com.example.notatnik.data;

import com.example.notatnik.animations.AnimationButton;
import ohos.aafwk.ability.Ability;
import ohos.agp.components.ListContainer;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
    private static DataHolder instance;
    private byte state;
    // 1 - brak zmian
    // 2 - zmiany w głównej bazie
    // 3 - zmiany tytuł
    // 4 - dodanie do listy/zmiana notatki
    // 5 - edytowanie w liście

    private Integer lastId;

    private String nazwa, tresc;
    private List<Data> dane, usuwane;

    private List<ListNot> listy,edytowane;

    private Data nowy;
    private Ability ability;

    private ListContainer listContainer;

    private AnimationButton animationButton;

    private ListNot listNot;


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
        edytowane = new ArrayList<>();
        for(ListNot listNot:listy){
            ListNot kopia = new ListNot();
            kopia.setListNotId(listNot.getListNotId());
            kopia.setNazwa(listNot.getNazwa());
            kopia.setDataParentId(listNot.getDataParentId());
            kopia.setZrobione(listNot.getZrobione());
            edytowane.add(kopia);
        }
    }

    public ListContainer getListContainer() {
        return listContainer;
    }

    public void setListContainer(ListContainer listContainer) {
        this.listContainer = listContainer;
    }

    public AnimationButton getAnimationButton() {
        return animationButton;
    }

    public void setAnimationButton(AnimationButton animationButton) {
        this.animationButton = animationButton;
    }

    public ListNot getListNot() {
        return listNot;
    }

    public void setListNot(ListNot listNot) {
        this.listNot = listNot;
    }

    public void addEdytowane(ListNot listNot){
        edytowane.add(listNot);
    }
}