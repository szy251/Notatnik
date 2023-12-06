package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.*;
import com.example.notatnik.providers.KafelekListProvider;
import com.example.notatnik.utils.DataPomocnik;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddNormalSlice extends AbilitySlice {
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
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_lista_dodawnaie);
        but1 = (Button) findComponentById(ResourceTable.Id_accept);
        but2 = (Button) findComponentById(ResourceTable.Id_decline);
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        animatorProperty = new AnimationButton(1.f,0.f,100,but1,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but1,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);
        juz = true;
        inicjalizacja();
        DataHolder.getInstance().setNazwa("");
        DataHolder.getInstance().setTresc("");
        DataHolder.getInstance().addObecne(getAbility());
        DataHolder.getInstance().setWybrane(new boolean[7]);
        DataHolder.getInstance().setGodzina(LocalDateTime.now().getHour());
        DataHolder.getInstance().setMinuty(LocalDateTime.now().getMinute());
        DataHolder.getInstance().setAlarm(false);
        DataHolder.getInstance().setPowtorzenia(false);
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(DataHolder.getInstance().getNazwa().length() > 0  && DataHolder.getInstance().getTresc().length() > 0) {
                    helper = new DatabaseHelper(getContext());
                    ormContext = helper.getOrmContext("data", "Notes.db", Dane.class);

                    Data data = DataPomocnik.stworz("Norm");


                    NormalNot normalNot = new NormalNot();
                    normalNot.setDataParentId(DataHolder.getInstance().getLastId());
                    normalNot.setTresc(DataHolder.getInstance().getTresc());

                    ormContext.insert(data);
                    ormContext.insert(normalNot);
                    ormContext.flush();
                    DataHolder.getInstance().setState((byte) 2);

                    DataHolder.getInstance().incLastId();
                    int index = DataPomocnik.get_index(DataHolder.getInstance().getOpcjeData().getSortowTyp(),DataHolder.getInstance().getDane(), data);
                    if(index <0){
                        index = -index -1;
                    }
                    DataHolder.getInstance().addDane(index,data);
                    DataHolder.getInstance().getAbility().terminateAbility();
                    terminateAbility();
                }
                else if(DataHolder.getInstance().getNazwa().equals("")){
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("  Empty title");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366,100);
                    toastDialog.show();
                }
                else{
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("  Empty note");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(376,100);
                    toastDialog.show();
                }
            }
        });
        but2.setClickedListener(listener-> terminateAbility());
    }


    private void inicjalizacja(){
        kafelekList = new ArrayList<>();
        Kafelek kafelek = new Kafelek("Title","Your title","com.example.notatnik.AddTytul");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Note","Your note","com.example.notatnik.AddTresc");
        kafelekList.add(kafelek);
        kafelek = new Kafelek("Time","Off","com.example.notatnik.AddNotyfication");
        kafelekList.add(kafelek);
        kafelekListProvider =  new KafelekListProvider(kafelekList,this);
        listContainer.setItemProvider(kafelekListProvider);
        listContainer.setCentralScrollMode(true);
        listContainer.setFocusable(Component.FOCUS_ADAPTABLE);
        listContainer.requestFocus();
        DataHolder.getInstance().setListContainer(listContainer);
        listContainer.setLongClickable(false);
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
        if(DataHolder.getInstance().getState() == 3
        ){
            kafelekList.get(0).setMniejszy(DataHolder.getInstance().getNazwa());
            kafelekListProvider.notifyDataSetItemChanged(0);
            DataHolder.getInstance().setState((byte) 1);
        }
        else if(DataHolder.getInstance().getState() == 4){
            kafelekList.get(1).setMniejszy(DataHolder.getInstance().getTresc());
            kafelekListProvider.notifyDataSetItemChanged(1);
            DataHolder.getInstance().setState((byte) 1);
        }
        else if(DataHolder.getInstance().getState() == 6){
            if(!DataHolder.getInstance().isAlarm()) kafelekList.get(2).setMniejszy("Off");
            else if(DataHolder.getInstance().isPowtorzenia()) kafelekList.get(2).setMniejszy(String.format("%02d:%02d",DataHolder.getInstance().getGodzina(),DataHolder.getInstance().getMinuty()) +", Repeat");
            else kafelekList.get(2).setMniejszy(String.format("%02d:%02d",DataHolder.getInstance().getGodzina(),DataHolder.getInstance().getMinuty()) +", One time");
            kafelekListProvider.notifyDataSetItemChanged(2);
            DataHolder.getInstance().setState((byte) 1);
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
