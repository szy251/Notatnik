package com.example.notatnik.data;

import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.slice.ChangeListyTrescSlice;
import ohos.agp.components.ListContainer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SmallDataHolder{
    private static SmallDataHolder instance;
    private Data data;
    private NormalNot normalNot;

    private List<ListNot> listNots, kopie, usuniete, edytowane, nowe;
    private Byte state;
    //1 - brak zmian
    //2 - zmiana tytul
    //3 - zmiana tresc
    //4 - zmiana alarm

    private Byte state2;
    //1 - brak zmian
    //2 - zmien notatke
    private Integer godzina, minuty, pozycja;

    private LocalDateTime dzien;

    private boolean[] wybrane;

    private ListContainer listContainer;
    private AnimationButton animationButton;
    private ListNot listNot;
    private ChangeListyTrescSlice changeListyTrescSlice;
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

    public List<ListNot> getKopie() {
        return kopie;
    }

    public void setKopie(List<ListNot> kopie) {
        this.kopie = kopie;
    }

    public List<ListNot> getUsuniete() {
        return usuniete;
    }

    public void setUsuniete(List<ListNot> usuniete) {
        this.usuniete = usuniete;
    }

    public List<ListNot> getEdytowane() {
        return edytowane;
    }

    public void setEdytowane(List<ListNot> edytowane) {
        this.edytowane = edytowane;
    }

    public void kopiaListy(){
        kopie = new ArrayList<>();
        for(ListNot listNot:listNots){
            ListNot kopia = new ListNot();
            kopia.setListNotId(listNot.getListNotId());
            kopia.setNazwa(listNot.getNazwa());
            kopia.setDataParentId(listNot.getDataParentId());
            kopia.setZrobione(listNot.getZrobione());
            kopie.add(kopia);
        }
    }
    public void addKopie(ListNot list){
        kopie.add(list);
    }
    public void addUsuniete(ListNot list){
        usuniete.add(list);
    }
    public void addEdytowane(ListNot list){
        edytowane.add(list);
    }
    public void addNowe(ListNot list){
        nowe.add(list);
    }
    public void removeEdytowane(ListNot list){
        edytowane.remove(list);
    }
    public void removeKopie(ListNot list){kopie.remove(list);}
    public void removeNowe(ListNot list){
        nowe.remove(list);
    }
    public void removeUsuniete(ListNot list){
        usuniete.remove(list);
    }

    public void setNowe(List<ListNot> nowe) {
        this.nowe = nowe;
    }
    public List<ListNot> getNowe(){
        return nowe;
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

    public Integer getPozycja() {
        return pozycja;
    }

    public void setPozycja(Integer pozycja) {
        this.pozycja = pozycja;
    }

    public ChangeListyTrescSlice getChangeListyTrescSlice() {
        return changeListyTrescSlice;
    }

    public void setChangeListyTrescSlice(ChangeListyTrescSlice changeListyTrescSlice) {
        this.changeListyTrescSlice = changeListyTrescSlice;
    }
}