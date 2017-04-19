package moj.rain.app.domain;


import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;

public interface BaseUseCase {

    void trackDisposable(@NonNull Disposable disposable);

    void cleanUp();
}
