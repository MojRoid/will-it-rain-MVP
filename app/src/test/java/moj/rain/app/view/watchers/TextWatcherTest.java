package moj.rain.app.view.watchers;

import android.text.SpannableStringBuilder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static moj.rain.TestConstants.LOCATION_1;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class TextWatcherTest {

    @Mock
    private TextWatcher.Callback callback;
    @Mock
    private SpannableStringBuilder spannableStringBuilder;

    private TextWatcher textWatcher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        textWatcher = new TextWatcher(callback);
    }

    @Test
    public void beforeTextChanged() throws Exception {
        whenBeforeTextIsChanged();
        thenCallbackShouldNotBeCalled();
    }

    @Test
    public void onTextChanged() throws Exception {
        whenOnTextIsChanged();
        thenCallbackShouldNotBeCalled();
    }

    @Test
    public void afterTextChanged_empty() throws Exception {
        whenAfterTextIsChanged("");
        thenCallbackShouldNotBeCalled();
    }

    @Test
    public void afterTextChanged_text() throws Exception {
        whenAfterTextIsChanged(LOCATION_1);
        thenCallbackShouldBeCalled();
    }

    private void whenBeforeTextIsChanged() {
        textWatcher.beforeTextChanged(LOCATION_1, 0, 0, 0);
    }

    private void whenOnTextIsChanged() {
        textWatcher.onTextChanged(LOCATION_1, 0, 0, 0);
    }

    private void whenAfterTextIsChanged(String text) {
        when(spannableStringBuilder.toString()).thenReturn(text);
        textWatcher.afterTextChanged(spannableStringBuilder);
    }

    private void thenCallbackShouldNotBeCalled() {
        then(callback).shouldHaveZeroInteractions();
    }

    private void thenCallbackShouldBeCalled() {
        then(callback).should(times(1)).onTextChanged(LOCATION_1);
        then(callback).shouldHaveNoMoreInteractions();
    }

}