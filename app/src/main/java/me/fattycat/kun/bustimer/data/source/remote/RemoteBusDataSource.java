package me.fattycat.kun.bustimer.data.source.remote;

import me.fattycat.kun.bustimer.data.source.BusContract;

/**
 * Author: qk329
 * Date: 2016/12/15
 */

public class RemoteBusDataSource extends AbstractRemoteDataSource implements BusContract.remote {

    public RemoteBusDataSource(ApiService api) {
        super(api);
    }
}
