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

public class OpcjeTloSlice extends AbilitySlice {
    Button but1, but2;
    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4;
    ScrollView scrollView;
    Boolean juz;
    RadioButton radioButton1, radioButton2,radioButton3,radioButton4,radioButton5, radioButton6,radioButton7,radioButton8;
    Integer a;
    DatabaseHelper helper;
    OrmContext context;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_opcje_tlo);
        but1 = (Button) findComponentById(ResourceTable.Id_accept_tlo);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_tlo);
        radioButton1 = (RadioButton) findComponentById(ResourceTable.Id_gray);
        radioButton2 = (RadioButton) findComponentById(ResourceTable.Id_blue);
        radioButton3 = (RadioButton) findComponentById(ResourceTable.Id_red);
        radioButton4 = (RadioButton) findComponentById(ResourceTable.Id_turquoise);
        radioButton5 = (RadioButton) findComponentById(ResourceTable.Id_lime);
        radioButton6 = (RadioButton) findComponentById(ResourceTable.Id_purple);
        radioButton7 = (RadioButton) findComponentById(ResourceTable.Id_orange);
        radioButton8 = (RadioButton) findComponentById(ResourceTable.Id_mint);
        DataHolder.getInstance().addObecne(getAbility());
        if(DataHolder.getInstance().getKafelekId() == 1) {
            a = DataHolder.getInstance().getOpcjeData().getNormalTytId();
        }
        else{
            a = DataHolder.getInstance().getOpcjeData().getListTytId();
        }
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        ustaw();
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        juz = true;
        animatorProperty = new AnimationButton(1.f,0.f,100,but1,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but1,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);
        scrollView = (ScrollView) findComponentById(ResourceTable.Id_scroll_opcje_tlo);
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
        radioButton1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_tytuly_gray;
            }
        });
        radioButton2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_tytuly_blue;
            }
        });
        radioButton3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_tytuly_red;
            }
        });
        radioButton4.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_tytuly_turquoise;
            }
        });
        radioButton5.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_tytuly_lime;
            }
        });
        radioButton6.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_tytuly_purple;
            }
        });
        radioButton7.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_tytuly_orange;
            }
        });
        radioButton8.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = ResourceTable.Graphic_tytuly_mint;
            }
        });
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(DataHolder.getInstance().getKafelekId() == 1) {
                    DataHolder.getInstance().getOpcjeData().setNormalTytId(a);
                }
                else{
                    DataHolder.getInstance().getOpcjeData().setListTytId(a);
                }
                helper = new DatabaseHelper(getContext());
                context = helper.getOrmContext("data","Notes.db", Dane.class);
                context.update(DataHolder.getInstance().getOpcjeData());
                context.flush();
                DataHolder.getInstance().setState((byte) 2);
                terminateAbility();
            }
        });
        but2.setClickedListener(listener->terminateAbility());
    }

    void ustaw(){
        switch (a){
            case ResourceTable.Graphic_tytuly_gray:
                radioButton1.setChecked(true);
                break;
            case ResourceTable.Graphic_tytuly_blue:
                radioButton2.setChecked(true);
                break;
            case ResourceTable.Graphic_tytuly_red:
                radioButton3.setChecked(true);
                break;
            case ResourceTable.Graphic_tytuly_turquoise:
                radioButton4.setChecked(true);
                break;
            case ResourceTable.Graphic_tytuly_lime:
                radioButton5.setChecked(true);
                break;
            case ResourceTable.Graphic_tytuly_purple:
                radioButton6.setChecked(true);
                break;
            case ResourceTable.Graphic_tytuly_orange:
                radioButton7.setChecked(true);
                break;
            case ResourceTable.Graphic_tytuly_mint:
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
