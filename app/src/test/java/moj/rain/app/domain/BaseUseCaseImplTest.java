package moj.rain.app.domain;


import android.support.annotation.NonNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;


public class BaseUseCaseImplTest {

    @Mock
    private Disposable disposable1;
    @Mock
    private Disposable disposable2;
    @Mock
    private CompositeDisposable compositeDisposable;

    private BaseUseCaseImpl baseUseCase;

    private BaseUseCaseImpl getSpy() {
        return spy(new BaseUseCaseImpl(compositeDisposable) {
            @Override
            public void trackDisposable(@NonNull Disposable disposable) {
                super.trackDisposable(disposable);
            }

            @Override
            public void cleanUp() {
                super.cleanUp();
            }
        });
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        baseUseCase = getSpy();
    }

    @Test
    @DisplayName("WHEN disposables are tracked THEN disposables should be added to the composite disposable")
    public void trackDisposable() throws Exception {
        whenDisposablesAreTracked();
        thenDisposablesShouldBeAddedToTheCompositeDisposable();
    }

    @Test
    @DisplayName("WHEN use cases are cleaned up THEN the composite disposable should be cleared")
    public void cleanUp() throws Exception {
        whenUseCasesAreCleanedUp();
        thenTheCompositeDisposableShouldBeCleared();
    }

    private void whenUseCasesAreCleanedUp() {
        baseUseCase.cleanUp();
    }

    private void whenDisposablesAreTracked() {
        baseUseCase.trackDisposable(disposable1);
        baseUseCase.trackDisposable(disposable2);
    }

    private void thenDisposablesShouldBeAddedToTheCompositeDisposable() {
        then(compositeDisposable).should(times(1)).add(disposable1);
        then(compositeDisposable).should(times(1)).add(disposable2);
        then(compositeDisposable).shouldHaveNoMoreInteractions();
    }

    private void thenTheCompositeDisposableShouldBeCleared() {
        then(compositeDisposable).should(times(1)).clear();
        then(compositeDisposable).shouldHaveNoMoreInteractions();
    }
}