package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.Kafelek;
import com.example.notatnik.providers.KafelekListProvider;
import com.example.notatnik.utils.Kolor;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;

import java.util.ArrayList;
import java.util.List;

public class GlobalOpcjeSlice extends AbilitySlice {
    ListContainer listContainer;
    KafelekListProvider kafelekListProvider;
    List<Kafelek> kafelekList;
    boolean juz;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_opcje);
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_opcje_kafelki);
        DataHolder.getInstance().setStraznik(true);
        DataHolder.getInstance().addObecne(getAbility());
        juz = true;
        inicjalizacja();
    }
    private void inicjalizacja(){
        kafelekList = new ArrayList<>();
        Kafelek kafelek = new Kafelek("Text size",wielkosc(),"com.example.notatnik.OpcjeTextSize");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Normal background", Kolor.nazwa(DataHolder.getInstance().getOpcjeData().getNormalTytId()),"com.example.notatnik.OpcjeTlo");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("List background",Kolor.nazwa(DataHolder.getInstance().getOpcjeData().getListTytId()),"com.example.notatnik.OpcjeTlo");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Button color",Kolor.nazwa(DataHolder.getInstance().getOpcjeData().getPrzycTloId()),"com.example.notatnik.OpcjePrzyciski");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Checked color",Kolor.nazwa(DataHolder.getInstance().getOpcjeData().getCheckedListId()),"com.example.notatnik.OpcjeCheckedList");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Sort",sortowanie(),"com.example.notatnik.OpcjeSorting");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Notification sound",dzwiek(),"com.example.notatnik.OpcjeSound");
        kafelekList.add(kafelek);
        kafelekListProvider =  new KafelekListProvider(kafelekList,this);
        listContainer.setItemProvider(kafelekListProvider);
        listContainer.setCentralScrollMode(true);
        listContainer.setFocusable(Component.FOCUS_ADAPTABLE);
        listContainer.requestFocus();
        listContainer.setLongClickable(false);
        DataHolder.getInstance().setKafelekId(-1);
        listContainer.addScrolledListener(new Component.ScrolledListener() {
            @Override
            public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
                int ja = listContainer.getCenterFocusablePosition();
                if(DataHolder.getInstance().getKafelekId() != ja  && ja >= 0) {
                    DataHolder.getInstance().setKafelekId(ja);

                    if(ja > 0) {
                        Text text = (Text) listContainer.getComponentAt(ja-1).findComponentById(ResourceTable.Id_wieksze);
                        text.stopAutoScrolling();
                    }
                    Text text1 = (Text) listContainer.getComponentAt(ja).findComponentById(ResourceTable.Id_wieksze);
                    text1.startAutoScrolling();
                    if(ja<6){
                        Text text2 = (Text) listContainer.getComponentAt(ja+1).findComponentById(ResourceTable.Id_wieksze);
                        text2.stopAutoScrolling();
                    }


                }
            }
        });

    }
    String wielkosc(){
        if(DataHolder.getInstance().getOpcjeData().getTextSize() == 0.8f) return "Small";
        if(DataHolder.getInstance().getOpcjeData().getTextSize() == 1.f) return  "Medium";
        return "Large";
    }
    String sortowanie(){
        switch (DataHolder.getInstance().getOpcjeData().getSortowTyp()){
            case 1:
                return "Default";
            case 2:
                return "Default reverse";
            case 3:
                return "Title";
            case 4:
                return "Title reverse";
            case 5:
                return "Type";
            case 6:
                return "Type reverse";
        }
        return "";
    }
    String dzwiek(){
        if(DataHolder.getInstance().getOpcjeData().getSlot().equals("slot1")) return "Off";
        return "On";
    }
    @Override
    public void onActive() {
        if(DataHolder.getInstance().getKafelekId() == 0){
            kafelekList.get(0).setMniejszy(wielkosc());
            kafelekListProvider.notifyDataSetItemChanged(0);
        }
        if(DataHolder.getInstance().getKafelekId() == 1){
            kafelekList.get(1).setMniejszy(Kolor.nazwa(DataHolder.getInstance().getOpcjeData().getNormalTytId()));
            kafelekListProvider.notifyDataSetItemChanged(1);
        }
        if(DataHolder.getInstance().getKafelekId() == 2){
            kafelekList.get(2).setMniejszy(Kolor.nazwa(DataHolder.getInstance().getOpcjeData().getListTytId()));
            kafelekListProvider.notifyDataSetItemChanged(2);
        }
        if(DataHolder.getInstance().getKafelekId() == 3){
            kafelekList.get(3).setMniejszy(Kolor.nazwa(DataHolder.getInstance().getOpcjeData().getPrzycTloId()));
            kafelekListProvider.notifyDataSetItemChanged(3);
        }
        if(DataHolder.getInstance().getKafelekId() == 4){
            kafelekList.get(4).setMniejszy(Kolor.nazwa(DataHolder.getInstance().getOpcjeData().getCheckedListId()));
            kafelekListProvider.notifyDataSetItemChanged(4);
        }
        if(DataHolder.getInstance().getKafelekId() == 5){
            kafelekList.get(5).setMniejszy(sortowanie());
            kafelekListProvider.notifyDataSetItemChanged(5);
        }
        if(DataHolder.getInstance().getKafelekId() == 6){
            kafelekList.get(6).setMniejszy(dzwiek());
            kafelekListProvider.notifyDataSetItemChanged(6);
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
