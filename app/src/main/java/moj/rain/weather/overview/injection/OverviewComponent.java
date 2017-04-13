package moj.rain.weather.overview.injection;


import dagger.Subcomponent;
import moj.rain.app.injection.scopes.PerActivity;
import moj.rain.weather.overview.view.OverviewActivity;

@PerActivity
@Subcomponent(modules = {OverviewModule.class})
public interface OverviewComponent {

    void inject(OverviewActivity activity);
}
