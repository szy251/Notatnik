package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.ListNot;
import com.example.notatnik.slice.AddListyTrescSlice;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.List;

public class ZadaniaListProvider extends BaseItemProvider {
    private List<ListNot> dane;
    private AbilitySlice slice;

    public ZadaniaListProvider(List<ListNot> dane, AbilitySlice slice) {
        this.dane =  dane;
        this.slice = slice;
    }
    public class ZadaniaHolder{
        Text textField;
        Button button;
        ZadaniaHolder(Component component){
            textField = (Text) component.findComponentById(ResourceTable.Id_tresc_zadanie);
            button = (Button) component.findComponentById(ResourceTable.Id_usun_zadanie);
            textField.setTextSize((int)(30*DataHolder.getInstance().getOpcjeData().getTextSize()));
        }
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
        ZadaniaHolder holder;
        if(component ==  null)
        {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_zadanie,null,false);
            holder = new ZadaniaHolder(cpt);
            cpt.setTag(holder);
        }
        else {
            cpt = component;
            holder = (ZadaniaHolder) cpt.getTag();
        }

        holder.button.setClickedListener(new Component.ClickedListener() {
            AddListyTrescSlice addListyTrescSlice = (AddListyTrescSlice) slice;

            @Override
            public void onClick(Component component) {
                if(dane.size() > 2){
                    addListyTrescSlice.falsz();
                }
                addListyTrescSlice.falsz2();
                dane.remove(i);
                if(DataHolder.getInstance().getListContainer().getCenterFocusablePosition() == dane.size()-1 && !addListyTrescSlice.isJuz2() && i < 5){
                    DataHolder.getInstance().getAnimationButton().start();
                    addListyTrescSlice.prawda3();
                }
                notifyDataChanged();
                TaskDispatcher taskDispatcher = addListyTrescSlice.getGlobalTaskDispatcher(TaskPriority.DEFAULT);
                taskDispatcher.delayDispatch(new Runnable() {
                    @Override
                    public void run() {
                        addListyTrescSlice.prawda();
                        addListyTrescSlice.prawda2();
                    }
                },200);
            }
        });
        holder.textField.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                DataHolder.getInstance().setListNot(d);
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder().
                        withDeviceId("").
                        withBundleName("com.example.notatnik").
                        withAbilityName("com.example.notatnik.AddZadanie").
                        build();
                intent.setOperation(operation);
                DataHolder.getInstance().setPozycja(i);
                slice.startAbility(intent);
            }
        });
        holder.textField.setText(d.getNazwa());

        return cpt;
    }
}