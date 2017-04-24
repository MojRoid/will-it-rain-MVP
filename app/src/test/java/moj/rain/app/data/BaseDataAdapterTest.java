package moj.rain.app.data;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.jupiter.api.BeforeEach;
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
    public void isValid() throws Exception {
        // Given
        String destination = "123";

        // When
        boolean actual = baseDataAdapter.isValid(destination);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    public void transformSource() throws Exception {
        // Given
        Integer source = 123;

        // When
        String actual = baseDataAdapter.transformSource(source);

        // Then
        assertThat(actual).isEqualTo("123");
    }

    @Test
    public void transform_data() throws Exception {
        // Given
        List<Integer> integerList = new ArrayList<>();
        Integer source1 = 123;
        Integer source2 = 456;
        Integer source3 = 789;
        integerList.add(source1);
        integerList.add(source2);
        integerList.add(source3);

        // When
        baseDataAdapter.transform(integerList, callback);

        // Then
        List<String> stringList = new ArrayList<>();
        String destination1 = "123";
        String destination2 = "456";
        String destination3 = "789";
        stringList.add(destination1);
        stringList.add(destination2);
        stringList.add(destination3);
        then(callback).should(times(1)).onDataAdapted(stringList);
    }

    @Test
    public void transform_null() throws Exception {
        // When
        baseDataAdapter.transform(null, callback);

        // Then
        // TODO: verify
    }

    @Test
    public void cancel() throws Exception {
        // Given
        given(disposable.isDisposed()).willReturn(false);

        // When
        baseDataAdapter.cancel();

        // Then
        then(disposable).should(times(1)).isDisposed();
        then(disposable).should(times(1)).dispose();
        then(disposable).shouldHaveNoMoreInteractions();
    }

}