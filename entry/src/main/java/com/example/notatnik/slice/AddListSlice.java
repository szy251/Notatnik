package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.*;
import com.example.notatnik.providers.KafelekListProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.util.ArrayList;
import java.util.List;

public class AddListSlice extends AbilitySlice {
    ListContainer listContainer;
    KafelekListProvider kafelekListProvider;
    List<Kafelek> kafelekList;
    Button but1, but2;
    OrmContext ormContext;
    DatabaseHelper helper;
    boolean juz;

    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_normal);
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_normal);
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_lista_dodawnaie);
        but1 = (Button) findComponentById(ResourceTable.Id_accept);
        but2 = (Button) findComponentById(ResourceTable.Id_decline);
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        animatorProperty = new AnimationButton(1.f,0.f,100,but1,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but1,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);
        juz = true;
        inicjalizacja();
        DataHolder.getInstance().setNazwa("");
        DataHolder.getInstance().setListy(new ArrayList<>());
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(DataHolder.getInstance().getNazwa().equals("")){
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Empty title");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366,100);
                    toastDialog.show();
                }
                else if(DataHolder.getInstance().getListy().size() == 0)
                {
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Empty content");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366,100);
                    toastDialog.show();
                }
                else{
                    helper = new DatabaseHelper(getContext());
                    ormContext = helper.getOrmContext("data", "Data.db", Dane.class);
                    Data data = new Data();
                    data.setAlarm(false);
                    data.setNazwa(DataHolder.getInstance().getNazwa());
                    data.setTyp("List");
                    data.setDataId(DataHolder.getInstance().getLastId());

                    ormContext.insert(data);

                    List<ListNot> listNots = DataHolder.getInstance().getListy();
                    for(ListNot listy:listNots){
                        ormContext.insert(listy);
                    }

                    ormContext.flush();

                    DataHolder.getInstance().incLastId();
                    DataHolder.getInstance().setState((byte) 2);
                    DataHolder.getInstance().addDane(data);
                    DataHolder.getInstance().getAbility().terminateAbility();
                    terminateAbility();
                }
            }
        });
        but2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminateAbility();
            }
        });
    }

    private void inicjalizacja(){
        kafelekList = new ArrayList<>();
        Kafelek kafelek = new Kafelek("Title","Your title","com.example.notatnik.AddTytul");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("List","Count: 0","com.example.notatnik.AddListyTresc");
        kafelekList.add(kafelek);
        kafelekListProvider =  new KafelekListProvider(kafelekList,this);
        listContainer.setItemProvider(kafelekListProvider);
        listContainer.setCentralScrollMode(true);
        listContainer.addScrolledListener(new Component.ScrolledListener() {
            @Override
            public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
                int il = listContainer.getCenterFocusablePosition();
                if(il <= 0 ){
                    if(!juz) {
                        animatorProperty2.start();
                        animatorProperty4.start();
                    }
                    juz = true;
                }
                else if(juz){
                    juz = false;
                    animatorProperty.start();
                    animatorProperty3.start();
                }
            }
        });

    }
    @Override
    public void onActive() {

        if(DataHolder.getInstance().getState() == 3){
            kafelekList.get(0).setMniejszy(DataHolder.getInstance().getNazwa());
            kafelekListProvider.notifyDataChanged();
            DataHolder.getInstance().setState((byte) 1);
        }
        else if(DataHolder.getInstance().getState() == 4){
            kafelekList.get(1).setMniejszy("Count: "+ DataHolder.getInstance().getListy().size());
            kafelekListProvider.notifyDataChanged();
            DataHolder.getInstance().setState((byte) 1);
        }
        super.onActive();super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
