package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.Kafelek;
import com.example.notatnik.data.SmallDataHolder;
import com.example.notatnik.providers.KafelekListProvider;
import com.example.notatnik.utils.Dni;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;

import java.util.ArrayList;
import java.util.List;

public class NormalOpcjeSlice extends AbilitySlice {
    ListContainer listContainer;
    KafelekListProvider kafelekListProvider;
    List<Kafelek> kafelekList;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_opcje);
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_opcje_kafelki);
        SmallDataHolder.getInstance().setDzien(Dni.naDate(SmallDataHolder.getInstance().getData().getDzien()));
        inicjalizuj();

    }

    void inicjalizuj(){
        kafelekList = new ArrayList<>();
        Kafelek kafelek = new Kafelek("Title", SmallDataHolder.getInstance().getData().getNazwa(),"com.example.notatnik.ChangeTytul");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Note",SmallDataHolder.getInstance().getNormalNot().getTresc(),"com.example.notatnik.ChangeTresc");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Time",czas(),"com.example.notatnik.ChangeNotyfication");
        kafelekList.add(kafelek);
        kafelekListProvider =  new KafelekListProvider(kafelekList,this);
        listContainer.setItemProvider(kafelekListProvider);
        listContainer.setCentralScrollMode(true);
    }

    String czas(){
        if(!SmallDataHolder.getInstance().getData().getAlarm()) return "Off";
        if(SmallDataHolder.getInstance().getData().getPowtorz()){
            return SmallDataHolder.getInstance().getDzien().getHour() + ":" + SmallDataHolder.getInstance().getDzien().getMinute() +", Repeat";
        }
        return SmallDataHolder.getInstance().getDzien().getHour() + ":" + SmallDataHolder.getInstance().getDzien().getMinute() +", Once";
    }

    @Override
    public void onActive() {
        if(SmallDataHolder.getInstance().getState() == 2){
            kafelekList.get(0).setMniejszy(SmallDataHolder.getInstance().getData().getNazwa());
            kafelekListProvider.notifyDataSetItemChanged(0);
            SmallDataHolder.getInstance().setState((byte) 1);
        }
        else if(SmallDataHolder.getInstance().getState() == 3){
            kafelekList.get(1).setMniejszy(SmallDataHolder.getInstance().getNormalNot().getTresc());
            kafelekListProvider.notifyDataSetItemChanged(1);
            SmallDataHolder.getInstance().setState((byte) 1);
        }
        else if(SmallDataHolder.getInstance().getState() == 4){
            kafelekList.get(2).setMniejszy(czas());
            kafelekListProvider.notifyDataSetItemChanged(2);
            SmallDataHolder.getInstance().setState((byte) 1);
        }
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
    @Override
    protected void onStop() {
        DataHolder.getInstance().removeformObecne(getAbility());
        super.onStop();
    }
}
