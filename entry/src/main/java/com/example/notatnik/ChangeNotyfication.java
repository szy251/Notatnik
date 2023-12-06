package com.example.notatnik;

import com.example.notatnik.slice.ChangeNotyficationSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChangeNotyfication extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChangeNotyficationSlice.class.getName());
        setSwipeToDismiss(true);
    }
}
