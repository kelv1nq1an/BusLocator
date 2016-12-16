package me.fattycat.kun.bustimer.data.source.remote;

import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.LineInfoModel;
import me.fattycat.kun.bustimer.data.model.SearchModel;
import me.fattycat.kun.bustimer.data.model.StationModel;
import me.fattycat.kun.bustimer.data.source.BusContract;
import me.fattycat.kun.bustimer.network.HttpResultFunc;
import rx.Observable;

/**
 * Author: qk329
 * Date: 2016/12/15
 */

public class RemoteBusDataSource extends AbstractRemoteDataSource implements BusContract.remote {

    public RemoteBusDataSource(ApiService api) {
        super(api);
    }

    @Override
    public Observable<SearchModel> searchByName(String name) {
        return mApi.getLines(name).map(new HttpResultFunc<SearchModel>());
    }

    @Override
    public Observable<LineInfoModel> getLineInfo(String runPathID) {
        return mApi.getLineInfo(runPathID).map(new HttpResultFunc<LineInfoModel>());
    }

    @Override
    public Observable<BusGPSModel> getBusGPS(String runPathID, String flag) {
        return mApi.getLineGPS(runPathID, flag).map(new HttpResultFunc<BusGPSModel>());
    }

    @Override
    public Observable<StationModel> getStations(String runPathID) {
        return mApi.getStations(runPathID).map(new HttpResultFunc<StationModel>());
    }
}
