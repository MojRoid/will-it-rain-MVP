package moj.rain.app.injection;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moj.rain.app.RainApp;
import moj.rain.app.injection.qualifiers.ForComputationScheduler;
import moj.rain.app.injection.qualifiers.ForIoScheduler;
import moj.rain.app.injection.qualifiers.ForMainThreadScheduler;
import moj.rain.app.injection.qualifiers.ForSingleScheduler;
import timber.log.Timber;

@Module
public class RainAppModule {

    private RainApp app;

    public RainAppModule(RainApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Timber.Tree provideLogger() {
        return new Timber.DebugTree();
    }

    @Provides
    @Singleton
    @ForIoScheduler
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @ForComputationScheduler
    Scheduler provideComputationScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @ForSingleScheduler
    Scheduler provideSingleScheduler() {
        return Schedulers.single();
    }

    @Provides
    @Singleton
    @ForMainThreadScheduler
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
