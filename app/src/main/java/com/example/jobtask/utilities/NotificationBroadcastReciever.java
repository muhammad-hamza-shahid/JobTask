package com.example.jobtask.utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jobtask.MainActivity;
import com.example.jobtask.R;
import com.example.jobtask.adapters.DrinkListAdapter;
import com.example.jobtask.model.DrinkResponce;
import com.example.jobtask.network.ApiClient;
import com.example.jobtask.network.ApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationBroadcastReciever extends BroadcastReceiver {

  private NotificationManager mNotificationManager;
  private NotificationManagerCompat notificationManagerCompat;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID ="primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManagerCompat = NotificationManagerCompat.from(context);

        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            Intent serviceIntent = new Intent(context, Service.class);
            context.startService(serviceIntent);
        } else {

            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
                  ApiService apiInterface = ApiClient.getApiClient().create(ApiService.class);
                Call<DrinkResponce> call=apiInterface.getByName("Margarita");

                call.enqueue(new Callback<DrinkResponce>() {
                    @Override
                    public void onResponse(Call<DrinkResponce> call, Response<DrinkResponce> response) {
                        if (response.isSuccessful()) {
                            // add your code to get data
                            JsonArray detailsJson = response.body().getAllDrinks().getAsJsonArray();
                            List<DrinkResponce> allDrinks = new ArrayList<>();

                            for (int i = 0; i < detailsJson.size(); i++) {
                                JsonElement drinkElement = detailsJson.get(i);
                                JsonObject drinkObject = drinkElement.getAsJsonObject();
                                DrinkResponce singleDrink = new DrinkResponce(drinkObject.get("strDrink").getAsString(), drinkObject.get("strInstructions").getAsString(), drinkObject.get("strAlcoholic").getAsString(), drinkObject.get("strDrinkThumb").getAsString());
                                allDrinks.add(singleDrink);
                            }
                            if(allDrinks.size()>0)
                            {
                                final int min = 0;
                                final int max = allDrinks.size()-1;
                                int random = new Random().nextInt((max - min) + 1) + min;
                                DrinkResponce singleDrink = allDrinks.get(random);

                                deliverNotification(context , singleDrink.getDrinkName(),singleDrink.getDrinkRecipe());
                            }

                        } else if (!response.isSuccessful()) {
                            //display error message
                            Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                            Log.i("tag", response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<DrinkResponce> call, Throwable t) {
                        Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("tag", t.getMessage());
                    }
                });

            }
            else
            {
                List<DrinkResponce> favouriteDrinks = new ArrayList<>();
                DataBaseHandler dbh = new DataBaseHandler(context);
                favouriteDrinks=dbh.getFavorite();
                final int random;

                if(favouriteDrinks.size()>0)
                {
                    final int min = 0;
                    final int max = favouriteDrinks.size()-1;
                    random = new Random().nextInt((max - min) + 1) + min;
                    DrinkResponce singleDrink = favouriteDrinks.get(random);

                    deliverNotification(context , singleDrink.getDrinkName(),singleDrink.getDrinkRecipe());

                }else {
                    deliverNotification(context , "Drink Time","Need some drinks open app now");
                }
                connected = false;

            }

        }

    }

    private void deliverNotification(Context context,String title,String Description) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        RemoteViews collapsedView = new RemoteViews("com.example.jobtask",
//                R.layout.notification);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle(title)
                .setContentText(Description)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
