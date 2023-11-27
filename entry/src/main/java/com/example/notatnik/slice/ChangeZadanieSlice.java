package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.SmallDataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;

public class ChangeZadanieSlice extends AbilitySlice {
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
        textField.setText(SmallDataHolder.getInstance().getListNot().getNazwa());
        textField.setTextSize((int)(30*DataHolder.getInstance().getOpcjeData().getTextSize()));
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
                    SmallDataHolder.getInstance().getListNot().setNazwa(textField.getText());
                    if(SmallDataHolder.getInstance().getListNot().getDataParentId() == -1) {
                        SmallDataHolder.getInstance().getListNot().setDataParentId(SmallDataHolder.getInstance().getData().getDataId());
                        SmallDataHolder.getInstance().addKopie(SmallDataHolder.getInstance().getListNot());
                        SmallDataHolder.getInstance().addNowe(SmallDataHolder.getInstance().getListNot());
                        SmallDataHolder.getInstance().setState((byte) 4);
                        SmallDataHolder.getInstance().getChangeListyTrescSlice().falsz2();
                    }
                    else{
                        if(SmallDataHolder.getInstance().getListNot().getListNotId()!=null && SmallDataHolder.getInstance().getEdytowane().stream().noneMatch(listNot -> listNot.getListNotId().equals(SmallDataHolder.getInstance().getListNot().getListNotId()))) {
                            SmallDataHolder.getInstance().addEdytowane(SmallDataHolder.getInstance().getListNot());
                        }
                        SmallDataHolder.getInstance().setState((byte) 5);
                    }
                    terminateAbility();
                }
                else{
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Can't be empty");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366,130);
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
