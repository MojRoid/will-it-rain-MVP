package moj.rain.weather.overview.domain.search;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static moj.rain.weather.overview.domain.search.SearchInputUseCaseImpl.DEBOUNCE_TIMEOUT;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class SearchInputUseCaseImplTest {

    @Mock
    private CompositeDisposable compositeDisposable;
    @Mock
    private SearchInputUseCase.Callback callback;

    private SearchInputUseCaseImpl searchInputUseCase;
    private String input;
    private PublishSubject<String> publishSubject = PublishSubject.create();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        searchInputUseCase = new SearchInputUseCaseImpl(
                compositeDisposable,
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                publishSubject);

        searchInputUseCase.setCallback(callback);
    }

    @Test
    public void execute_empty() throws Exception {
        givenInput("");
        whenSearchInputIsExecuted();
        whenWaitDuration(DEBOUNCE_TIMEOUT * 2);
        thenCallbackShouldBeCalled("");
    }

    @Test
    public void execute_no_wait() throws Exception {
        givenInput("");
        whenSearchInputIsExecuted();
        thenCallbackShouldBeCalled();
    }

    @Test
    public void execute_sixCharacters() throws Exception {
        givenInput("london");
        whenSearchInputIsExecuted();
        whenWaitDuration(DEBOUNCE_TIMEOUT * 2);
        thenCallbackShouldBeCalled("london");
    }

    @Test
    public void execute_shouldDebounce() throws Exception {
        givenInput("Brighton");
        whenSearchInputIsExecuted();
        whenWaitDuration(DEBOUNCE_TIMEOUT / 2);
        givenInput("london");
        whenSearchInputIsExecuted();
        whenWaitDuration(DEBOUNCE_TIMEOUT / 2);
        givenInput("Manchester");
        whenSearchInputIsExecuted();
        whenWaitDuration(DEBOUNCE_TIMEOUT * 2);
        thenCallbackShouldBeCalled("Manchester");
    }

    @Test
    public void execute_shouldNotDebounce() throws Exception {
        givenInput("Brighton");
        whenSearchInputIsExecuted();
        whenWaitDuration(DEBOUNCE_TIMEOUT * 2);
        givenInput("london");
        whenSearchInputIsExecuted();
        whenWaitDuration(DEBOUNCE_TIMEOUT * 2);
        thenCallbackShouldBeCalled("Brighton", "london");
    }

    private void givenInput(String input) {
        this.input = input;
    }

    private void whenSearchInputIsExecuted() {
        searchInputUseCase.execute(input);
    }

    private void whenWaitDuration(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    private void thenCallbackShouldBeCalled(String... inputs) {
        for (String input : inputs) {
            then(callback).should(times(1)).onValidSearchInput(input);
        }
        then(callback).shouldHaveNoMoreInteractions();
    }
}
