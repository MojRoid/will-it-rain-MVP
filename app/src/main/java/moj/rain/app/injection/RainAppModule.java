package moj.rain.app.injection;


import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moj.rain.app.RainApp;
import moj.rain.app.injection.qualifiers.ForApplication;
import moj.rain.app.injection.qualifiers.ForComputationThread;
import moj.rain.app.injection.qualifiers.ForIoThread;
import moj.rain.app.injection.qualifiers.ForMainThread;
import timber.log.Timber;

@Module
public class RainAppModule {

    private RainApp app;

    public RainAppModule(RainApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideAppContext() {
        return app;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return app.getResources();
    }

    @Provides
    @Singleton
    Timber.Tree provideLogger() {
        return new Timber.DebugTree();
    }

    @Provides
    @Singleton
    @ForIoThread
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @ForComputationThread
    Scheduler provideComputationScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @ForMainThread
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
