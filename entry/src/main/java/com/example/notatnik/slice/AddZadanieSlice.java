package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;

public class AddZadanieSlice extends AbilitySlice {
    Button but1, but2;
    TextField textField;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_tytul);
        but1 = (Button) findComponentById(ResourceTable.Id_akceptuj_tyt);
        but2 = (Button) findComponentById(ResourceTable.Id_anuluj_tyt);
        textField = (TextField) findComponentById(ResourceTable.Id_dodaj_tytul);
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        textField.setPosition(58,150);
        textField.setHint("Add text");
        textField.setTextSize((int)(40*DataHolder.getInstance().getOpcjeData().getTextSize()));
        textField.setText(DataHolder.getInstance().getListNot().getNazwa());
        DataHolder.getInstance().addObecne(getAbility());

        textField.addTextObserver(new Text.TextObserver() {
            @Override
            public void onTextUpdated(String s, int i, int i1, int i2) {
                if(s.length() > 25){
                    textField.setText(s.substring(0,25));
                }
            }
        });

        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(!textField.getText().equals("")) {
                    DataHolder.getInstance().getListNot().setNazwa(textField.getText());
                    if(DataHolder.getInstance().getListNot().getDataParentId() == -1) {
                        DataHolder.getInstance().getListNot().setDataParentId(DataHolder.getInstance().getLastId());
                        DataHolder.getInstance().addEdytowane(DataHolder.getInstance().getListNot());
                        DataHolder.getInstance().setState((byte) 4);
                        DataHolder.getInstance().getAddListyTrescSlice().falsz2();
                    }
                    else{
                        DataHolder.getInstance().setState((byte) 5);
                    }
                    terminateAbility();
                }
                else{
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Can't be empty");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366,160);
                    toastDialog.show();
                }

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
