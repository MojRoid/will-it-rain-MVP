package moj.rain.app.domain;


import android.support.annotation.NonNull;

import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        baseUseCase = spy(new BaseUseCaseImpl(compositeDisposable) {
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

    @Test
    public void trackDisposable() throws Exception {
        // When
        baseUseCase.trackDisposable(disposable1);
        baseUseCase.trackDisposable(disposable2);

        // Then
        then(compositeDisposable).should(times(1)).add(disposable1);
        then(compositeDisposable).should(times(1)).add(disposable2);
        then(compositeDisposable).shouldHaveNoMoreInteractions();
    }

    @Test
    public void cleanUp() throws Exception {
        // When
        baseUseCase.cleanUp();

        // Then
        then(compositeDisposable).should(times(1)).clear();
        then(compositeDisposable).shouldHaveNoMoreInteractions();

    }
}