package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.SmallDataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

public class ChangeTrescSlice extends AbilitySlice {
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
        textField.setPosition(58,150);
        textField.setHeight(250);
        textField.setText(SmallDataHolder.getInstance().getNormalNot().getTresc());
        DataHolder.getInstance().addObecne(getAbility());

        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(textField.getText().length() > 0){
                    SmallDataHolder.getInstance().getNormalNot().setTresc(textField.getText());
                    DatabaseHelper helper = new DatabaseHelper(getContext());
                    OrmContext context = helper.getOrmContext("data", "Data.db", Dane.class);
                    context.update(SmallDataHolder.getInstance().getNormalNot());
                    context.flush();
                    SmallDataHolder.getInstance().setState2((byte) 2);
                    SmallDataHolder.getInstance().setState((byte) 3);
                    terminateAbility();
                }
                else{
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Can't be empty");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366, 150);
                    toastDialog.show();
                }
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
