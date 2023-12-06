package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.*;
import com.example.notatnik.providers.DeleteListProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
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
    ListContainer listContainer;
    List<Data> dane;
    Image image;
    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4;
    Button but;
    Boolean juz;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_delete);
        image =(Image)findComponentById(ResourceTable.Id_usun_accept);
        but = (Button) findComponentById(ResourceTable.Id_usun_decline);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        image.setBackground(shapeElement);
        but.setBackground(shapeElement);
        listContainer = (ListContainer)findComponentById(ResourceTable.Id_deletelista);
        image.setPosition(120,40);
        but.setPosition(246, 40);
        listContainer.setPosition(0,0);
        animatorProperty = new AnimationButton(1.f,0.f,100,image,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,image,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but,false);
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
        but.setClickedListener(listener-> terminateAbility());

    }
    private void inicjalizacja(){
        dane = DataHolder.getInstance().getDane();
        DeleteListProvider deleteListProvider = new DeleteListProvider(dane,this);
        listContainer.setItemProvider(deleteListProvider);
        listContainer.enableScrollBar(Component.AXIS_Y,true);
        listContainer.setScrollbarBackgroundColor(Color.GRAY);
        listContainer.setScrollbarColor(Color.WHITE);
        listContainer.setFocusable(Component.FOCUS_ADAPTABLE);
        listContainer.requestFocus();
        listContainer.setMode(Component.OVAL_MODE);
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
        listContainer.setLongClickable(false);
    }
    void  delete(){
        helper = new DatabaseHelper(this);
        context = helper.getOrmContext("data","Notes.db",Dane.class);
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
