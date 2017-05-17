package moj.rain.weather.overview.domain.geocoding;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moj.rain.app.domain.BaseUseCaseImpl;
import moj.rain.app.injection.qualifiers.ForIoThread;
import moj.rain.app.injection.qualifiers.ForMainThread;
import moj.rain.app.repository.repos.geocoding.GeocodingRepository;

public class GetCoordinatesUseCaseImpl extends BaseUseCaseImpl implements GetCoordinatesUseCase {

    private final GeocodingRepository geocodingRepository;
    private final Scheduler ioScheduler;
    private final Scheduler mainThreadScheduler;

    private Callback callback;

    @Inject
    public GetCoordinatesUseCaseImpl(CompositeDisposable compositeDisposable,
                                     GeocodingRepository geocodingRepository,
                                     @ForIoThread Scheduler ioScheduler,
                                     @ForMainThread Scheduler mainThreadScheduler) {
        super(compositeDisposable);
        this.geocodingRepository = geocodingRepository;
        this.ioScheduler = ioScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute(String location) {
        Disposable disposable = geocodingRepository.getCoordinates(location)
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(callback::onCoordinatesRetrieved, callback::onCoordinatesNetworkError);

        trackDisposable(disposable);
    }
}
