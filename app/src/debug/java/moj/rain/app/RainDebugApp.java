package moj.rain.app;

import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class RainDebugApp extends RainApp {

    @Override
    public void onCreate() {
        super.onCreate();
        setupDebugTools();
    }

    private void setupDebugTools() {
        initStetho();
        initLeakCanary();
        initStrictMode();
        initTimber();
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void initStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyFlashScreen()
                .penaltyDeathOnNetwork()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
    }

    private void initTimber() {
        Timber.plant(loggingTree);
    }
}
