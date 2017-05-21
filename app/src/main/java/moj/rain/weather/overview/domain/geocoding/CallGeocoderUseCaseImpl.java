package moj.rain.weather.overview.domain.geocoding;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moj.rain.app.domain.BaseUseCaseImpl;
import moj.rain.app.injection.qualifiers.ForComputationScheduler;
import moj.rain.app.injection.qualifiers.ForIoScheduler;
import moj.rain.app.injection.qualifiers.ForMainThreadScheduler;
import moj.rain.app.repository.repos.geocoding.GeocodingRepository;

public class CallGeocoderUseCaseImpl extends BaseUseCaseImpl implements CallGeocoderUseCase {

    private final GeocodingRepository geocodingRepository;
    private final Scheduler scheduler;
    private final Scheduler mainThreadScheduler;

    private Callback callback;

    @Inject
    public CallGeocoderUseCaseImpl(CompositeDisposable compositeDisposable,
                                   GeocodingRepository geocodingRepository,
                                   @ForIoScheduler Scheduler scheduler,
                                   @ForMainThreadScheduler Scheduler mainThreadScheduler) {
        super(compositeDisposable);
        this.geocodingRepository = geocodingRepository;
        this.scheduler = scheduler;
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute(String location) {
        Disposable disposable = geocodingRepository.getGeocoding(location)
                .subscribeOn(scheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(callback::onGeocodingRetrieved, callback::onGeocodingNetworkError);

        trackDisposable(disposable);
    }
}
