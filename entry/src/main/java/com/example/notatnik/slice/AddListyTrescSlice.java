package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.ListNot;
import com.example.notatnik.providers.ZadaniaListProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.List;

public class AddListyTrescSlice extends AbilitySlice {
    ListContainer listContainer;
    ZadaniaListProvider zadaniaListProvider;
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
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        but3.setBackground(shapeElement);
        animatorProperty = new AnimationButton(1.f,0.f,100,but1,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but1,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);
        animatorProperty5 = new AnimationButton(1.f,0.f,100,but3,true);
        animatorProperty6 =  new AnimationButton(0.f,1.f,100,but3,false);
        listNots = DataHolder.getInstance().getEdytowane();
        DataHolder.getInstance().setAddListyTrescSlice(this);
        DataHolder.getInstance().setAnimationButton(animatorProperty6);
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
                DataHolder.getInstance().setListy(DataHolder.getInstance().getEdytowane());
                DataHolder.getInstance().setState((byte) 4);
                terminateAbility();
            }
        });
        but2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminateAbility();
            }
        });
        but3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {

                ListNot listNot = new ListNot();
                listNot.setDataParentId(-1);
                listNot.setZrobione(false);
                DataHolder.getInstance().setListNot(listNot);
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder().
                        withDeviceId("").
                        withBundleName("com.example.notatnik").
                        withAbilityName("com.example.notatnik.AddZadanie").
                        build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });
    }

    private void inicjalizacja(){
        zadaniaListProvider =  new ZadaniaListProvider(listNots,this);
        listContainer.setItemProvider(zadaniaListProvider);
        listContainer.setCentralScrollMode(true);
        DataHolder.getInstance().setListContainer(listContainer);
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
        if(DataHolder.getInstance().getState() == 4){
            TaskDispatcher taskDispatcher = getGlobalTaskDispatcher(TaskPriority.DEFAULT);
            DataHolder.getInstance().setState((byte) 1);
            zadaniaListProvider.notifyDataSetItemInserted(listContainer.getChildCount());
            listContainer.scrollTo(listContainer.getChildCount()-1);
            juz2 = true;
            taskDispatcher.delayDispatch(new Runnable() {
                @Override
                public void run() {
                    juz4 = true;
                }
            },1000);
        }
        else if(DataHolder.getInstance().getState() == 5){
            DataHolder.getInstance().setState((byte) 1);
            zadaniaListProvider.notifyDataSetItemChanged(DataHolder.getInstance().getPozycja());
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
