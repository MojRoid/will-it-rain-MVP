package moj.rain.app.data;


public interface DataAdapterCallback<DESTINATION> {

    void onDataAdapted(DESTINATION data);

    void onDataAdaptError(Throwable throwable);
}
