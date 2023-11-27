package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.SmallDataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;

public class TrescSlice extends AbilitySlice {
    Text text;
    Button button;
    AnimationButton animationButton, animationButton2;
    Boolean juz;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_tresc);

        ScrollView scrollView = (ScrollView) findComponentById(ResourceTable.Id_scroll);
        scrollView.setRotationSensitivity(ScrollView.ROTATION_SENSITIVITY_HIGH);
        scrollView.setMode(Component.OVAL_MODE);
        scrollView.setCentralScrollMode(true);
        scrollView.setFocusable(Component.ACCESSIBILITY_ENABLE);
        scrollView.setTouchFocusable(true);
        scrollView.requestFocus();
        text = (Text)findComponentById(ResourceTable.Id_text_tresc);
        text.setTextSize((int)(30*DataHolder.getInstance().getOpcjeData().getTextSize()));
        button = (Button)findComponentById(ResourceTable.Id_to_option);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        button.setBackground(shapeElement);
        scrollView.enableScrollBar(Component.AXIS_Y,true);
        scrollView.setVibrationEffectEnabled(true);
        DataHolder.getInstance().addObecne(getAbility());
        text.setMultipleLine(true);
        text.setText(SmallDataHolder.getInstance().getNormalNot().getTresc());
        SmallDataHolder.getInstance().setState((byte) 1);
        SmallDataHolder.getInstance().setState2((byte) 1);
        button.setPosition(183,20);
        animationButton = new AnimationButton(1.f,0.f,100,button,true);
        animationButton2 = new AnimationButton(0.f,1.f,100,button,false);
        juz=true;
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.NormalOpcje")
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });

        scrollView.addScrolledListener(new Component.ScrolledListener() {
            @Override
            public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
                if(i1 <= 20 ){
                    if(!juz) {
                        animationButton2.start();
                    }
                    juz = true;
                }
                else if(juz){
                    juz = false;
                    animationButton.start();
                }
            }
        });
    }

    @Override
    public void onActive() {
        if(SmallDataHolder.getInstance().getState2() == 2){
            text.setText(SmallDataHolder.getInstance().getNormalNot().getTresc());
            SmallDataHolder.getInstance().setState2((byte) 1);
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
