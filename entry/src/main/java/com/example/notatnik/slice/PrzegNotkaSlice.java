package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.animations.AnimationButton;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.ListNot;
import com.example.notatnik.data.SmallDataHolder;
import com.example.notatnik.providers.NotkaListProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.ListContainer;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

import java.util.List;

public class PrzegNotkaSlice extends AbilitySlice {
    ListContainer listContainer;
    List<ListNot> dane;
    Image but;
    Boolean juz;
    NotkaListProvider notkaListProvider;
    AnimationButton animatorProperty, animatorProperty2;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_przeg_notka);
        but = (Image) findComponentById(ResourceTable.Id_przeg_notka_wiecej);
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_notki);
        animatorProperty = new AnimationButton(1.f,0.f,100,but,true);
        animatorProperty2 = new AnimationButton(0.f,1.f,100,but,false);
        but.setPosition(183,20);
        ShapeElement shapeElement = new ShapeElement(getContext(),DataHolder.getInstance().getOpcjeData().getPrzycTloId());
        but.setBackground(shapeElement);
        listContainer.setPosition(0,0);
        juz = true;
        SmallDataHolder.getInstance().setState((byte) 1);
        SmallDataHolder.getInstance().setState2((byte) 1);
        DataHolder.getInstance().addObecne(getAbility());
        inicjalizacja();

        but.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.notatnik")
                        .withAbilityName("com.example.notatnik.ListOpcje")
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });
    }

    private void inicjalizacja(){
        dane = SmallDataHolder.getInstance().getListNots();
        notkaListProvider = new NotkaListProvider(dane,this);
        listContainer.setItemProvider(notkaListProvider);
        listContainer.enableScrollBar(Component.AXIS_Y,true);
        listContainer.setScrollbarBackgroundColor(Color.GRAY);
        listContainer.setScrollbarColor(Color.WHITE);
        listContainer.setFocusable(Component.FOCUS_ADAPTABLE);
        listContainer.requestFocus();
        listContainer.setMode(Component.OVAL_MODE);
        listContainer.setCentralScrollMode(true);
        listContainer.setLongClickable(false);
        listContainer.addScrolledListener(new Component.ScrolledListener() {
            @Override
            public void onContentScrolled(Component component, int i, int i1, int i2, int i3) {
                int il = listContainer.getCenterFocusablePosition();
                if(il <= 0 ){
                    if(!juz) {
                        animatorProperty2.start();
                    }
                    juz = true;
                }
                else if(juz){
                    juz = false;
                    animatorProperty.start();
                }
            }
        });
    }
    @Override
    public void onActive() {
        if(SmallDataHolder.getInstance().getState2() == 2){
            notkaListProvider.setDane(SmallDataHolder.getInstance().getListNots());
            notkaListProvider.notifyDataChanged();
            SmallDataHolder.getInstance().setState2((byte) 1);
        }
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
