package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.Data;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.providers.NazwaListProvider;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmPredicates;
import ohos.event.notification.NotificationRequest;
import ohos.event.notification.NotificationSlot;
import ohos.miscservices.timeutility.Time;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    DatabaseHelper helper;
    OrmContext context;
    ListContainer listContainer;
    List<Data> dane;
    Button but, but2;
    Boolean juz;
    NazwaListProvider nazwaListProvider;
    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4;
    NotificationSlot notificationSlot;
    NotificationRequest notificationRequest;
    NotificationRequest.NotificationNormalContent notificationNormalContent;
    NotificationRequest.NotificationContent notificationContent;
    int notifId;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        juz = true;
        but = (Button)findComponentById(ResourceTable.Id_dodaj);
        but2 = (Button) findComponentById(ResourceTable.Id_main_opcje);
        listContainer = (ListContainer)findComponentById(ResourceTable.Id_tytuły);
        but.setPosition(120,40);
        but2.setPosition(246, 40);
        listContainer.setPosition(0,0);
        animatorProperty = new AnimationButton(1.f,0.f,100,but,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);
        if(DataHolder.getInstance().getObecne() != null){
            List<Ability> kopia =  new ArrayList<>();
            kopia.addAll(DataHolder.getInstance().getObecne());
            for(Ability ability : kopia){
                ability.terminateAbility();
            }
        }
        DataHolder.getInstance().setObecne(new ArrayList<>());
        DataHolder.getInstance().addObecne(getAbility());
        inicjalizacja();

       but.setClickedListener(new Component.ClickedListener() {
           @Override
           public void onClick(Component component) {
               Intent intent = new Intent();
               Operation operation = new Intent.OperationBuilder()
                       .withDeviceId("")
                       .withBundleName("com.example.notatnik")
                       .withAbilityName("com.example.notatnik.ChoseType")
                       .build();
               intent.setOperation(operation);
               startAbility(intent);
           }
       });
       but2.setClickedListener(new Component.ClickedListener() {
           @Override
           public void onClick(Component component) {
               long   i = Time.getCurrentTime();
               for(int j = 0; j < 40000; j++){
                   Data data = new Data();
                   data.setNazwa("nie wiem");
                   data.setAlarm(true);
                   data.setDataId(1);
                   for(int h = 0; h < j; h++){
                       data.setDataId(1);
                       data.setNazwa("nie wiem");
                       data.setAlarm(true);
                   }
               }
               long z =  Time.getCurrentTime();
               i = z -i;
               ToastDialog toastDialog = new ToastDialog(getContext());
               toastDialog.setText(String.valueOf(i));
               toastDialog.setDuration(3000);
               toastDialog.setOffset(0, 158);
               toastDialog.setSize(366,100);
               toastDialog.show();
           }
       });
    }
    private void inicjalizacja(){
        dane = read();

        if(!dane.isEmpty()) DataHolder.getInstance().setLastId(dane.get(dane.size()-1).getDataId()+1);
        else DataHolder.getInstance().setLastId(0);

        DataHolder.getInstance().setDane(dane);
        DataHolder.getInstance().setState((byte) 1);

        nazwaListProvider = new NazwaListProvider(DataHolder.getInstance().getDane(),this);
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
               // terminateAbility();
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
        if(DataHolder.getInstance().getState() == 2 ){
            DataHolder.getInstance().setState((byte) 1);
            nazwaListProvider.notifyDataChanged();
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
