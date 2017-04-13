package moj.rain.app.presenter;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import moj.rain.app.domain.BaseUseCase;

public class BasePresenter {
    private List<BaseUseCase> useCaseList;

    protected BasePresenter() {
        useCaseList = new ArrayList<>();
    }

    protected void trackUseCase(@NonNull BaseUseCase useCase) {
        useCaseList.add(useCase);
    }

    protected void cleanup() {
        for (BaseUseCase useCase : useCaseList) {
            useCase.cleanUp();
        }
        useCaseList.clear();
    }
}
