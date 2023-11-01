package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.SmallDataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;

public class TrescSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_tresc);


        String tresc = SmallDataHolder.getInstance().getNormalNot().getTresc();
       // Data d = intent.getSerializableParam("calosc");
        ScrollView scrollView = (ScrollView) findComponentById(ResourceTable.Id_scroll);
        scrollView.setRotationSensitivity(ScrollView.ROTATION_SENSITIVITY_HIGH);
        scrollView.setMode(Component.OVAL_MODE);
        scrollView.setCentralScrollMode(true);
        scrollView.setFocusable(Component.ACCESSIBILITY_ENABLE);
        scrollView.setTouchFocusable(true);
        scrollView.requestFocus();
        //scrollView.fluentScrollByY(-150);
        Text text = (Text)findComponentById(ResourceTable.Id_text_tresc);
        Button button = (Button)findComponentById(ResourceTable.Id_to_option);
        DirectionalLayout directionalLayout = (DirectionalLayout)findComponentById(ResourceTable.Id_tresc);
        DirectionalLayout directionalLayout1 = (DirectionalLayout)findComponentById(ResourceTable.Id_tresc2);
        scrollView.enableScrollBar(Component.AXIS_Y,true);
        scrollView.setVibrationEffectEnabled(true);
        DataHolder.getInstance().addObecne(getAbility());
        text.setMultipleLine(true);
        text.setText(tresc);
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.Opcje")
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
                terminateAbility();
            }
        });

       // directionalLayout1.scrollBy(0,-150);
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
