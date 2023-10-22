package com.example.notatnik;

import com.example.notatnik.slice.ChoseTypeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChoseType extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChoseTypeSlice.class.getName());
    }
}
