package moj.rain.app.data;


import java.util.List;

public interface DataAdapterListener<DESTINATION> {

    void onDataAdapted(List<DESTINATION> data);

    void onDataAdaptError(Throwable throwable);
}
