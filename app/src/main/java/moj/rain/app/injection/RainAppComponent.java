package moj.rain.app.injection;


import javax.inject.Singleton;

import dagger.Component;
import moj.rain.app.RainApp;
import moj.rain.app.repository.network.injection.NetworkModule;
import moj.rain.weather.overview.injection.OverviewComponent;
import moj.rain.weather.overview.injection.OverviewModule;

@Singleton
@Component(modules = {RainAppModule.class, NetworkModule.class})
public interface RainAppComponent {

    void inject(RainApp app);

    OverviewComponent plus(OverviewModule module);
}
