package com.desarrollo.appmusica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button play_pause, btn_repetir;
    MediaPlayer mp;
    ImageView iv;
    int repetir = 2, posicion = 0;

    private static final  String PRIMARY_CHANNEL_ID
            = "primary_notifications_channel";
    private static final int  NOTIFICATION_ID
            =100;
    private Button btn_notify;
    private NotificationManager mNotifyManager;


    MediaPlayer vectormp [] = new MediaPlayer [3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_pause = (Button)findViewById(R.id.btn_play);
        btn_repetir = (Button)findViewById(R.id.btn_repetir);
        iv = (ImageView)findViewById(R.id.imageView);

        vectormp[0] = MediaPlayer.create(this, R.raw.filter);
        vectormp[1] = MediaPlayer.create(this, R.raw.oasis);
        vectormp[2] = MediaPlayer.create(this, R.raw.guns);

    }

    //Método para el botón PlayPause
    public void PlayPause(View view){
        if(vectormp[posicion].isPlaying()){
            vectormp[posicion].pause();
            play_pause.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
        } else {
            vectormp[posicion].start();
            play_pause.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para el botón Stop
    public void Stop(View view){
        if(vectormp[posicion] != null){
            vectormp[posicion].stop();

            vectormp[0] = MediaPlayer.create(this, R.raw.filter);
            vectormp[1] = MediaPlayer.create(this, R.raw.oasis);
            vectormp[2] = MediaPlayer.create(this, R.raw.guns);
            posicion = 0;
            play_pause.setBackgroundResource(R.drawable.reproducir);
            iv.setImageResource(R.drawable.portada1);
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
        }
    }

    //Método repetir una pista
    public void Repetir(View view){
        if(repetir == 1){
            btn_repetir.setBackgroundResource(R.drawable.no_repetir);
            Toast.makeText(this, "No repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(false);
            repetir = 2;
        } else {
            btn_repetir.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this, "Repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(true);
            repetir = 1;
        }
    }

    //Método para saltar a la siguiente canción.
    public void Siguiente(View view){
        if(posicion < vectormp.length -1){

            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                posicion++;
                vectormp[posicion].start();

                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }

            } else {
                posicion++;

                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }
            }

        } else {
            Toast.makeText(this, "No hay más canciones", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para regresar a la canción anterior
    public void Anterior(View view){
        if(posicion >= 1){

            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                vectormp[0] = MediaPlayer.create(this, R.raw.filter);
                vectormp[1] = MediaPlayer.create(this, R.raw.oasis);
                vectormp[2] = MediaPlayer.create(this, R.raw.guns);
                posicion--;

                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }

                vectormp[posicion].start();

            } else {
                posicion--;

                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                }else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }
            }

        } else {
            Toast.makeText(this, "No hay más canciones", Toast.LENGTH_SHORT).show();
        }
    }
    public void  createNotificationChannel(){
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel
                    =new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "app Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.
                    setDescription("Notification from app musica");
            mNotifyManager
                    .createNotificationChannel(notificationChannel);
        }
    }
    private NotificationCompat.Builder getNotificationBuilder(){
        NotificationCompat.Builder notifyBuilder
                = new  NotificationCompat.Builder(getApplicationContext()
                ,PRIMARY_CHANNEL_ID)
                .setContentTitle("Has sido notificado!!")
                .setContentText("Este es el texto de la notificacion.")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        return notifyBuilder;
    }
    public void sendNotifications(){
        NotificationCompat.Builder notificationBuilder
                =getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID
                ,notificationBuilder.build());

    }

}