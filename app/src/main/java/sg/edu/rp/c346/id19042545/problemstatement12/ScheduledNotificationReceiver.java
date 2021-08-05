package sg.edu.rp.c346.id19042545.problemstatement12;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

public class ScheduledNotificationReceiver extends BroadcastReceiver {
    int reqCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        long id = intent.getLongExtra("id",0);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("default","Default Channel",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("this is default notification");
            notificationManager.createNotificationChannel(channel);
        }


        Intent i = new Intent(context,MainActivity.class);
        PendingIntent pIntent =  PendingIntent.getActivity(context,reqCode,i,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Launch Task Manager",
                pIntent).build();


        Intent intentreply = new Intent(context,
                MainActivity.class);
        intentreply.putExtra("id",id);
        PendingIntent pendingIntentReply = PendingIntent.getActivity
                (context, 0, intentreply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String [] {"completed"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntentReply)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default");
        builder.setContentTitle(name);
        builder.setContentText(desc);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);


        builder.extend(extender);

        Notification n = builder.build();
        notificationManager.notify(123,n);

    }
}