package com.example.notatnik.slice;

import com.example.notatnik.*;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.utils.Color;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.event.notification.NotificationHelper;
import ohos.event.notification.NotificationRequest;
import ohos.event.notification.NotificationSlot;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;
import ohos.rpc.RemoteException;

import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    DatabaseHelper helper;
    OrmContext context;
    ListContainer listContainer;
    List<Data> dane;
    Button but;
    Boolean juz;
    AnimationButton animatorProperty, animatorProperty2;
    NotificationSlot notificationSlot;
    NotificationRequest notificationRequest;
    NotificationRequest.NotificationNormalContent notificationNormalContent;
    NotificationRequest.NotificationContent notificationContent;
    int notifId;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        juz = true;
        notifId = 1;
        super.setUIContent(ResourceTable.Layout_ability_main);
        but = (Button)findComponentById(ResourceTable.Id_dodaj);
        listContainer = (ListContainer)findComponentById(ResourceTable.Id_tytuły);
        but.setPosition(0,0);
        listContainer.setPosition(0,0);
        animatorProperty = new AnimationButton(1.f,0.f,500,but,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,500,but,false);
        notificationSlot = new NotificationSlot("slot1","notes",NotificationSlot.LEVEL_DEFAULT);
        notificationSlot.setEnableVibration(true);
        notificationSlot.setDescription("Nie mam pojecia");
        notificationRequest = new NotificationRequest();
        notificationRequest.setNotificationId(notifId);
        notificationRequest.setSlotId(notificationSlot.getId());
        try {
            NotificationHelper.addNotificationSlot(notificationSlot);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        PositionLayout positionLayout = (PositionLayout) findComponentById(ResourceTable.Id_mainability);
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
                       System.exit(0);
                    }
                }
                return true;
            }
        });

       inicjalizacja();
       but.setClickedListener(new Component.ClickedListener() {
           @Override
           public void onClick(Component component) {
               Intent intent = new Intent();
               Operation operation = new Intent.OperationBuilder()
                       .withDeviceId("")
                       .withBundleName("com.example.notatnik")
                       .withAbilityName("com.example.notatnik.Add_tytul")
                       .build();
               intent.setOperation(operation);
               intent.setParam("title","");
               intent.setParam("tresc","");
               intent.setParam("Id_dane",-1);
               startAbility(intent);
               terminateAbility();
               /*notificationNormalContent = new NotificationRequest.NotificationNormalContent();
               notificationNormalContent.setTitle("Notes").setText("Notyfikacja o numerze id " + notifId);
               notificationContent = new NotificationRequest.NotificationContent(notificationNormalContent);
               notificationRequest.setContent(notificationContent);
               try {
                   NotificationHelper.publishNotification(notificationRequest);
               } catch (RemoteException e) {
                   e.printStackTrace();
               }
               notifId++;
               notificationRequest.setNotificationId(notifId);*/
           }
       });
    }
    private void inicjalizacja(){
        dane = read();
        NazwaListProvider nazwaListProvider = new NazwaListProvider(dane,this);
        listContainer.setItemProvider(nazwaListProvider);
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
                if(il ==0 || il ==-1 ){
                    if(!juz) animatorProperty2.start();
                    juz = true;
                }
                else if(juz){
                    juz = false;
                    animatorProperty.start();
                }

                if(il != -1){
                    if(il>0){
                        Component cpt1 = listContainer.getComponentAt(il-1);
                        Text text1 = (Text) cpt1.findComponentById(ResourceTable.Id_nazwa);
                        text1.setTextColor(new Color(Color.getIntColor("#FF9F9F9F")));
                    }

                    Component cpt = listContainer.getComponentAt(il);
                    Text text = (Text) cpt.findComponentById(ResourceTable.Id_nazwa);
                    text.setTextColor(new Color(Color.getIntColor("#FFFFFFFF")));

                    if(il+1<dane.size()){
                        Component cpt2 = listContainer.getComponentAt(il+1);
                        Text text2 = (Text) cpt2.findComponentById(ResourceTable.Id_nazwa);
                        text2.setTextColor(new Color(Color.getIntColor("#FF9F9F9F")));
                    }
                }
            }
        });

        listContainer.setItemLongClickedListener(new ListContainer.ItemLongClickedListener() {
            @Override
            public boolean onItemLongClicked(ListContainer listContainer, Component component, int i, long l) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.Delete")
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
                terminateAbility();
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

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
