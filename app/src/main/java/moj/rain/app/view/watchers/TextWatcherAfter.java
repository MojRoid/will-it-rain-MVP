package moj.rain.app.view.watchers;

import android.text.Editable;

import io.reactivex.annotations.NonNull;

public class TextWatcherAfter implements android.text.TextWatcher {

    public interface Callback {
        void onTextChanged(@NonNull String text);
    }

    private Callback callback;

    public TextWatcherAfter(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().trim().length() == 0) {
            return;
        }
        callback.onTextChanged(s.toString());
    }
}
