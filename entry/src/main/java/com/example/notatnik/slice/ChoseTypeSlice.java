package com.example.notatnik.slice;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.AbilitySliceAnimator;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;

public class ChoseTypeSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_type);
        Button but1 = (Button) findComponentById(ResourceTable.Id_wyb_normalne);
        Button but2 = (Button) findComponentById(ResourceTable.Id_wyb_listy);
        DirectionalLayout layout = (DirectionalLayout) findComponentById(ResourceTable.Id_typy);
        AbilitySliceAnimator abilitySliceAnimator = new AbilitySliceAnimator(466,0,466,0);
        getAbility().setAbilitySliceAnimator(abilitySliceAnimator);

        DataHolder.getInstance().setAbility(getAbility());

        but1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder().
                        withDeviceId("").
                        withBundleName("com.example.notatnik").
                        withAbilityName("com.example.notatnik.AddNormal").
                        build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });
        but2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder().
                        withDeviceId("").
                        withBundleName("com.example.notatnik").
                        withAbilityName("com.example.notatnik.AddList").
                        build();
                intent.setOperation(operation);
                startAbility(intent);
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
