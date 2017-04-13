package moj.rain.app.injection;


import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moj.rain.app.RainApp;
import moj.rain.app.injection.qualifiers.ForApplication;
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
}
