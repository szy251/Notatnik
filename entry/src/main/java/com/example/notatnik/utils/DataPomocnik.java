package com.example.notatnik.utils;


import com.example.notatnik.data.Dane;
import com.example.notatnik.data.Data;
import com.example.notatnik.data.DataHolder;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.event.notification.ReminderHelper;
import ohos.rpc.RemoteException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.binarySearch;

public class DataPomocnik {
    public static Data stworz(String typ){
        Data data = new Data();
        data.setAlarm(DataHolder.getInstance().isAlarm());
        data.setNazwa(DataHolder.getInstance().getNazwa());
        data.setTyp(typ);
        data.setDataId(DataHolder.getInstance().getLastId());
        Dni.ustawDni(data,DataHolder.getInstance().getWybrane());
        data.setPowtorz(DataHolder.getInstance().isPowtorzenia());
        data.setDzien(Dni.naString(Dni.jutoLubDzis(DataHolder.getInstance().getGodzina(), DataHolder.getInstance().getMinuty())));

        if(DataHolder.getInstance().isAlarm())
            data.setAlarmId(Notyfikacje.publikuj(DataHolder.getInstance().getNazwa(),
                    DataHolder.getInstance().getLastId(),
                    DataHolder.getInstance().getGodzina(),
                    DataHolder.getInstance().getMinuty(),
                    Dni.naInty(DataHolder.getInstance().getWybrane())));
        return data;
    }

    public static void aktualizujData(Data data, Context context){
        DatabaseHelper helper = new DatabaseHelper(context);
        OrmContext ormContext = helper.getOrmContext("data", "Data.db", Dane.class);
        ormContext.update(data);
        ormContext.flush();
    }
    public static boolean sprawdzAlarm(Data data, Context context){
        if(data.getAlarm() && ! data.getPowtorz()){
            LocalDateTime to = Dni.naDate(data.getDzien());
            if(to.isBefore(LocalDateTime.now())){
                try {
                    ReminderHelper.cancelReminder(data.getAlarmId());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                data.setAlarm(false);
                DatabaseHelper helper = new DatabaseHelper(context);
                OrmContext ormContext = helper.getOrmContext("data","Data.db", Dane.class);
                ormContext.update(data);
                ormContext.flush();
                return true;
            }
        }
        return false;
    }

    public static class DataIdComparator implements Comparator<Data> {
        @Override
        public int compare(Data d1, Data d2) {
            return Integer.compare(d1.getDataId(), d2.getDataId());
        }
    }
    public static class NazwaComparator implements Comparator<Data> {
        @Override
        public int compare(Data d1, Data d2) {
            return d1.getNazwa().compareToIgnoreCase(d2.getNazwa());
        }
    }
    public static class TypComparator implements Comparator<Data> {
        @Override
        public int compare(Data d1, Data d2) {
            return d1.getTyp().compareToIgnoreCase(d2.getTyp());
        }
    }

    public static void sort(int i, List<Data> dane){
        switch (i){
            case 1:
                DataIdComparator comparator = new DataIdComparator();
                dane.sort(comparator);
                break;
            case 2:
                DataIdComparator comparator1 = new DataIdComparator();
                dane.sort(comparator1.reversed());
                break;
            case 3:
                NazwaComparator comparator2 = new NazwaComparator();
                dane.sort(comparator2);
                break;
            case 4:
                NazwaComparator comparator3 = new NazwaComparator();
                dane.sort(comparator3.reversed());
                break;
            case 5:
                TypComparator comparator4 = new TypComparator();
                dane.sort(comparator4);
                break;
            case 6:
                TypComparator comparator5 = new TypComparator();
                dane.sort(comparator5.reversed());
                break;
        }
    }
    public static int get_index(int i, List<Data> dane,Data szukan){
        switch (i){
            case 1:
                DataIdComparator comparator = new DataIdComparator();
                return binarySearch(dane,szukan,comparator);
            case 2:
                DataIdComparator comparator1 = new DataIdComparator();
                return binarySearch(dane,szukan,comparator1.reversed());
            case 3:
                NazwaComparator comparator2 = new NazwaComparator();
                return binarySearch(dane,szukan,comparator2);
            case 4:
                NazwaComparator comparator3 = new NazwaComparator();
                return binarySearch(dane,szukan,comparator3.reversed());
            case 5:
                TypComparator comparator4 = new TypComparator();
                return binarySearch(dane,szukan,comparator4);
            case 6:
                TypComparator comparator5 = new TypComparator();
                return binarySearch(dane,szukan,comparator5.reversed());
        }
        return 0;
    }
}