package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;

import java.util.List;

public class DniListProvider extends BaseItemProvider {
    private List<String> dane;
    private AbilitySlice slice;
    private boolean[] dzien;


    public DniListProvider(List<String> dane, AbilitySlice slice, boolean[] dzien) {
        this.dane = dane;
        this.slice = slice;
        this.dzien = new boolean[dzien.length];
        System.arraycopy(dzien, 0, this.dzien, 0, dzien.length);

    }

    @Override
    public int getCount() {
        return dane == null ? 0 : dane.size();
    }

    @Override
    public Object getItem(int i) {
        if (dane != null && i >= 0 && i < dane.size()) return dane.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public boolean[] dostan(){
        return dzien;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final Component cpt;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_tytul_usun,null,false);
        }
        else cpt = component;
        String d =  dane.get(i);
        Text text =  (Text) cpt.findComponentById(ResourceTable.Id_nazwa_usun);
        //DirectionalLayout directionalLayout =  (DirectionalLayout) cpt.findComponentById(ResourceTable.Id_tytul_usun);
        Checkbox checkbox = (Checkbox)cpt.findComponentById(ResourceTable.Id_checkbox);

        text.setText(d);

        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setStroke(1,new RgbColor(255,255,255));
        shapeElement.setRgbColor(new RgbColor(0,0,0));
        shapeElement.setCornerRadius(5);
        checkbox.setBackground(shapeElement);


        checkbox.setCheckedStateChangedListener((cos, state)->{
            dzien[i] = state;
            if(state){
                shapeElement.setRgbColor(new RgbColor(0,148,125));
                shapeElement.setStroke(0, new RgbColor(255,255,255));
            }
            else{
                shapeElement.setRgbColor(new RgbColor(0,0,0));
                shapeElement.setStroke(1, new RgbColor(255,255,255));
            }
        });
        if(dzien[i]){
            checkbox.setChecked(true);
        }
        return cpt;
    }
}