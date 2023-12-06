package com.example.notatnik;

import com.example.notatnik.slice.AddNotyficationSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AddNotyfication extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AddNotyficationSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
