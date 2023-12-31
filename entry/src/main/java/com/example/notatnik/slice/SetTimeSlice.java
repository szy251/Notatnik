package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TimePicker;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

import java.util.Arrays;

public class SetTimeSlice extends AbilitySlice {
    Button but1, but2;
    TimePicker timePicker;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_set_time);
        but1 = (Button) findComponentById(ResourceTable.Id_accept_time);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_time);
        timePicker = (TimePicker) findComponentById(ResourceTable.Id_timepicker);
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        RgbColor color = Arrays.stream(shapeElement.getRgbColors()).findFirst().get();
        timePicker.setOperatedTextColor(new Color(color.asArgbInt()));
        timePicker.setPosition(123,160);
        timePicker.enableSecond(false);
        timePicker.showSecond(false);
        timePicker.setWheelModeEnabled(true);
        timePicker.setMinute(DataHolder.getInstance().getMinutyKopia());
        timePicker.setHour(DataHolder.getInstance().getGodzinaKopia());
        timePicker.setTouchFocusable(true);
        timePicker.requestFocus();
        DataHolder.getInstance().addObecne(getAbility());
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                DataHolder.getInstance().setGodzinaKopia(timePicker.getHour());
                DataHolder.getInstance().setMinutyKopia(timePicker.getMinute());
                terminateAbility();
            }
        });
        but2.setClickedListener(listener-> terminateAbility());
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
