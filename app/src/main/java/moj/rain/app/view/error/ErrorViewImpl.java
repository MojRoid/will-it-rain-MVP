package moj.rain.app.view.error;


import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import moj.rain.R;

public class ErrorViewImpl implements ErrorView {

    @Override
    public void showNetworkErrorView(@NonNull View view) {
        Snackbar.make(view,
                view.getContext().getString(R.string.network_error_message),
                Snackbar.LENGTH_SHORT)
                .show();
    }
}
