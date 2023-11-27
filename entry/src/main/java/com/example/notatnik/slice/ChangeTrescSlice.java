package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.SmallDataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ScrollView;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;

public class ChangeTrescSlice extends AbilitySlice {
    Button but1, but2;
    TextField textField;
    ScrollView scrollView;
    Boolean juz;
    AnimationButton animatorProperty, animatorProperty2, animatorProperty3, animatorProperty4;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_add_tytul);

        but1 = (Button) findComponentById(ResourceTable.Id_akceptuj_tyt);
        but2 = (Button) findComponentById(ResourceTable.Id_anuluj_tyt);
        textField = (TextField) findComponentById(ResourceTable.Id_dodaj_tytul);
        scrollView = (ScrollView) findComponentById(ResourceTable.Id_scroll_edycja_tekst);
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but1.setBackground(shapeElement);
        but2.setBackground(shapeElement);
        textField.setText(SmallDataHolder.getInstance().getNormalNot().getTresc());
        textField.setTextSize((int)(30*DataHolder.getInstance().getOpcjeData().getTextSize()));
        DataHolder.getInstance().addObecne(getAbility());
        textField.setFocusable(Component.ACCESSIBILITY_ADAPTABLE);
        textField.setTouchFocusable(true);
        scrollView.setTouchFocusable(true);
        scrollView.setFocusable(Component.ACCESSIBILITY_ENABLE);
        scrollView.requestFocus();
        juz = true;
        animatorProperty = new AnimationButton(1.f,0.f,100,but1,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but1,false);
        animatorProperty3 = new AnimationButton(1.f,0.f,100,but2,true);
        animatorProperty4 =  new AnimationButton(0.f,1.f,100,but2,false);

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
        but2.setClickedListener(listener->terminateAbility());
        scrollView.addScrolledListener(new Component.ScrolledListener() {
            @Override
            public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
                if(i1 <= 20 ){
                    if(!juz) {
                        animatorProperty2.start();
                        animatorProperty4.start();
                    }
                    juz = true;
                }
                else if(juz){
                    juz = false;
                    animatorProperty.start();
                    animatorProperty3.start();
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
