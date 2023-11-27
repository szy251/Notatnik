package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.RadioButton;
import ohos.agp.components.RadioContainer;
import ohos.agp.components.element.ShapeElement;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

public class OpcjeTextSizeSlice extends AbilitySlice {
    Button but1, but2;
    RadioContainer radioContainer;
    RadioButton radioButton, radioButton2,radioButton3;
    Float a;
    DatabaseHelper helper;
    OrmContext context;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_opcje_text_size);
        but1 = (Button) findComponentById(ResourceTable.Id_accept_wielkosc);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_wielkosc);
        radioContainer = (RadioContainer) findComponentById(ResourceTable.Id_radio_container);
        radioButton = (RadioButton)findComponentById(ResourceTable.Id_size_small);
        radioButton2 = (RadioButton)findComponentById(ResourceTable.Id_size_medium);
        radioButton3 = (RadioButton)findComponentById(ResourceTable.Id_size_large);
        a = DataHolder.getInstance().getOpcjeData().getTextSize();
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        radioContainer.setPosition(83,160);
        if(a==0.8f){
            radioButton.setChecked(true);
        }
        else if(a==1.f){
            radioButton2.setChecked(true);
        }
        else{
            radioButton3.setChecked(true);
        }
        radioButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
               a = 0.8f;
            }
        });
        radioButton2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = 1.f;
            }
        });
        radioButton3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = 1.2f;
            }
        });
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                helper = new DatabaseHelper(getContext());
                context = helper.getOrmContext("data","Data.db", Dane.class);
                DataHolder.getInstance().getOpcjeData().setTextSize(a);
                context.update(DataHolder.getInstance().getOpcjeData());
                context.flush();
                DataHolder.getInstance().setState((byte) 2);
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
}
