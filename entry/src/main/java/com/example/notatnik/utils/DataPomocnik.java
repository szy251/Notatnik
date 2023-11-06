package com.example.notatnik.utils;


import com.example.notatnik.data.Dane;
import com.example.notatnik.data.Data;
import com.example.notatnik.data.DataHolder;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.time.LocalDateTime;

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
    public static void sprawdzAlarm(Data data, Context context){
        if(data.getAlarm() && ! data.getPowtorz()){
            LocalDateTime to = Dni.naDate(data.getDzien());
            if(to.isBefore(LocalDateTime.now())){
                data.setAlarm(false);
                DatabaseHelper helper = new DatabaseHelper(context);
                OrmContext ormContext = helper.getOrmContext("data","Data.db", Dane.class);
                ormContext.update(data);
                ormContext.flush();
            }
        }
    }
}