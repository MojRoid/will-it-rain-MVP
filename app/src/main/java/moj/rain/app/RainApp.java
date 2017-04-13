package moj.rain.app;


import android.app.Application;

import javax.inject.Inject;

import moj.rain.app.injection.DaggerRainAppComponent;
import moj.rain.app.injection.RainAppComponent;
import moj.rain.app.injection.RainAppModule;
import timber.log.Timber;

public class RainApp extends Application {

    @Inject
    Timber.Tree loggingTree;

    private static RainApp instance;
    private final RainAppComponent component;

    public RainApp() {
        instance = this;
        component = DaggerRainAppComponent
                .builder()
                .rainAppModule(new RainAppModule(instance))
                .build();
        component.inject(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupLogger();
    }

    private void setupLogger() {
        Timber.plant(loggingTree);
    }

    public static RainApp get() {
        return instance;
    }

    public RainAppComponent getComponent() {
        return component;
    }
}
