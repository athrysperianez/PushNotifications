package practicas.elias.pushnotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService(){
    private val TAG = "MyFirebaseMsgService"
    companion object {
        public val CHANNEL_ID  = "Notificaciones_channel"
    }


    override fun onNewToken(token: String?) {

    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.getNotification() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val descriptionText = "Descripcion de ejemplo"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            Thread.sleep(5000)
            var mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(remoteMessage.notification?.title)
                    .setContentText(remoteMessage.notification?.body)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(10, mBuilder.build())
            }
        }
    }
}