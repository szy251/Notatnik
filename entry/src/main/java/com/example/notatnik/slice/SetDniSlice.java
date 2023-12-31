package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.providers.DniListProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

import java.util.ArrayList;
import java.util.List;

public class SetDniSlice extends AbilitySlice {
    ListContainer listContainer;
    List<String> dane;
    Button but, but2;
    Boolean juz;
    DniListProvider dniListProvider;
    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_set_dni);
        DataHolder.getInstance().addObecne(getAbility());
        juz = true;
        but = (Button)findComponentById(ResourceTable.Id_accept_dni);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_dni);
        listContainer = (ListContainer)findComponentById(ResourceTable.Id_dni);
        but.setPosition(120,40);
        but2.setPosition(246, 40);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        listContainer.setPosition(0,0);
        animatorProperty = new AnimationButton(1.f,0.f,100,but,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);
        inicjalizacja();
        but.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                DataHolder.getInstance().setWybraneKopia(dniListProvider.dostan());
                terminateAbility();
            }
        });
        but2.setClickedListener(listener-> terminateAbility());
    }

    private void inicjalizacja() {
        stworz();


        dniListProvider = new DniListProvider(dane, this,DataHolder.getInstance().getWybraneKopia());
        listContainer.setItemProvider(dniListProvider);
        listContainer.enableScrollBar(Component.AXIS_Y, true);
        listContainer.setScrollbarBackgroundColor(Color.GRAY);
        listContainer.setScrollbarColor(Color.WHITE);
        listContainer.setFocusable(Component.FOCUS_ADAPTABLE);
        listContainer.requestFocus();
        listContainer.setMode(Component.OVAL_MODE);
        listContainer.setCentralScrollMode(true);
        listContainer.setLongClickable(false);
        listContainer.addScrolledListener(new Component.ScrolledListener() {
            @Override
            public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
                int il = listContainer.getCenterFocusablePosition();
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
        });
    }

    private void stworz(){
        dane = new ArrayList<>();
        dane.add("Monday");
        dane.add("Tuesday");
        dane.add("Wednesday");
        dane.add("Thursday");
        dane.add("Friday");
        dane.add("Saturday");
        dane.add("Sunday");
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
