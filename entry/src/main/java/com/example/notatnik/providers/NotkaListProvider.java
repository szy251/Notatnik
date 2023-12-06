package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.Dane;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.ListNot;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.rdb.ValuesBucket;

import java.util.List;

public class NotkaListProvider extends BaseItemProvider {
    private List<ListNot> dane;
    private AbilitySlice slice;

    public NotkaListProvider(List<ListNot> dane, AbilitySlice slice) {
        this.dane =  dane;
        this.slice = slice;
    }
    public class NotkaHolder{
        DirectionalLayout directionalLayout;
        Text text;
        ShapeElement shapeElement,shapeElement2;
        NotkaHolder(Component component){
             directionalLayout = (DirectionalLayout) component.findComponentById(ResourceTable.Id_notka_dodatkowa);
             text = (Text) component.findComponentById(ResourceTable.Id_notka_text);
             text.setTextSize((int)(30*DataHolder.getInstance().getOpcjeData().getTextSize()));

             shapeElement = new ShapeElement(component.getContext(), DataHolder.getInstance().getOpcjeData().getCheckedListId());
             shapeElement2 = new ShapeElement(component.getContext(),ResourceTable.Graphic_tytuly_gray);
        }
    }
    public void setDane(List<ListNot> dane) {
        this.dane = dane;
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
        ListNot d =  dane.get(i);
        NotkaHolder holder;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_notka,null,false);
            holder = new NotkaHolder(cpt);
            cpt.setTag(holder);
        }
        else{
            cpt = component;
            holder = (NotkaHolder) cpt.getTag();
        }

        holder.text.setText(d.getNazwa());
        holder.directionalLayout.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(d.getZrobione()){
                    d.setZrobione(false);
                    holder.directionalLayout.setBackground(holder.shapeElement2);
                }
                else{
                    d.setZrobione(true);
                    holder.directionalLayout.setBackground(holder.shapeElement);
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(cpt.getContext());
                OrmContext ormContext = databaseHelper.getOrmContext("data","Notes.db", Dane.class);
                ValuesBucket valuesBucket = new ValuesBucket();
                valuesBucket.putBoolean("zrobione", d.getZrobione());
                ormContext.update(ormContext.where(ListNot.class).equalTo("ListNotId", d.getListNotId()), valuesBucket);
                ormContext.flush();
            }
        });

        if(d.getZrobione()){
            holder.directionalLayout.setBackground(holder.shapeElement);
        }
        else{
           holder.directionalLayout.setBackground(holder.shapeElement2);
        }
        return cpt;
    }
}