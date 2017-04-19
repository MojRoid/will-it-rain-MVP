package moj.rain.app.data;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import timber.log.Timber;

public abstract class BaseDataAdapter<SOURCE, DESTINATION> {

    private final Scheduler computationScheduler;
    private final Scheduler mainThreadScheduler;

    public BaseDataAdapter(Scheduler computationScheduler, Scheduler mainThreadScheduler) {
        this.computationScheduler = computationScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Nullable
    protected abstract DESTINATION transform(@Nullable SOURCE source);

    protected boolean isValid(DESTINATION destination) {
        return destination != null;
    }

    public void transform(@Nullable List<SOURCE> sourceList, @NonNull DataAdapterListener<DESTINATION> adapterListener) {
        if (sourceList == null) {
            Timber.w("No data to adapt.");
            return;
        }

        Observable.from(sourceList)
                .map(this::transform)
                .filter(this::isValid)
                .toList()
                .subscribeOn(computationScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(adapterListener::onDataAdapted, adapterListener::onDataAdaptError);

    }
}
