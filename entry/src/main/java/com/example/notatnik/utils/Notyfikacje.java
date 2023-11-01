package com.example.notatnik.utils;


import ohos.event.notification.*;
import ohos.rpc.RemoteException;

public class Notyfikacje {
    public static Integer publikuj(String nazwa, Integer id, Integer godzina, Integer minuta, int[] dni){
        // Create a notification slot
        NotificationSlot notificationSlot = new NotificationSlot("slot1","notes",NotificationSlot.LEVEL_HIGH);
        notificationSlot.setEnableLight(false);
        notificationSlot.setEnableVibration(true);
        notificationSlot.setSound(null);
        notificationSlot.setVibrationStyle(new long[]{0,3000,0,3000});
        notificationSlot.enableBypassDnd(false);
// Add the notification slot to the system
        try {
            ReminderHelper.addNotificationSlot(notificationSlot);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

// Create a reminder request
        ReminderRequest reminder = new ReminderRequestAlarm(godzina, minuta, dni);
        reminder.setTitle("Your note").setContent(nazwa);
        reminder.setSnoozeTimes(0).setTimeInterval(0).setRingDuration(2);
// Set the intent agent
        reminder.setIntentAgent("com.example.notatnik", "com.example.notatnik.MainAbility");
// Set the notification slot id
        reminder.setSlotId("slot1");
        reminder.setActionButton("Close",ReminderRequest.ACTION_BUTTON_TYPE_CLOSE);
        reminder.setNotificationId(id);
// Publish the reminder
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