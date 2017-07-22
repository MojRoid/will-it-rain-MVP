package moj.rain.app.view.error;


import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import moj.rain.R;

public class ErrorViewImpl implements ErrorView {

    @Override
    public void showNetworkErrorView(@NonNull View view) {
        String error = view.getContext().getString(R.string.network_error_message);
        showSnackbar(view, error);
    }

    @Override
    public void showNoResultsErrorView(@NonNull View view) {
        String error = view.getContext().getString(R.string.no_results_error_message);
        showSnackbar(view, error);
    }

    private void showSnackbar(@NonNull View view, String error) {
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
    }
}
