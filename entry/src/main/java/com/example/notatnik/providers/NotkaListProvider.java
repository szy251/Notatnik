package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.ListNot;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;

import java.util.List;

public class NotkaListProvider extends BaseItemProvider {
    private List<ListNot> dane;
    private AbilitySlice slice;

    public NotkaListProvider(List<ListNot> dane, AbilitySlice slice) {
        this.dane =  dane;
        this.slice = slice;
    }

    @Override
    public int getCount() {
        return dane == null? 0:dane.size();
    }

    @Override
    public Object getItem(int i) {
        if(dane !=null && i >=0 && i < dane.size()) return dane.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        final Component cpt;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_notka,null,false);
        }
        else cpt = component;

        ListNot d =  dane.get(i);

        DirectionalLayout directionalLayout = (DirectionalLayout) cpt.findComponentById(ResourceTable.Id_notka_dodatkowa);
        Text text = (Text) cpt.findComponentById(ResourceTable.Id_notka_text);

        text.setText(d.getNazwa());
        ShapeElement shapeElement = new ShapeElement(cpt.getContext(), ResourceTable.Graphic_zaznaczone);
        ShapeElement shapeElement2 = new ShapeElement(cpt.getContext(),ResourceTable.Graphic_tytuly);
        if(d.getZrobione()){
            directionalLayout.setBackground(shapeElement);
        }
        directionalLayout.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(d.getZrobione()){
                    d.setZrobione(false);
                    directionalLayout.setBackground(shapeElement2);
                }
                else{
                    d.setZrobione(true);
                    directionalLayout.setBackground(shapeElement);
                }
            }
        });
        return cpt;
    }
}