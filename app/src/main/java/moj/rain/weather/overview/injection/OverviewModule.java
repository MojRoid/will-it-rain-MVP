package moj.rain.weather.overview.injection;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import moj.rain.app.injection.qualifiers.ForActivity;
import moj.rain.app.injection.scopes.PerActivity;
import moj.rain.app.view.error.ErrorViewManager;
import moj.rain.app.view.error.ErrorViewManagerImpl;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.domain.GetWeatherUseCaseImpl;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.presenter.OverviewPresenterImpl;
import moj.rain.weather.overview.view.OverviewView;

@Module
public class OverviewModule {

    private Context context;
    private OverviewView view;

    public OverviewModule(Context context, OverviewView view) {
        this.context = context;
        this.view = view;
    }

    @Provides
    @PerActivity
    @ForActivity
    Context provideContext() {
        return context;
    }

    @Provides
    @PerActivity
    OverviewView provideView() {
        return view;
    }

    @Provides
    @PerActivity
    OverviewPresenter providePresenter(OverviewPresenterImpl overviewPresenter) {
        return overviewPresenter;
    }

    @Provides
    @PerActivity
    GetWeatherUseCase provideGetWeatherUseCase(GetWeatherUseCaseImpl getWeatherUseCase) {
        return getWeatherUseCase;
    }

    @Provides
    @PerActivity
    ErrorViewManager provideErrorViewManager() {
        return new ErrorViewManagerImpl();
    }
}
