package moj.rain.weather.overview.domain.search;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import moj.rain.app.domain.BaseUseCaseImpl;
import moj.rain.app.injection.qualifiers.ForIoScheduler;
import moj.rain.app.injection.qualifiers.ForMainThreadScheduler;

public class SearchInputUseCaseImpl extends BaseUseCaseImpl implements SearchInputUseCase {

    public static final int DEBOUNCE_TIMEOUT = 500;
    private final Scheduler scheduler;
    private final Scheduler mainThreadScheduler;

    private Callback callback;
    private PublishSubject<String> publishSubject;

    @Inject
    public SearchInputUseCaseImpl(CompositeDisposable compositeDisposable,
                                  @ForIoScheduler Scheduler scheduler,
                                  @ForMainThreadScheduler Scheduler mainThreadScheduler,
                                  PublishSubject<String> publishSubject) {
        super(compositeDisposable);
        this.scheduler = scheduler;
        this.mainThreadScheduler = mainThreadScheduler;
        this.publishSubject = publishSubject;

        setUpPublisher();
    }

    private void setUpPublisher() {
        Disposable disposable = publishSubject
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                .subscribeOn(scheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(s -> callback.onValidSearchInput(s), Throwable::getMessage);

        trackDisposable(disposable);
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute(@NonNull String input) {
        publishSubject.onNext(input);
    }
}
