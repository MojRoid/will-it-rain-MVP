package moj.rain.weather.overview.injection;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import moj.rain.app.injection.qualifiers.ForActivity;
import moj.rain.app.injection.scopes.PerActivity;
import moj.rain.weather.overview.domain.GetWeatherUseCase;
import moj.rain.weather.overview.domain.GetWeatherUseCaseImpl;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.presenter.OverviewPresenterImpl;
import moj.rain.weather.overview.view.OverviewActivity;
import moj.rain.weather.overview.view.OverviewView;
import moj.rain.weather.overview.view.adapter.HourListAdapter;
import moj.rain.weather.overview.view.adapter.HourListAdapterImpl;

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
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    RecyclerView.LayoutManager provideLayoutManager(@ForActivity Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    @PerActivity
    HourListAdapter provideRainAdapter() {
        return new HourListAdapterImpl();
    }
}
