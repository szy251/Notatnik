package com.example.notatnik;

import com.example.notatnik.slice.OpcjeSortingSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class OpcjeSorting extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OpcjeSortingSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
