package moj.rain.app.domain;


import android.support.annotation.NonNull;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseUseCaseImpl implements BaseUseCase {

    private final CompositeSubscription compositeSubscription;

    protected BaseUseCaseImpl() {
        this.compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void trackSubscription(@NonNull Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void cleanUp() {
        compositeSubscription.clear();
    }
}
