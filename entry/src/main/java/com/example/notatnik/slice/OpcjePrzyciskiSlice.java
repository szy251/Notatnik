package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.RadioButton;
import ohos.agp.components.ScrollView;
import ohos.agp.components.element.ShapeElement;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

public class OpcjePrzyciskiSlice extends AbilitySlice {
    Button but1, but2;
    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4;
    ScrollView scrollView;
    Boolean juz;
    RadioButton radioButton2,radioButton3,radioButton4,radioButton5, radioButton6,radioButton7,radioButton8;
    Integer a;
    DatabaseHelper helper;
    OrmContext context;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_opcje_checked_list);
        but1 = (Button) findComponentById(ResourceTable.Id_accept_tlo_checked);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_tlo_checked);
        radioButton2 = (RadioButton) findComponentById(ResourceTable.Id_blue_checked);
        radioButton3 = (RadioButton) findComponentById(ResourceTable.Id_red_checked);
        radioButton4 = (RadioButton) findComponentById(ResourceTable.Id_turquoise_checked);
        radioButton5 = (RadioButton) findComponentById(ResourceTable.Id_lime_checked);
        radioButton6 = (RadioButton) findComponentById(ResourceTable.Id_purple_checked);
        radioButton7 = (RadioButton) findComponentById(ResourceTable.Id_orange_checked);
        radioButton8 = (RadioButton) findComponentById(ResourceTable.Id_mint_checked);
        a = DataHolder.getInstance().getOpcjeData().getPrzycTloId();
        DataHolder.getInstance().addObecne(getAbility());
        ustaw();
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        juz = true;
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        animatorProperty = new AnimationButton(1.f,0.f,100,but1,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but1,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);
        scrollView = (ScrollView) findComponentById(ResourceTable.Id_scroll_opcje_tlo_checked);
        scrollView.setFocusable(Component.ACCESSIBILITY_ENABLE);
        scrollView.setTouchFocusable(true);
        scrollView.requestFocus();

        scrollView.addScrolledListener(new Component.ScrolledListener() {
            @Override
            public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
                if(i1 <= 20 ){
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
        radioButton2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_przycisk_blue;
            }
        });
        radioButton3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_przycisk_red;
            }
        });
        radioButton4.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_przycisk_turquoise;
            }
        });
        radioButton5.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_przycisk_lime;
            }
        });
        radioButton6.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_przycisk_purple;
            }
        });
        radioButton7.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_przycisk_orange;
            }
        });
        radioButton8.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_przycisk_mint;
            }
        });
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                DataHolder.getInstance().getOpcjeData().setPrzycTloId(a);
                helper = new DatabaseHelper(getContext());
                context = helper.getOrmContext("data","Notes.db", Dane.class);
                context.update(DataHolder.getInstance().getOpcjeData());
                context.flush();
                DataHolder.getInstance().setPrzyciski(true);
                terminateAbility();
            }
        });
        but2.setClickedListener(listener-> terminateAbility());
    }

    void ustaw(){
        switch (a){
            case ResourceTable.Graphic_przycisk_blue:
                radioButton2.setChecked(true);
                break;
            case ResourceTable.Graphic_przycisk_red:
                radioButton3.setChecked(true);
                break;
            case ResourceTable.Graphic_przycisk_turquoise:
                radioButton4.setChecked(true);
                break;
            case ResourceTable.Graphic_przycisk_lime:
                radioButton5.setChecked(true);
                break;
            case ResourceTable.Graphic_przycisk_purple:
                radioButton6.setChecked(true);
                break;
            case ResourceTable.Graphic_przycisk_orange:
                radioButton7.setChecked(true);
                break;
            case ResourceTable.Graphic_przycisk_mint:
                radioButton8.setChecked(true);
                break;
        }
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
