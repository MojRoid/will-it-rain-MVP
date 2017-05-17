package moj.rain.app.data;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void setUp() throws Exception {
        baseDataAdapter = getSpy();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isValid() throws Exception {
        givenValidDestinationData("123");
        whenDestinationDataIsCheckedIfValid();
        thenDestinationDataShouldBeDeemedValid();
    }

    @Test
    public void transformSource() throws Exception {
        givenValidSourceData(123);
        whenSourceIsTransformed();
        thenSourceShouldBeTransformedToDestination("123");
    }

    @Test
    public void transform_data() throws Exception {
        givenSourceList(123);
        whenDataIsTransformed();
        thenDataIsAdaptedAndPassedToCallback("123");
    }

    @Test
    public void transform_null() throws Exception {
        givenNullSourceList();
        whenDataIsTransformed();
        thenDataIsNotAdapted();
    }

    @Test
    public void cancel_notDisposed() throws Exception {
        givenDisposable(false);
        whenTheTransformationIsCanceled();
        thenTheDisposableIsDisposed();
    }

    @Test
    public void cancel_disposed() throws Exception {
        givenDisposable(true);
        whenTheTransformationIsCanceled();
        thenTheDisposableIsNotDisposed();
    }

    private void givenValidDestinationData(String destination) {
        this.destination = destination;
    }

    private void givenValidSourceData(int source) {
        this.source = source;
    }

    private void givenSourceList(int source) {
        sourceList = new ArrayList<>();
        sourceList.add(source);
    }

    private void givenNullSourceList() {
        sourceList = null;
    }

    private void givenDisposable(boolean isDisposed) {
        given(disposable.isDisposed()).willReturn(isDisposed);
    }

    private void whenDestinationDataIsCheckedIfValid() {
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

    private void thenDestinationDataShouldBeDeemedValid() {
        assertThat(actualBoolean).isTrue();
    }

    private void thenSourceShouldBeTransformedToDestination(String expected) {
        assertThat(actualString).isEqualTo(expected);
    }

    private void thenDataIsAdaptedAndPassedToCallback(String destination) {
        destinationList = new ArrayList<>();
        destinationList.add(destination);
        then(callback).should(times(1)).onDataAdapted(destinationList);
        then(callback).shouldHaveNoMoreInteractions();
    }

    private void thenDataIsNotAdapted() {
        then(callback).shouldHaveZeroInteractions();
    }

    private void thenTheDisposableIsDisposed() {
        then(disposable).should(times(1)).isDisposed();
        then(disposable).should(times(1)).dispose();
        then(disposable).shouldHaveNoMoreInteractions();
    }

    private void thenTheDisposableIsNotDisposed() {
        then(disposable).should(times(1)).isDisposed();
        then(disposable).shouldHaveNoMoreInteractions();
    }
}