package moj.rain.app.view.error;


import android.support.design.widget.Snackbar;
import android.view.View;

public class ErrorViewManagerImpl implements ErrorViewManager {

    @Override
    public void showError(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }
}
