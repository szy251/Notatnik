package com.example.notatnik.utils;

import com.example.notatnik.ResourceTable;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentState;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.components.element.StateElement;
import ohos.app.Context;

public class Kolor {
    public static String nazwa(Integer kolor){
        switch(kolor){
            case ResourceTable.Graphic_przycisk_gray:
            case ResourceTable.Graphic_tytuly_gray:
                return "Gray";
            case ResourceTable.Graphic_przycisk_blue:
            case ResourceTable.Graphic_tytuly_blue:
                return "Blue";
            case ResourceTable.Graphic_przycisk_red:
            case ResourceTable.Graphic_tytuly_red:
                return "Red";
            case ResourceTable.Graphic_przycisk_turquoise:
            case ResourceTable.Graphic_tytuly_turquoise:
                return "Turquoise";
            case ResourceTable.Graphic_przycisk_lime:
            case ResourceTable.Graphic_tytuly_lime:
                return "Lime";
            case ResourceTable.Graphic_przycisk_purple:
            case ResourceTable.Graphic_tytuly_purple:
                return "Purple";
            case ResourceTable.Graphic_przycisk_orange:
            case ResourceTable.Graphic_tytuly_orange:
                return "Orange";
            case ResourceTable.Graphic_przycisk_mint:
            case ResourceTable.Graphic_tytuly_mint:
                return "Mint";
        }
        return "";
    }
    public static int kod_przycisk(Integer kod){
        switch(kod){
            case ResourceTable.Graphic_przycisk_blue:
                return ResourceTable.Graphic_track_element_on_blue;
            case ResourceTable.Graphic_przycisk_red:
                return ResourceTable.Graphic_track_element_on_red;
            case ResourceTable.Graphic_przycisk_turquoise:
                return ResourceTable.Graphic_track_element_on_turquoise;
            case ResourceTable.Graphic_przycisk_lime:
                return ResourceTable.Graphic_track_element_on_lime;
            case ResourceTable.Graphic_przycisk_purple:
                return ResourceTable.Graphic_track_element_on_purple;
            case ResourceTable.Graphic_przycisk_orange:
                return ResourceTable.Graphic_track_element_on_orange;
            case ResourceTable.Graphic_przycisk_mint:
                return ResourceTable.Graphic_track_element_on_mint;
        }
        return ResourceTable.Graphic_track_element_on_turquoise;
    }

    public static StateElement on_off_background(Context context,int on_kod){
        StateElement stateElement = new StateElement();
        ShapeElement on = new ShapeElement(context,on_kod);
        ShapeElement off = new ShapeElement(context, ResourceTable.Graphic_track_element_off);
        stateElement.addState(new int[]{ComponentState.COMPONENT_STATE_CHECKED}, on);
        stateElement.addState(new int[]{ComponentState.COMPONENT_STATE_EMPTY}, off);
        return  stateElement;
    }
    public static StateElement check_background(RgbColor color){
        StateElement stateElement = new StateElement();
        ShapeElement zaznaczony = new ShapeElement();
        ShapeElement niezaznaczony = new ShapeElement();
        zaznaczony.setRgbColor(color);
        zaznaczony.setStroke(4,new RgbColor(255,255,255));
        zaznaczony.setCornerRadius(5);
        niezaznaczony.setStroke(4,new RgbColor(255,255,255));
        niezaznaczony.setRgbColor(new RgbColor(0,0,0));
        niezaznaczony.setCornerRadius(5);
        stateElement.addState(new int[]{ComponentState.COMPONENT_STATE_CHECKED}, zaznaczony);
        stateElement.addState(new int[]{ComponentState.COMPONENT_STATE_EMPTY}, niezaznaczony);
        return  stateElement;
    }
}