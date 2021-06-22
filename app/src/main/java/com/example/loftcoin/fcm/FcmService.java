package com.example.loftcoin.fcm;

import androidx.annotation.NonNull;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.LoftApp;
import com.example.loftcoin.R;
import com.example.loftcoin.ui.main.MainActivity;
import com.example.loftcoin.util.Notifier;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class FcmService extends FirebaseMessagingService {

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    Notifier notifier;

    @Override
    public void onCreate(){
        super.onCreate();
        final BaseComponent baseComponent = ((LoftApp) getApplication()).getComponent();
        DaggerFcmComponent.builder().baseComponent(baseComponent).build().inject(this);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        final RemoteMessage.Notification notification = remoteMessage.getNotification();
        if(notification != null) {
            disposable.add(notifier.sendMessage(
                    Objects.toString(notification.getTitle(), getString(R.string.app_name)),
                    Objects.toString(notification.getBody(), "Something wrong!"),
                    MainActivity.class
            ).subscribe());

        }
    }

    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
