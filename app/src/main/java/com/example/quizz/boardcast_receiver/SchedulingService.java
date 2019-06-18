package com.example.quizz.boardcast_receiver;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
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

public class SchedulingService extends IntentService {
    private static final int TIME_VIBRATE = 1000;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SchedulingService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Notification", "Runned");
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
                    NotificationManager notificationManager = (NotificationManager)SchedulingService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent notificationIntent = new Intent(SchedulingService.this, MainActivity.class);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent= PendingIntent.getActivity(SchedulingService.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(SchedulingService.this)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("Ptit Quizz")
                            .setContentText("Nhiệm vụ hôm nay: "+schedual.getNote())
                            .setSound(sound)
                            .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE})
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    notificationManager.notify(1, builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
