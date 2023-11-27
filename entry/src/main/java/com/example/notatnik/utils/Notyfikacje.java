package com.example.notatnik.utils;


import com.example.notatnik.data.DataHolder;
import ohos.event.notification.*;
import ohos.rpc.RemoteException;

public class Notyfikacje {
    public static void dodaj_slot(boolean dzwiek){
        try {
            ReminderHelper.removeNotificationSlot("slot1");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        NotificationSlot notificationSlot = new NotificationSlot("slot1","notes",NotificationSlot.LEVEL_HIGH);
        notificationSlot.setEnableLight(false);
        notificationSlot.setEnableVibration(true);
        if(!dzwiek) notificationSlot.setSound(null);
        notificationSlot.setVibrationStyle(new long[]{0,3000,0,3000});
        notificationSlot.enableBypassDnd(false);
        try {
            ReminderHelper.addNotificationSlot(notificationSlot);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        DataHolder.getInstance().setStraznik(false);
    }
    public static Integer publikuj(String nazwa, Integer id, Integer godzina, Integer minuta, int[] dni){

        if(DataHolder.getInstance().isStraznik())dodaj_slot(false);
        ReminderRequest reminder = new ReminderRequestAlarm(godzina, minuta, dni);
        reminder.setTitle("Your note").setContent(nazwa);
        reminder.setSnoozeTimes(0).setTimeInterval(0).setRingDuration(2);
        reminder.setIntentAgent("com.example.notatnik", "com.example.notatnik.MainAbility");
        reminder.setSlotId("slot1");
        reminder.setActionButton("Close",ReminderRequest.ACTION_BUTTON_TYPE_CLOSE);
        reminder.setNotificationId(id);
        int z = -1;
        try {
            z  = ReminderHelper.publishReminder(reminder);
        } catch (ReminderManager.AppLimitExceedsException e) {
            e.printStackTrace();
        } catch (ReminderManager.SysLimitExceedsException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return z;
    }
}