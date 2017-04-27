package moj.rain.app.data;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public class BaseDataAdapterTest {

    @Mock
    private BaseDataAdapter.Callback<String> callback;
    @Mock
    private Disposable disposable;

    @InjectMocks
    private BaseDataAdapter<Integer, String> baseDataAdapter;

    private String destination;
    private boolean actualBoolean;
    private Integer source;
    private String actualString;
    private List<Integer> sourceList;
    private List<String> destinationList;

    private BaseDataAdapter<Integer, String> getSpy() {
        return spy(new BaseDataAdapter<Integer, String>(
                Schedulers.trampoline(),
                Schedulers.trampoline()) {
            @Override
            protected boolean isValid(String destination) {
                return !StringUtils.isBlank(destination);
            }

            @Nullable
            @Override
            protected String transformSource(@NonNull Integer source) {
                return String.valueOf(source);
            }
        });
    }

    @BeforeEach
    public void setUp() throws Exception {
        baseDataAdapter = getSpy();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("GIVEN a valid destination value WHEN destination validity is checked THEN destination should be deemed valid")
    public void isValid() throws Exception {
        givenAValidDestinationValue();
        whenDestinationValidityIsChecked();
        thenDestinationShouldBeDeemedValid();
    }

    @Test
    @DisplayName("GIVEN a valid source value WHEN source is transformed THEN source should be transformed")
    public void transformSource() throws Exception {
        givenAValidSourceValue();
        whenSourceIsTransformed();
        thenSourceShouldBeTransformed();
    }

    @Test
    @DisplayName("GIVEN source data WHEN data is transformed THEN data is adapted AND passed to callback")
    public void transform_data() throws Exception {
        givenSourceData(123);
        whenDataIsTransformed();
        thenDataIsAdaptedAndPassedToCallback("123");
    }

    @Test
    @DisplayName("GIVEN disposable is not disposed WHEN the transformation is canceled THEN the disposable is disposed")
    public void cancel() throws Exception {
        givenDisposableIsNotDisposed();
        whenTheTransformationIsCanceled();
        thenTheDisposableIsDisposed();
    }

    private void givenAValidDestinationValue() {
        destination = "123";
    }

    private void givenAValidSourceValue() {
        source = 123;
    }

    private void givenSourceData(int source) {
        sourceList = new ArrayList<>();
        sourceList.add(source);
    }

    private void givenDisposableIsNotDisposed() {
        given(disposable.isDisposed()).willReturn(false);
    }

    private void whenDestinationValidityIsChecked() {
        actualBoolean = baseDataAdapter.isValid(destination);
    }

    private void whenTheTransformationIsCanceled() {
        baseDataAdapter.cancel();
    }

    private void whenSourceIsTransformed() {
        actualString = baseDataAdapter.transformSource(source);
    }

    private void whenDataIsTransformed() {
        baseDataAdapter.transform(sourceList, callback);
    }

    private void thenDestinationShouldBeDeemedValid() {
        assertThat(actualBoolean).isTrue();
    }

    private void thenSourceShouldBeTransformed() {
        assertThat(actualString).isEqualTo("123");
    }

    private void thenDataIsAdaptedAndPassedToCallback(String destination) {
        destinationList = new ArrayList<>();
        destinationList.add(destination);
        then(callback).should(times(1)).onDataAdapted(destinationList);
        then(callback).shouldHaveNoMoreInteractions();
    }

    private void thenTheDisposableIsDisposed() {
        then(disposable).should(times(1)).isDisposed();
        then(disposable).should(times(1)).dispose();
        then(disposable).shouldHaveNoMoreInteractions();
    }
}