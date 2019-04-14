package com.example.fig;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyServiceno extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        if(notification != null) {
            this.sendNotification(remoteMessage.getNotification().getBody());
            Log.d("Firefire","FireFire");
        }

    }

    private void sendNotification(String messageBody) {
        // チャネルID。
        String channelId = getString(R.string.default_notification_channel_id);

        // Notificationを生成。
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        notificationBuilder.setContentTitle("通知テスト");//getString(R.string.app_name));
        notificationBuilder.setContentText(messageBody);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setAutoCancel(true);

        // タップ時に呼ばれるIntentを設定。
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        notificationBuilder.setContentIntent(pendingIntent);

        // Notification channelを生成。
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            // APIレベルが26以上の場合、チャネルは最低一つ必要です。
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "create_notifi",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableLights(true);
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
            }
            // Notificationの表示。
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d("Firefire","FireFire" + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }

}
