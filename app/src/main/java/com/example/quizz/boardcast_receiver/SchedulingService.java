package com.example.quizz.boardcast_receiver;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.quizz.ExtendFunc;
import com.example.quizz.HomeActivity;
import com.example.quizz.MainActivity;
import com.example.quizz.R;
import com.example.quizz.model.Schedual;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SchedulingService extends BroadcastReceiver {
    private static final int TIME_VIBRATE = 1000;

    @Override
    public void onReceive(final Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "quizz_channel")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Ptit Quizz")
                .setContentText("Nhiệm vụ hôm nay: ")
                .setSound(sound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE})
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(001, builder.build());
        Date c= Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String[] date = sdf.format(c).split("-");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("schedules").child(uid).child(ExtendFunc.DateToString(year, month,day));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Schedual schedual = dataSnapshot.getValue(Schedual.class);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    Intent notificationIntent = new Intent(context, MainActivity.class);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, notificationIntent, 0);
                    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "quizz_channel")
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Ptit Quizz")
                            .setContentText("Nhiệm vụ hôm nay: "+schedual.getNote())
                            .setSound(sound)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE})
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    notificationManager.notify(001, builder.build());
                }
            }

            @Override

            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
