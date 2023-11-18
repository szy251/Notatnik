package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;

public class AddTytulSlice extends AbilitySlice {
    Button but1, but2;
    TextField textField;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_tytul);
        but1 = (Button) findComponentById(ResourceTable.Id_akceptuj_tyt);
        but2 = (Button) findComponentById(ResourceTable.Id_anuluj_tyt);
        textField = (TextField) findComponentById(ResourceTable.Id_dodaj_tytul);
        but1.setPosition(120, 40);
        but2.setPosition(246, 40);
       // textField.setPosition(58, 150);
        textField.setText(DataHolder.getInstance().getNazwa());
        DataHolder.getInstance().addObecne(getAbility());

        textField.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if (s.length() > 25) {
                    textField.setText(s.substring(0, 25));
                }
            }
        });

        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (DataHolder.getInstance().getDane().stream().filter(data -> textField.getText().equals(data.getNazwa())).findFirst().orElse(null) == null) {
                    DataHolder.getInstance().setNazwa(textField.getText());
                    DataHolder.getInstance().setState((byte) 3);
                    terminateAbility();
                } else {
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Already used");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366, 100);
                    toastDialog.show();
                }

            }
        });

        but2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
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
    @Override
    protected void onStop() {
        DataHolder.getInstance().removeformObecne(getAbility());
        super.onStop();
    }
}
