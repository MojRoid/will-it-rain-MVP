package moj.rain.weather.overview.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import moj.rain.BuildConfig;
import moj.rain.R;
import moj.rain.app.RainApp;
import moj.rain.app.network.model.geocoding.Location;
import moj.rain.app.view.BaseActivity;
import moj.rain.app.view.error.ErrorView;
import moj.rain.app.view.watchers.TextWatcherAfter;
import moj.rain.weather.overview.injection.OverviewModule;
import moj.rain.weather.overview.model.WeatherData;
import moj.rain.weather.overview.presenter.OverviewPresenter;
import moj.rain.weather.overview.view.adapter.WeatherAdapter;

public class OverviewActivity extends BaseActivity implements OverviewView, TextWatcherAfter.Callback {

    @BindView(R.id.geocoding_search_input_et)
    EditText geocodingSearchInputEt;
    @BindView(R.id.formatted_address_results_txt)
    TextView formattedAddressResultTxt;
    @BindView(R.id.hour_list_rv)
    RecyclerView recyclerView;
    @BindView(R.id.google_maps_image)
    ImageView googleMapsImage;

    @Inject
    OverviewPresenter presenter;
    @Inject
    ErrorView errorView;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    WeatherAdapter weatherAdapter;
    @Inject
    TextWatcherAfter textWatcherAfter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_overview;
    }

    @Override
    public void injectDependencies() {
        RainApp.get()
                .getComponent()
                .plus(new OverviewModule(this))
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpRecyclerView();
        setUpViews();
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(weatherAdapter);
    }

    private void setUpViews() {
        geocodingSearchInputEt.addTextChangedListener(textWatcherAfter);
        ViewGroup.LayoutParams layoutParams = googleMapsImage.getLayoutParams();
        layoutParams.height = Resources.getSystem().getDisplayMetrics().widthPixels / 4;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getWeather();
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroyed();
        super.onDestroy();
    }

    @Override
    public void showNetworkError() {
        View view = findViewById(android.R.id.content);
        errorView.showNetworkErrorView(view);
    }

    @Override
    public void showNoResultsError() {
        View view = findViewById(android.R.id.content);
        errorView.showNoResultsErrorView(view);
    }

    @Override
    public void showWeather(@Nullable WeatherData weatherData) {
        weatherAdapter.setWeatherData(weatherData);
    }

    @Override
    public void showFormattedAddress(@NonNull String formattedAddress) {
        formattedAddressResultTxt.setText(formattedAddress);
    }

    @Override
    public void showMap(@NonNull Location location) {
        Glide.with(this).load(String.format(Locale.getDefault(), BuildConfig.GOOGLE_STATIC_MAPS_FULL_URL,
                        location.getLat(), location.getLng(), BuildConfig.GOOGLE_API_KEY))
                .centerCrop()
                .into(googleMapsImage);
    }

    @Override
    public void onTextChanged(@NonNull String input) {
        presenter.onSearchInput(input);
    }
}
