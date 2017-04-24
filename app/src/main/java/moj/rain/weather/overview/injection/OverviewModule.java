package moj.rain.weather.overview.injection;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import moj.rain.app.injection.qualifiers.ForActivity;
import moj.rain.app.injection.scopes.PerActivity;
import moj.rain.app.view.error.ErrorViewManager;
import moj.rain.app.view.error.ErrorViewManagerImpl;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.domain.GetWeatherUseCaseImpl;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.presenter.OverviewPresenterImpl;
import moj.rain.weather.overview.view.OverviewActivity;
import moj.rain.weather.overview.view.OverviewView;

@Module
public class OverviewModule {

    private final OverviewActivity activity;

    public OverviewModule(OverviewActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    @ForActivity
    Context provideContext() {
        return activity;
    }

    @Provides
    @PerActivity
    OverviewView provideView() {
        return activity;
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

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
