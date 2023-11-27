package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.SmallDataHolder;
import com.example.notatnik.utils.DataPomocnik;
import com.example.notatnik.utils.Dni;
import com.example.notatnik.utils.Kolor;
import com.example.notatnik.utils.Notyfikacje;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Switch;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.event.notification.ReminderHelper;
import ohos.rpc.RemoteException;

public class ChangeNotyficationSlice extends AbilitySlice {
    Button but1, but2, but3, but4;
    Switch on_off;
    Text text1, text2;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_notyfication);
        but1 = (Button) findComponentById(ResourceTable.Id_accept_notyfiy);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_notyfi);
        but3 = (Button) findComponentById(ResourceTable.Id_hour_change);
        but4 = (Button) findComponentById(ResourceTable.Id_repeat_change);
        on_off = (Switch) findComponentById(ResourceTable.Id_on_off);
        text1 = (Text) findComponentById(ResourceTable.Id_hour);
        text2 = (Text) findComponentById(ResourceTable.Id_repeat);
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        but3.setPosition(50,155);
        text1.setPosition(133,210);
        but4.setPosition(50, 260);
        text2.setPosition(83,315);
        on_off.setPosition(158,360);
        on_off.setTrackElement(Kolor.on_off_background(getContext(),Kolor.kod_przycisk(DataHolder.getInstance().getOpcjeData().getPrzycTloId())));
        on_off.setChecked(SmallDataHolder.getInstance().getData().getAlarm());
        DataHolder.getInstance().addObecne(getAbility());
        SmallDataHolder.getInstance().setGodzina(SmallDataHolder.getInstance().getDzien().getHour());
        SmallDataHolder.getInstance().setMinuty(SmallDataHolder.getInstance().getDzien().getMinute());
        SmallDataHolder.getInstance().setWybrane(Dni.dostanDni(SmallDataHolder.getInstance().getData()));


        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(SmallDataHolder.getInstance().getData().getAlarm()){
                    try {
                        ReminderHelper.cancelReminder(SmallDataHolder.getInstance().getData().getAlarmId());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                SmallDataHolder.getInstance().setDzien(Dni.jutoLubDzis(SmallDataHolder.getInstance().getGodzina(),SmallDataHolder.getInstance().getMinuty()));
                Dni.ustawDni(SmallDataHolder.getInstance().getData(),SmallDataHolder.getInstance().getWybrane());
                SmallDataHolder.getInstance().getData().setPowtorz(!text2.getText().equals("One time"));
                SmallDataHolder.getInstance().getData().setAlarm(on_off.isChecked());
                SmallDataHolder.getInstance().getData().setDzien(Dni.naString(SmallDataHolder.getInstance().getDzien()));
                if(on_off.isChecked()){
                    SmallDataHolder.getInstance().getData().
                            setAlarmId(Notyfikacje.publikuj(SmallDataHolder.getInstance().getData().getNazwa(),
                                    SmallDataHolder.getInstance().getData().getDataId(),
                                    SmallDataHolder.getInstance().getGodzina(),
                                    SmallDataHolder.getInstance().getMinuty(),
                                    Dni.naInty(SmallDataHolder.getInstance().getWybrane())));
                }
                DataPomocnik.aktualizujData(SmallDataHolder.getInstance().getData(), getContext());
                DataHolder.getInstance().setState((byte) 2);
                SmallDataHolder.getInstance().setState((byte) 4);
                terminateAbility();
            }
        });
        but2.setClickedListener(listener->terminateAbility());
        but3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent1 = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.ChangeTime")
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
                        .withAbilityName("com.example.notatnik.ChangeDni")
                        .build();
                intent1.setOperation(operation);
                startAbility(intent1);
            }
        });
    }

    @Override
    public void onActive() {
        text1.setText(SmallDataHolder.getInstance().getGodzina() + ":" + SmallDataHolder.getInstance().getMinuty());
        text2.setText(Dni.dniTygodnia(SmallDataHolder.getInstance().getWybrane()));
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
