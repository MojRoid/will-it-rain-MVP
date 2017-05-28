package moj.rain.weather.overview.domain.search;

import io.reactivex.annotations.NonNull;
import moj.rain.app.domain.BaseUseCase;

public interface SearchInputUseCase extends BaseUseCase {

    interface Callback {
        void onValidSearchInput(@NonNull String input);
    }

    void setCallback(Callback callback);

    void execute(@NonNull String input);
}
