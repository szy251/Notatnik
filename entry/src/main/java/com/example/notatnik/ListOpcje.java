package com.example.notatnik;

import com.example.notatnik.slice.ListOpcjeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ListOpcje extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ListOpcjeSlice.class.getName());
    }
}
