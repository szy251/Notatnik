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
        but1.setPosition(120,40);
        but2.setPosition(246, 40);
        textField.setPosition(58,150);
        textField.setText(DataHolder.getInstance().getNazwa());

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
                if(DataHolder.getInstance().getDane().stream().filter(data -> textField.getText().equals(data.getNazwa())).findFirst().orElse(null)==null) {
                    DataHolder.getInstance().setNazwa(textField.getText());
                    DataHolder.getInstance().setState((byte) 3);
                    terminateAbility();
                }
                else{
                    ToastDialog toastDialog = new ToastDialog(getContext());
                    toastDialog.setText("Already used");
                    toastDialog.setDuration(3000);
                    toastDialog.setOffset(0, 158);
                    toastDialog.setSize(366,100);
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

       /* tekst = intent.getSerializableParam("tresc");
        tytul = intent.getSerializableParam("title");
        Integer id = intent.getIntParam("Id_dane",-1);


        TextField textField = (TextField) findComponentById(ResourceTable.Id_dodaj_tytul);
        Button button = (Button)findComponentById(ResourceTable.Id_dalej);
        ScrollView scrollView = (ScrollView)findComponentById(ResourceTable.Id_scroll);

        textField.setText(tytul);
        button.setPosition(0,466-100);
        scrollView.setPosition(0,0);
        PositionLayout positionLayout = (PositionLayout) findComponentById(ResourceTable.Id_ability_add);
        positionLayout.setTouchEventListener(new Component.TouchEventListener() {
            float startPositionX = 0;
            float endPositionX = 0;
            float startPositionY = 0;
            float endPositionY = 0;
            final float horizontalSwipeThreshold = 100;
            final float verticalSwipeThreshold = 50;
            @Override
            public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
                if(touchEvent.getAction() == TouchEvent.PRIMARY_POINT_DOWN){
                    MmiPoint downPoint = touchEvent.getPointerScreenPosition(0);
                    startPositionX = downPoint.getX();
                    startPositionY = downPoint.getY();
                }
                if(touchEvent.getAction() == TouchEvent.PRIMARY_POINT_UP){
                    MmiPoint movePoint = touchEvent.getPointerScreenPosition(0);
                    endPositionX = movePoint.getX();
                    endPositionY = movePoint.getY();
                    if((Math.abs(endPositionY - startPositionY) < verticalSwipeThreshold) &&
                            (endPositionX - startPositionX > horizontalSwipeThreshold)){

                        if(byl)
                        {
                            tekst = textField.getEditableString();
                            textField.setText(tytul);
                            textField.setScrollable(false);
                            textField.setHint("Add title");
                            button.setText("Next");
                            byl = false;
                        }
                        else if(id == -1){
                            Intent intent = new Intent();
                            Operation operation = new Intent.OperationBuilder()
                                    .withDeviceId("")
                                    .withBundleName("com.example.notatnik")
                                    .withAbilityName("com.example.notatnik.MainAbility")
                                    .build();
                            intent.setOperation(operation);
                            startAbility(intent);
                            terminateAbility();
                        }
                        else {
                            Intent intent = new Intent();
                            Operation operation = new Intent.OperationBuilder()
                                    .withDeviceId("")
                                    .withBundleName("com.example.notatnik")
                                    .withAbilityName("com.example.notatnik.Opcje")
                                    .build();
                            intent.setOperation(operation);
                            intent.setParam("title",tytul);
                            intent.setParam("tresc",tekst);
                            intent.setParam("Id_dane",id);
                            startAbility(intent);
                            terminateAbility();
                        }
                    }
                }
                return true;
            }
        });

        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (id == -1) {
                    if (!byl) {
                        tytul = textField.getEditableString();
                        if (tytul.length() <= 0) {
                            ToastDialog toastDialog = new ToastDialog(getContext());
                            toastDialog.setText("Please write title");
                            toastDialog.setOffset(0, 158);
                            toastDialog.setSize(366,150);
                            toastDialog.show();
                        } else if (tytul.length() > 25) {
                            ToastDialog toastDialog = new ToastDialog(getContext());
                            toastDialog.setText("Max 25 characters");
                            toastDialog.setDuration(3000);
                            toastDialog.setOffset(0, 158);
                            toastDialog.setSize(366,150);
                            toastDialog.show();
                        } else if (jest(tytul)) {
                            ToastDialog toastDialog = new ToastDialog(getContext());
                            toastDialog.setText("Title already used");
                            toastDialog.setDuration(3000);
                            toastDialog.setOffset(0, 158);
                            toastDialog.setSize(366,150);
                            toastDialog.show();
                        } else {
                            textField.setText(tekst);
                            textField.setHint("Add note content");
                            button.setText("End");
                            textField.setMultipleLine(true);
                            //  textField.setScrollable(true);
                            positionLayout.clearFocus();
                            byl = true;
                        }
                    }
                    else {
                        tekst = textField.getText();
                        if (tekst.length() == 0) {
                            ToastDialog toastDialog = new ToastDialog(getContext());
                            toastDialog.setText("Can't be empty");
                            toastDialog.setDuration(3000);
                            toastDialog.setOffset(0, 158);
                            toastDialog.setSize(366,150);
                            toastDialog.show();
                        } else {
                            add();
                            Intent intent = new Intent();
                            Operation operation = new Intent.OperationBuilder()
                                    .withDeviceId("")
                                    .withBundleName("com.example.notatnik")
                                    .withAbilityName("com.example.notatnik.MainAbility")
                                    .build();
                            intent.setOperation(operation);
                            startAbility(intent);
                            terminateAbility();
                        }
                    }
                }
                else {
                    if (!byl) {
                        tytul = textField.getEditableString();
                        if (tytul.length() <= 0) {
                            ToastDialog toastDialog = new ToastDialog(getContext());
                            toastDialog.setText("Please write title");
                            toastDialog.setDuration(3000);
                            toastDialog.setOffset(50, 158);
                            toastDialog.setSize(366,150);
                            toastDialog.show();
                        } else if (tytul.length() > 25) {
                            ToastDialog toastDialog = new ToastDialog(getContext());
                            toastDialog.setText("Max 25 characters");
                            toastDialog.setDuration(3000);
                            toastDialog.setOffset(0, 158);
                            toastDialog.setSize(366,150);
                            toastDialog.show();
                        } else if (jest(tytul, id)) {
                            ToastDialog toastDialog = new ToastDialog(getContext());
                            toastDialog.setText("Title already used");
                            toastDialog.setDuration(3000);
                            toastDialog.setOffset(0, 158);
                            toastDialog.setSize(366,150);
                            toastDialog.show();
                        } else {
                            textField.setText(tekst);
                            textField.setHint("Add note content");
                            textField.setMultipleLine(true);
                            button.setText("End");
                            positionLayout.clearFocus();
                            //  textField.setScrollable(true);
                            byl = true;
                        }
                    }
                    else {
                        tekst = textField.getText();
                        if (tekst.length() == 0) {
                            ToastDialog toastDialog = new ToastDialog(getContext());
                            toastDialog.setText("Can't be empty");
                            toastDialog.setDuration(3000);
                            toastDialog.setOffset(0, 158);
                            toastDialog.setSize(366,150);
                            toastDialog.show();
                        } else {
                            update(id);
                            Intent intent = new Intent();
                            Operation operation = new Intent.OperationBuilder()
                                    .withDeviceId("")
                                    .withBundleName("com.example.notatnik")
                                    .withAbilityName("com.example.notatnik.MainAbility")
                                    .build();
                            intent.setOperation(operation);
                            startAbility(intent);
                            terminateAbility();
                        }
                    }
                }
            }
        });*/
    }

   /* private Boolean jest(String tytul){
            helper = new DatabaseHelper(this);
            context = helper.getOrmContext("data","Data.db", Dane.class);
            OrmPredicates ormPredicates = context.where(Data.class).equalTo("nazwa",tytul);
            return !context.query(ormPredicates).isEmpty();

    }
    private Boolean jest(String tytul, Integer id){
        helper = new DatabaseHelper(this);
        context = helper.getOrmContext("data","Data.db", Dane.class);
        OrmPredicates ormPredicates = context.where(Data.class).equalTo("nazwa",tytul).notEqualTo("DataId",id);
        return !context.query(ormPredicates).isEmpty();

    }*/
   /* private void add()
    {
        helper = new DatabaseHelper(this);
        context = helper.getOrmContext("data","Data.db", Dane.class);
        Data on = new Data();
        on.setNazwa(tytul);
        on.setTresc(tekst);
        context.insert(on);
        context.flush();
    }
    private void update(Integer id){
        helper = new DatabaseHelper(this);
        context = helper.getOrmContext("data","Data.db",Dane.class);
        Data on = new Data();
        on.setNazwa(tytul);
        on.setTresc(tekst);
        on.setDataId(id);
        context.delete(context.where(Data.class).equalTo("DataId",id));
        context.insert(on);
        context.flush();
    }*/
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}