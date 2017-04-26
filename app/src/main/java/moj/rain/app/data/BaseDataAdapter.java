package moj.rain.app.data;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public abstract class BaseDataAdapter<SOURCE, DESTINATION> {

    public interface Callback<DESTINATION> {

        void onDataAdapted(@NonNull List<DESTINATION> data);

        void onDataAdaptError(Throwable throwable);
    }

    private final Scheduler computationScheduler;
    private final Scheduler mainThreadScheduler;
    private Disposable disposable;

    public BaseDataAdapter(Scheduler computationScheduler, Scheduler mainThreadScheduler) {
        this.computationScheduler = computationScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    protected abstract boolean isValid(DESTINATION destination);

    @Nullable
    protected abstract DESTINATION transformSource(@NonNull SOURCE source);

    public void transform(@Nullable List<SOURCE> sourceList, @NonNull Callback<DESTINATION> callback) {
        if (sourceList == null) {
            Timber.w("No data to adapt.");
            return;
        }

        disposable = Observable.fromIterable(sourceList)
                .map(this::transformSource)
                .filter(this::isValid)
                .toList()
                .subscribeOn(computationScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(callback::onDataAdapted, callback::onDataAdaptError);
    }

    public void cancel() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
