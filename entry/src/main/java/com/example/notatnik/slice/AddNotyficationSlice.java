package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.utils.Dni;
import com.example.notatnik.utils.Kolor;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Switch;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;


public class AddNotyficationSlice extends AbilitySlice {
    Button but1, but2, but3, but4;
    Switch on_off;
    Text text1, text2;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_notyfication);
        DataHolder.getInstance().addObecne(getAbility());
        but1 = (Button) findComponentById(ResourceTable.Id_accept_notyfiy);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_notyfi);
        but3 = (Button) findComponentById(ResourceTable.Id_hour_change);
        but4 = (Button) findComponentById(ResourceTable.Id_repeat_change);
        ShapeElement shapeElement = new ShapeElement(getContext(), DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        on_off = (Switch) findComponentById(ResourceTable.Id_on_off);
        on_off.setTrackElement(Kolor.on_off_background(getContext(),Kolor.kod_przycisk(DataHolder.getInstance().getOpcjeData().getPrzycTloId())));
        text1 = (Text) findComponentById(ResourceTable.Id_hour);
        text2 = (Text) findComponentById(ResourceTable.Id_repeat);
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        but3.setPosition(50,155);
        text1.setPosition(133,210);
        but4.setPosition(50, 260);
        text2.setPosition(83,315);
        on_off.setPosition(158,360);
        on_off.setChecked(DataHolder.getInstance().isAlarm());
        DataHolder.getInstance().kopiaCzas();
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                DataHolder.getInstance().setAlarm(on_off.isChecked());
                DataHolder.getInstance().ustawCzas();
                DataHolder.getInstance().setPowtorzenia(!text2.getText().equals("One time"));
                DataHolder.getInstance().setState((byte) 6);
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
                Intent intent1 = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.SetTime")
                        .build();
                intent1.setOperation(operation);
                startAbility(intent1);
            }
        });
        but4.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent1 = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.SetDni")
                        .build();
                intent1.setOperation(operation);
                startAbility(intent1);
            }
        });

    }

    @Override
    public void onActive() {
        text1.setText(String.format("%02d:%02d",DataHolder.getInstance().getGodzinaKopia(),DataHolder.getInstance().getMinutyKopia()));
        text2.setText(Dni.dniTygodnia(DataHolder.getInstance().getWybraneKopia()));
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
