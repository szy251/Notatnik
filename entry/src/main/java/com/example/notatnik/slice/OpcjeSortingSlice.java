package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.utils.DataPomocnik;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.RadioButton;
import ohos.agp.components.ScrollView;
import ohos.agp.components.element.ShapeElement;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

public class OpcjeSortingSlice extends AbilitySlice {
    Button but1, but2;
    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4;
    ScrollView scrollView;
    Boolean juz;
    RadioButton radioButton1, radioButton2,radioButton3,radioButton4,radioButton5,radioButton6;
    Integer a;
    DatabaseHelper helper;
    OrmContext context;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_opcje_sorting);
        but1 = (Button) findComponentById(ResourceTable.Id_accept_sorting);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_sorting);
        radioButton1 = (RadioButton) findComponentById(ResourceTable.Id_def_sort);
        radioButton2 = (RadioButton) findComponentById(ResourceTable.Id_def_rev_sort);
        radioButton3 = (RadioButton) findComponentById(ResourceTable.Id_title_sort);
        radioButton4 = (RadioButton) findComponentById(ResourceTable.Id_title_rev_sort);
        radioButton5 = (RadioButton) findComponentById(ResourceTable.Id_type_sort);
        radioButton6 = (RadioButton) findComponentById(ResourceTable.Id_type_rev_sort);
        a = DataHolder.getInstance().getOpcjeData().getSortowTyp();
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
        scrollView = (ScrollView) findComponentById(ResourceTable.Id_scroll_opcje_sorting);
        scrollView.setTouchFocusable(true);
        scrollView.setFocusable(Component.ACCESSIBILITY_ENABLE);
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
                a =1;
            }
        });
        radioButton2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = 2;
            }
        });
        radioButton3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a =3;
            }
        });
        radioButton4.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = 4;
            }
        });
        radioButton5.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = 5;
            }
        });
        radioButton6.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a=6;
            }
        });
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                DataHolder.getInstance().getOpcjeData().setSortowTyp(a);
                helper = new DatabaseHelper(getContext());
                context = helper.getOrmContext("data","Data.db", Dane.class);
                context.update(DataHolder.getInstance().getOpcjeData());
                context.flush();
                DataHolder.getInstance().setState((byte) 2);
                DataPomocnik.sort(a,DataHolder.getInstance().getDane());
                terminateAbility();
            }
        });
    }

    void ustaw(){
        switch (a){
            case 1:
                radioButton1.setChecked(true);
                break;
            case 2:
                radioButton2.setChecked(true);
                break;
            case 3:
                radioButton3.setChecked(true);
                break;
            case 4:
                radioButton4.setChecked(true);
                break;
            case 5:
                radioButton5.setChecked(true);
                break;
            case 6:
                radioButton6.setChecked(true);
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
}
