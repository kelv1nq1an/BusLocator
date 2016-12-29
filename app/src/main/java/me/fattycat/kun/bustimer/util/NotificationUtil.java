package me.fattycat.kun.bustimer.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/7/25
 */

public class NotificationUtil {

    public static void createNotification(Context context, String contentTitle, String content, int notificationId) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                                                      .setSmallIcon(R.drawable.ic_station)
                                                      .setContentTitle(contentTitle)
                                                      .setContentText(content);
        Notification notification = mBuilder.build();
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notificationId, notification);
    }
}
