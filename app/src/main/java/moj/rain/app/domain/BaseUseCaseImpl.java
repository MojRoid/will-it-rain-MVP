package moj.rain.app.domain;


import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseUseCaseImpl implements BaseUseCase {

    private final CompositeDisposable compositeDisposable;

    protected BaseUseCaseImpl(@NonNull CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void trackDisposable(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void cleanUp() {
        compositeDisposable.clear();
    }
}
