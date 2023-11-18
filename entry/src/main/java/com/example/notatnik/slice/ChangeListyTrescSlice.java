package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.ListNot;
import com.example.notatnik.data.SmallDataHolder;
import com.example.notatnik.providers.ChangeZadaniaListProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.rdb.ValuesBucket;

import java.util.ArrayList;
import java.util.List;

public class ChangeListyTrescSlice extends AbilitySlice {
    ListContainer listContainer;
    ChangeZadaniaListProvider changeZadaniaListProvider;
    List<ListNot> listNots;
    Button but1, but2, but3;
    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4,animatorProperty5,animatorProperty6;
    boolean juz, juz2, juz3, juz4;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_listy_tresc);
        but1 = (Button) findComponentById(ResourceTable.Id_accept_listy);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_listy);
        but3 = (Button) findComponentById(ResourceTable.Id_wiecej_zadan);
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_lista_dodawnaie);
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        but3.setPosition(183,346);
        animatorProperty = new AnimationButton(1.f,0.f,100,but1,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but1,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);
        animatorProperty5 = new AnimationButton(1.f,0.f,100,but3,true);
        animatorProperty6 =  new AnimationButton(0.f,1.f,100,but3,false);
        SmallDataHolder.getInstance().setEdytowane(new ArrayList<>());
        SmallDataHolder.getInstance().setNowe(new ArrayList<>());
        SmallDataHolder.getInstance().setUsuniete(new ArrayList<>());
        SmallDataHolder.getInstance().kopiaListy();
        listNots = SmallDataHolder.getInstance().getKopie();
        SmallDataHolder.getInstance().setChangeListyTrescSlice(this);
        SmallDataHolder.getInstance().setAnimationButton(animatorProperty6);
        DataHolder.getInstance().addObecne(getAbility());
        if(listNots.size() > 1){
            juz2 = false;
            but3.setVisibility(Component.HIDE);
        }
        else {
            juz2 = true;
        }
        juz3 = true;
        juz4 = true;
        juz = true;
        inicjalizacja();
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (listNots.size() > 0) {
                    DatabaseHelper helper = new DatabaseHelper(getContext());
                    OrmContext ormContext = helper.getOrmContext("data", "Data.db", Dane.class);
                    for (ListNot listNot : SmallDataHolder.getInstance().getEdytowane()) {
                        ValuesBucket valuesBucket = new ValuesBucket();
                        valuesBucket.putString("nazwa", listNot.getNazwa());
                        ormContext.update(ormContext.where(ListNot.class).equalTo("ListNotId", listNot.getListNotId()), valuesBucket);
                    }
                    for (ListNot listNot : SmallDataHolder.getInstance().getNowe()) {
                        ormContext.insert(listNot);
                    }
                    for (ListNot listNot : SmallDataHolder.getInstance().getUsuniete()) {
                        ormContext.delete(ormContext.where(ListNot.class).equalTo("ListNotId", listNot.getListNotId()));
                    }
                    ormContext.flush();
                    SmallDataHolder.getInstance().setListNots(listNots);
                    SmallDataHolder.getInstance().setState((byte) 3);
                    SmallDataHolder.getInstance().setState2((byte) 2);
                    terminateAbility();
                }
                else{
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Empty content");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366,100);
                    toastDialog.show();
                }
            }
        });
        but2.setClickedListener(listener->terminateAbility());
        but3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                ListNot listNot = new ListNot();
                listNot.setDataParentId(-1);
                listNot.setZrobione(false);
                SmallDataHolder.getInstance().setListNot(listNot);
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder().
                        withDeviceId("").
                        withBundleName("com.example.notatnik").
                        withAbilityName("com.example.notatnik.ChangeZadanie").
                        build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });
    }
    private void inicjalizacja(){
        changeZadaniaListProvider =  new ChangeZadaniaListProvider(listNots,this);
        listContainer.setItemProvider(changeZadaniaListProvider);
        listContainer.setCentralScrollMode(true);
        SmallDataHolder.getInstance().setListContainer(listContainer);
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
                if (juz3) {
                    if (il <= 0) {
                        if (!juz) {
                            animatorProperty2.start();
                            animatorProperty4.start();
                        }
                        juz = true;
                    } else if (juz) {
                        juz = false;
                        animatorProperty.start();
                        animatorProperty3.start();
                    }
                }

                if (juz4) {
                    if (il == listNots.size() - 1 || il == -1) {
                        if (!juz2)
                            animatorProperty6.start();
                        juz2 = true;
                    } else if (juz2) {
                        juz2 = false;
                        animatorProperty5.start();
                    }
                }
            }
        });


    }

    public void prawda(){
        juz3 = true;
    }
    public void falsz(){
        juz3 = false;
    }

    public void falsz2(){juz4 = false;}
    public void prawda2(){juz4 = true;}
    public void prawda3(){juz2 = true;}
    public boolean isJuz2(){return juz2;}
    @Override
    public void onActive() {

        if(SmallDataHolder.getInstance().getState() == 4){
            TaskDispatcher taskDispatcher = getGlobalTaskDispatcher(TaskPriority.DEFAULT);
            SmallDataHolder.getInstance().setState((byte) 1);
            changeZadaniaListProvider.notifyDataSetItemInserted(listContainer.getChildCount());
            listContainer.scrollTo(listContainer.getChildCount()-1);
            juz2 = true;
            taskDispatcher.delayDispatch(new Runnable() {
                @Override
                public void run() {
                    juz4 = true;
                }
            },1000);
        }
        else if(SmallDataHolder.getInstance().getState() == 5){
            SmallDataHolder.getInstance().setState((byte) 1);
            changeZadaniaListProvider.notifyDataSetItemChanged(SmallDataHolder.getInstance().getPozycja());
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
