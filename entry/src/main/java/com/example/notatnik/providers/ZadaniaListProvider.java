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
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_zadanie,null,false);
        }
        else cpt = component;

        ListNot d =  dane.get(i);

        Text textField = (Text) cpt.findComponentById(ResourceTable.Id_tresc_zadanie);
        Button button = (Button) cpt.findComponentById(ResourceTable.Id_usun_zadanie);
        textField.setText(d.getNazwa());
        textField.setTextSize((int)(30*DataHolder.getInstance().getOpcjeData().getTextSize()));
        button.setClickedListener(new Component.ClickedListener() {
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
        textField.setClickedListener(new Component.ClickedListener() {
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
        return cpt;
    }
}