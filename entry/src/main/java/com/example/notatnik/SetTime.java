package com.example.notatnik;

import com.example.notatnik.slice.SetTimeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class SetTime extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SetTimeSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
