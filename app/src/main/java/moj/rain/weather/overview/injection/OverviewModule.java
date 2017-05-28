package moj.rain.weather.overview.injection;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import moj.rain.app.injection.qualifiers.ForActivity;
import moj.rain.app.injection.scopes.PerActivity;
import moj.rain.app.view.adapter.DiffCallback;
import moj.rain.app.view.error.ErrorView;
import moj.rain.app.view.error.ErrorViewImpl;
import moj.rain.app.view.watchers.TextWatcher;
import moj.rain.weather.overview.domain.geocoding.CallGeocoderUseCase;
import moj.rain.weather.overview.domain.geocoding.CallGeocoderUseCaseImpl;
import moj.rain.weather.overview.domain.search.SearchInputUseCase;
import moj.rain.weather.overview.domain.search.SearchInputUseCaseImpl;
import moj.rain.weather.overview.domain.weather.GetWeatherUseCase;
import moj.rain.weather.overview.domain.weather.GetWeatherUseCaseImpl;
import moj.rain.weather.overview.model.WeatherHour;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.presenter.OverviewPresenterImpl;
import moj.rain.weather.overview.view.OverviewActivity;
import moj.rain.weather.overview.view.OverviewView;
import moj.rain.weather.overview.view.adapter.WeatherAdapter;
import moj.rain.weather.overview.view.adapter.WeatherAdapterImpl;

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
    CallGeocoderUseCase provideGetCoordinatesUseCase(CallGeocoderUseCaseImpl getCoordinatesUseCase) {
        return getCoordinatesUseCase;
    }

    @Provides
    @PerActivity
    SearchInputUseCase provideSearchInputUseCase(SearchInputUseCaseImpl searchInputUseCase) {
        return searchInputUseCase;
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
    DiffCallback<WeatherHour> provideDiffCallback() {
        return new DiffCallback<>();
    }

    @Provides
    @PerActivity
    ErrorView provideErrorView() {
        return new ErrorViewImpl();
    }

    @Provides
    @PerActivity
    WeatherAdapter provideRainAdapter(WeatherAdapterImpl rainHourAdapter) {
        return rainHourAdapter;
    }

    @Provides
    @PerActivity
    TextWatcher provideTextWatcher() {
        return new TextWatcher(activity);
    }

    @Provides
    @PerActivity
    PublishSubject<String> providePublisherSubject() {
        return PublishSubject.create();
    }
}
