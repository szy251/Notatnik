package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.SmallDataHolder;
import com.example.notatnik.utils.DataPomocnik;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;

public class ChangeTytulSlice extends AbilitySlice {
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
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        textField.setText(SmallDataHolder.getInstance().getData().getNazwa());
        textField.setTextSize((int)(40*DataHolder.getInstance().getOpcjeData().getTextSize()));
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
                 if(SmallDataHolder.getInstance().getData().getNazwa().equals(textField.getText())) terminateAbility();
                 else if (DataHolder.getInstance().getDane().stream().filter(data -> textField.getText().equals(data.getNazwa())).findFirst().orElse(null) == null && textField.getText().length() > 0) {
                    SmallDataHolder.getInstance().getData().setNazwa(textField.getText());
                    DataHolder.getInstance().setState((byte) 2);
                    SmallDataHolder.getInstance().setState((byte) 2);
                    DataPomocnik.aktualizujData(SmallDataHolder.getInstance().getData(),getContext());
                    if(DataHolder.getInstance().getOpcjeData().getSortowTyp() == 3 || DataHolder.getInstance().getOpcjeData().getSortowTyp() ==4){
                        DataPomocnik.sort(DataHolder.getInstance().getOpcjeData().getSortowTyp(),DataHolder.getInstance().getDane());
                    }
                    terminateAbility();
                }
                else if(textField.getText().length()==0) {
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Can't be empty");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366, 160);
                    toastDialog.show();
                }
                else {
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText(" Already used");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(386, 100);
                    toastDialog.show();
                }
            }
        });
        but2.setClickedListener(listener->terminateAbility());
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
