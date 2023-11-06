package com.example.notatnik;

import com.example.notatnik.slice.ChangeTimeSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChangeTime extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChangeTimeSlice.class.getName());
    }
}
