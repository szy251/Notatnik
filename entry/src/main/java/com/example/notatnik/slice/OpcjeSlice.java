package com.example.notatnik.slice;

import com.example.notatnik.data.Dane;
import com.example.notatnik.data.Data;
import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;

public class OpcjeSlice extends AbilitySlice {
    OrmContext context;
    DatabaseHelper helper;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_opcje);
        String tresc = intent.getSerializableParam("tresc");
        String tytul = intent.getSerializableParam("title");
        Integer id = intent.getIntParam("Id_dane",-1);

        Button button = (Button)findComponentById(ResourceTable.Id_to_edit);
        Button delete = (Button)findComponentById(ResourceTable.Id_delete);
        DirectionalLayout directionalLayout = (DirectionalLayout)findComponentById(ResourceTable.Id_opcje);
        directionalLayout.setTouchEventListener(new Component.TouchEventListener() {
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
                                .withAbilityName("com.example.notatnik.Tresc")
                                .build();
                        intent.setOperation(operation);
                        intent.setParam("title",tytul);
                        intent.setParam("tresc",tresc);
                        intent.setParam("Id_dane",id);
                        startAbility(intent);
                        terminateAbility();
                    }
                }
                return true;
            }
        });

        delete.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                helper = new DatabaseHelper(OpcjeSlice.this);
                context = helper.getOrmContext("data","Data.db",Dane.class);
                context.delete(context.where(Data.class).equalTo("DataId",id));
                context.flush();
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
        });
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.Add_tytul")
                        .build();
                intent.setOperation(operation);
                intent.setParam("title",tytul);
                intent.setParam("tresc",tresc);
                intent.setParam("Id_dane",id);
                startAbility(intent);
                terminateAbility();
            }
        });
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
