package com.example.notatnik.providers;

import com.example.notatnik.ResourceTable;
import com.example.notatnik.data.DataHolder;
import com.example.notatnik.data.ListNot;
import com.example.notatnik.data.SmallDataHolder;
import com.example.notatnik.slice.ChangeListyTrescSlice;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.List;

public class ChangeZadaniaListProvider extends BaseItemProvider {
    private List<ListNot> dane;
    private AbilitySlice slice;

    public ChangeZadaniaListProvider(List<ListNot> dane, AbilitySlice slice) {
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
    public void addDane(ListNot listNot) {
        dane.add(listNot);
        notifyDataChanged();
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
        textField.setTextSize((int)(30* DataHolder.getInstance().getOpcjeData().getTextSize()));

        button.setClickedListener(new Component.ClickedListener() {
            ChangeListyTrescSlice changeListyTrescSlice = (ChangeListyTrescSlice) slice;

            @Override
            public void onClick(Component component) {
                if(d.getListNotId() != null){
                    SmallDataHolder.getInstance().addUsuniete(d);
                    SmallDataHolder.getInstance().removeEdytowane(d);
                }
                else{
                    SmallDataHolder.getInstance().removeNowe(d);
                }
                if(dane.size() > 2){
                    changeListyTrescSlice.falsz();
                }
                changeListyTrescSlice.falsz2();

                dane.remove(i);
                if(SmallDataHolder.getInstance().getListContainer().getCenterFocusablePosition() == dane.size()-1 && !changeListyTrescSlice.isJuz2() && i < 5) {
                    SmallDataHolder.getInstance().getAnimationButton().start();
                    changeListyTrescSlice.prawda3();
                }
                notifyDataChanged();
                TaskDispatcher taskDispatcher = changeListyTrescSlice.getGlobalTaskDispatcher(TaskPriority.DEFAULT);
                taskDispatcher.delayDispatch(new Runnable() {
                    @Override
                    public void run() {
                        changeListyTrescSlice.prawda();
                        changeListyTrescSlice.prawda2();
                    }
                },200);
            }
        });
        textField.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                SmallDataHolder.getInstance().setListNot(d);
                Intent intent = new Intent();
                Operation operation = new Intent.OperationBuilder().
                        withDeviceId("").
                        withBundleName("com.example.notatnik").
                        withAbilityName("com.example.notatnik.ChangeZadanie").
                        build();
                intent.setOperation(operation);
                SmallDataHolder.getInstance().setPozycja(i);
                slice.startAbility(intent);
            }
        });
        return cpt;
    }
}