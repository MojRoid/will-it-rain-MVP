package moj.rain.weather.overview.domain.weather;


import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moj.rain.app.domain.BaseUseCaseImpl;
import moj.rain.app.injection.qualifiers.ForIoThread;
import moj.rain.app.injection.qualifiers.ForMainThread;
import moj.rain.app.repository.repos.weather.WeatherRepository;

public class GetWeatherUseCaseImpl extends BaseUseCaseImpl implements GetWeatherUseCase {

    private final WeatherRepository weatherRepository;
    private final Scheduler ioScheduler;
    private final Scheduler mainThreadScheduler;

    private Callback callback;

    @Inject
    public GetWeatherUseCaseImpl(CompositeDisposable compositeDisposable,
                                 WeatherRepository weatherRepository,
                                 @ForIoThread Scheduler ioScheduler,
                                 @ForMainThread Scheduler mainThreadScheduler) {
        super(compositeDisposable);
        this.weatherRepository = weatherRepository;
        this.ioScheduler = ioScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute(double latitude, double longitude) {
        Disposable disposable = weatherRepository.getWeather(latitude, longitude)
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(callback::onWeatherRetrieved, callback::onWeatherNetworkError);

        trackDisposable(disposable);
    }
}
