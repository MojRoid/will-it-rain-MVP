package moj.rain.app.domain;


import android.support.annotation.NonNull;

import rx.Subscription;

public interface BaseUseCase {

    void trackSubscription(@NonNull Subscription subscription);

    void cleanUp();
}
