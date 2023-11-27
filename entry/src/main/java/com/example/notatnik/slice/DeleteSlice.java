package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.*;
import com.example.notatnik.providers.DeleteListProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.ListContainer;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.event.notification.ReminderHelper;
import ohos.rpc.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class DeleteSlice extends AbilitySlice {
    DatabaseHelper helper;
    OrmContext context;
    ListContainer listContainer2;
    List<Data> dane;
    Image image;
    Boolean juz;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_delete);
        image =(Image)findComponentById(ResourceTable.Id_usun);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        image.setBackground(shapeElement);
        listContainer2 = (ListContainer)findComponentById(ResourceTable.Id_deletelista);
        image.setPosition(0,0);
        listContainer2.setPosition(0,0);
        DataHolder.getInstance().setUsuwane(new ArrayList<>());
        juz = true;
        DataHolder.getInstance().addObecne(getAbility());
        inicjalizacja();
        image.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                delete();
            }
        });

    }
    private void inicjalizacja(){
        dane = DataHolder.getInstance().getDane();
        DeleteListProvider deleteListProvider = new DeleteListProvider(dane,this);
        listContainer2.setItemProvider(deleteListProvider);
        listContainer2.enableScrollBar(Component.AXIS_Y,true);
        listContainer2.setScrollbarBackgroundColor(Color.GRAY);
        listContainer2.setScrollbarColor(Color.WHITE);
        listContainer2.setFocusable(Component.FOCUS_ADAPTABLE);
        listContainer2.requestFocus();
        listContainer2.setMode(Component.OVAL_MODE);
        listContainer2.setCentralScrollMode(true);
        listContainer2.addScrolledListener(new Component.ScrolledListener() {
            @Override
            public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
                int il = listContainer2.getCenterFocusablePosition();
                if(il <= 0){
                    if (!juz) image.setVisibility(Component.VISIBLE);
                    juz = true;
                }
                else if(juz){
                    juz = false;
                    image.setVisibility(Component.HIDE);

                }
            }
        });
        listContainer2.setItemLongClickedListener(new ListContainer.ItemLongClickedListener() {
            @Override
            public boolean onItemLongClicked(ListContainer listContainer, Component component, int i, long l) {
                return  true;
            }
        });
    }
    void  delete(){
        helper = new DatabaseHelper(this);
        context = helper.getOrmContext("data","Data.db",Dane.class);
        DataHolder.getInstance().setState((byte) 2);
        List<Data> usuwane = DataHolder.getInstance().getUsuwane();
        for(Data usuwany : usuwane){
            if(usuwany.getAlarm()) {
                try {
                    ReminderHelper.cancelReminder(usuwany.getAlarmId());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            DataHolder.getInstance().removeDane(usuwany);
            if(usuwany.getTyp().equals("List"))
            {
                context.delete(context.where(ListNot.class).equalTo("dataParentId",usuwany.getDataId()));
            }
            else{
                context.delete(context.where(NormalNot.class).equalTo("dataParentId",usuwany.getDataId()));
            }
            context.delete(context.where(Data.class).equalTo("DataId", usuwany.getDataId()));
        }
        context.flush();
        DataHolder.getInstance().setUsuwane(null);
        terminateAbility();

    }


    @Override
    public void onActive() {
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
