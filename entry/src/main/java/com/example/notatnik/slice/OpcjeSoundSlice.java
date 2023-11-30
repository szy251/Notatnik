package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.Data;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.utils.DataPomocnik;
import com.example.notatnik.utils.Dni;
import com.example.notatnik.utils.Notyfikacje;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.RadioButton;
import ohos.agp.components.RadioContainer;
import ohos.agp.components.element.ShapeElement;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.event.notification.ReminderHelper;
import ohos.rpc.RemoteException;

import java.time.LocalDateTime;

public class OpcjeSoundSlice extends AbilitySlice {
    Button but1, but2;
    RadioContainer radioContainer;
    RadioButton radioButton, radioButton2;
    String a;
    DatabaseHelper helper;
    OrmContext context;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_opcje_sound);
        but1 = (Button) findComponentById(ResourceTable.Id_accept_sound);
        but2 = (Button) findComponentById(ResourceTable.Id_decline_sound);
        radioContainer = (RadioContainer) findComponentById(ResourceTable.Id_radio_container_sound);
        radioButton = (RadioButton)findComponentById(ResourceTable.Id_sound_on);
        radioButton2 = (RadioButton)findComponentById(ResourceTable.Id_sound_off);
        a = DataHolder.getInstance().getOpcjeData().getSlot();
        DataHolder.getInstance().addObecne(getAbility());
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        radioContainer.setPosition(68,160);
        ustaw();
        radioButton.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = "slot2";
            }
        });

        radioButton2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                a = "slot1";
            }
        });
        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                helper = new DatabaseHelper(getContext());
                context = helper.getOrmContext("data","Notes.db", Dane.class);
                if(!a.equals(DataHolder.getInstance().getOpcjeData().getSlot())){
                    for(Data data : DataHolder.getInstance().getDane()){
                        if(!DataPomocnik.sprawdzAlarm(data,getContext()) && data.getAlarm()){
                            try {
                                ReminderHelper.cancelReminder(data.getAlarmId());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            LocalDateTime dzien = Dni.naDate(data.getDzien());
                            int z = Notyfikacje.publikuj(data.getNazwa(),data.getDataId(),dzien.getHour(),dzien.getMinute(),Dni.naInty(Dni.dostanDni(data)),a);
                            data.setAlarmId(z);
                            context.update(data);
                        }
                    }
                }

                DataHolder.getInstance().getOpcjeData().setSlot(a);
                context.update(DataHolder.getInstance().getOpcjeData());
                context.flush();
                terminateAbility();
            }
        });
    }

    void ustaw(){
        if(a.equals("slot1")) radioButton2.setChecked(true);
        else radioButton.setChecked(true);
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
