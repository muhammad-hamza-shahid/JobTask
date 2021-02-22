package com.example.jobtask;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobtask.model.DrinkResponce;
import com.example.jobtask.network.ApiClient;
import com.example.jobtask.network.ApiService;
import com.example.jobtask.utilities.NotificationBroadcastReciever;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {


    AlarmManager alarmManager;
    PendingIntent pendingIntent;


    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID ="primary_notification_channel";

    TextView toolbarText;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel();

        Intent notifyIntent = new Intent(this, NotificationBroadcastReciever.class);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long repeatInterval = 15 * 1000;
        long triggerTime = SystemClock.elapsedRealtime()
                + repeatInterval;


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE,17);


        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), repeatInterval, notifyPendingIntent);
        }       // deliverNotification(MainActivity.this);











        //alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

       // Intent alarmIntent = new Intent(this, NotificationBroadcastReciever.class);
      //  pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        //startAlarm();

        toolbarText=findViewById(R.id.toolbar_text);

        loadFragment(new HomeFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


//        ComponentName receiver = new ComponentName(MainActivity.this, NotificationBroadcastReciever.class);
//        PackageManager pm = MainActivity.this.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
    }

    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }




    private void startAlarm() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home_page:
                    toolbarText.setText("Drinks Recipes");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.favorite_page:
                    toolbarText.setText("Favorite Recipes");
                    fragment = new FavouriteFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}