package com.example.notatnik.slice;

import com.example.notatnik.data.Dane;
import com.example.notatnik.data.Data;
import com.example.notatnik.providers.DeleteListProvider;
import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.utils.Color;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;

import java.util.ArrayList;
import java.util.List;

public class DeleteSlice extends AbilitySlice {
    DatabaseHelper helper;
    OrmContext context;
    ListContainer listContainer;
    List<Data> dane;
    Image image;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_delete);
        image =(Image)findComponentById(ResourceTable.Id_usun);
        listContainer = (ListContainer)findComponentById(ResourceTable.Id_deletelista);
        image.setPosition(0,0);
        listContainer.setPosition(0,0);
        DataHolder.getInstance().setUsuwane(new ArrayList<>());

        PositionLayout positionLayout = (PositionLayout) findComponentById(ResourceTable.Id_delete);
        positionLayout.setTouchEventListener(new Component.TouchEventListener() {
            float startPositionX = 0;
            float endPositionX = 0;
            float startPositionY = 0;
            float endPositionY = 0;
            final float horizontalSwipeThreshold = 100;
            final float verticalSwipeThreshold = 50;
            @Override
            public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
                if(touchEvent.getAction() == TouchEvent.PRIMARY_POINT_DOWN){
                    MmiPoint downPoint = touchEvent.getPointerScreenPosition(0);
                    startPositionX = downPoint.getX();
                    startPositionY = downPoint.getY();
                }
                if(touchEvent.getAction() == TouchEvent.PRIMARY_POINT_UP){
                    MmiPoint movePoint = touchEvent.getPointerScreenPosition(0);
                    endPositionX = movePoint.getX();
                    endPositionY = movePoint.getY();
                    if((Math.abs(endPositionY - startPositionY) < verticalSwipeThreshold) &&
                            (endPositionX - startPositionX > horizontalSwipeThreshold)){
                        Intent intent = new Intent();
                        Operation operation = new Intent.OperationBuilder()
                                .withDeviceId("")
                                .withBundleName("com.example.notatnik")
                                .withAbilityName("com.example.notatnik.MainAbility")
                                .build();
                        intent.setOperation(operation);
                        startAbility(intent);
                        terminateAbility();
                    }
                }
                return true;
            }
        });

        inicjalizacja();
        image.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                delete();
            }
        });
    }
    private void inicjalizacja(){
        dane = read();
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
                if(il == 0 || il == -1){
                    image.setVisibility(Component.VISIBLE);

                }
                else{
                    image.setVisibility(Component.HIDE);

                }
                /*if(il != -1){
                    if(il>0){
                        Component cpt1 = listContainer.getComponentAt(il-1);
                        Text text1 = (Text) cpt1.findComponentById(ResourceTable.Id_nazwa_usun);
                        text1.setTextColor(new Color(Color.getIntColor("#FF9F9F9F")));
                    }

                    Component cpt = listContainer.getComponentAt(il);
                    Text text = (Text) cpt.findComponentById(ResourceTable.Id_nazwa_usun);
                    text.setTextColor(new Color(Color.getIntColor("#FFFFFFFF")));

                    if(il+1<dane.size()){
                        Component cpt2 = listContainer.getComponentAt(il+1);
                        Text text2 = (Text) cpt2.findComponentById(ResourceTable.Id_nazwa_usun);
                        text2.setTextColor(new Color(Color.getIntColor("#FF9F9F9F")));
                    }
                }*/

            }
        });
        listContainer.setItemLongClickedListener(new ListContainer.ItemLongClickedListener() {
            @Override
            public boolean onItemLongClicked(ListContainer listContainer, Component component, int i, long l) {
                return  true;
            }
        });
    }
    private List<Data> read(){
        helper = new DatabaseHelper(this);
        context = helper.getOrmContext("data","Data.db", Dane.class);
        OrmPredicates ormPredicates = context.where(Data.class);
        return context.query(ormPredicates);
    }
    void  delete(){
        /*helper = new DatabaseHelper(this);
        context = helper.getOrmContext("data","Data.db",Dane.class);
        for (int i =0; i <a.size();i++) {
            if(a.get(i)) {
                context.delete(context.where(Data.class).equalTo("DataId", dane.get(i).getDataId()));
            }
        }
        context.flush();*/
        helper = new DatabaseHelper(this);
        context = helper.getOrmContext("data","Data.db",Dane.class);
        DataHolder.getInstance().setState((byte) 3);
        List<Data> usuwane = DataHolder.getInstance().getUsuwane();
        for(Data usuwany : usuwane){
            DataHolder.getInstance().removeDane(usuwany);
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
}
