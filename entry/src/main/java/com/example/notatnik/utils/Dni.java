package com.example.notatnik.utils;

import com.example.notatnik.data.Data;
import com.example.notatnik.data.SmallDataHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Dni{
    static String pattern = "yyyy-MM-dd HH:mm:ss";
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    public static String naString(LocalDateTime dateTime){
        return dateTime.format(dateTimeFormatter);
    }
    public static LocalDateTime naDate(String dni){
        return LocalDateTime.parse(dni,dateTimeFormatter);
    }

    public static LocalDateTime jutoLubDzis(int godzina, int minuty){
        LocalDateTime sprawdz = LocalDateTime.now().withSecond(0).withMinute(minuty).withHour(godzina);
        if(!LocalDateTime.now().isBefore(sprawdz)) sprawdz = sprawdz.plusDays(1);
        return sprawdz;
    }
    public static void ustawDni(Data data, boolean [] dni){
        data.setPon(dni[0]);
        data.setWt(dni[1]);
        data.setSr(dni[2]);
        data.setCzw(dni[3]);
        data.setPt(dni[4]);
        data.setSob(dni[5]);
        data.setNd(dni[6]);
    }

    public static boolean[] dostanDni(Data data){
        boolean[] dni = new boolean[7];
        dni[0] = data.getPon();
        dni[1] = data.getWt();
        dni[2] = data.getSr();
        dni[3] = data.getCzw();
        dni[4] = data.getPt();
        dni[5] = data.getSob();
        dni[6] = data.getNd();
        return dni;
    }

    public static int[] naInty(boolean [] dni){
        ArrayList<Integer> poprawne =  new ArrayList<>();
        for(int i = 0; i < 7; i++){
            if(dni[i]){
                poprawne.add(i+1);
            }
        }
        int[] wynik = new int[poprawne.size()];
        for(int i = 0; i <poprawne.size();i++){
            wynik[i] = poprawne.get(i);
        }
        return wynik;
    }

    public static String dniTygodnia(boolean [] dni){
        StringBuilder sb = new StringBuilder();
        boolean wszystkieFalse = true;
        boolean wszystkieTrue = true;

        for (int i = 0; i < dni.length; i++) {
            if (dni[i]) {
                wszystkieFalse = false;
                sb.append(nazwaDnia(i));
                sb.append(", ");
            } else {
                wszystkieTrue = false;
            }
        }

        if (wszystkieFalse) {
            return "One time";
        } else if (wszystkieTrue) {
            return "All days";
        } else {
            // Usuń ostatni przecinek i spację
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 2);
            }
            return sb.toString();
        }
    }

    static String nazwaDnia(int i){
        switch (i){
            case 0:
                return "Mon";
            case 1:
                return "Tue";
            case 2:
                return "Wed";
            case 3:
                return "Thu";
            case 4:
                return "Fri";
            case 5:
                return "Sat";
            case 6:
                return "Sun";
            default:
                return "";
        }
    }
    public static String czas(){
        if(!SmallDataHolder.getInstance().getData().getAlarm()) return "Off";
        if(SmallDataHolder.getInstance().getData().getPowtorz()){
            return String.format("%02d:%02d",SmallDataHolder.getInstance().getDzien().getHour(), SmallDataHolder.getInstance().getDzien().getMinute()) +", Repeat";
        }
        return String.format("%02d:%02d",SmallDataHolder.getInstance().getDzien().getHour(), SmallDataHolder.getInstance().getDzien().getMinute()) +", One time";
    }
}