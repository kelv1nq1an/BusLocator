package me.fattycat.kun.bustimer.network;

import me.fattycat.kun.bustimer.data.model.ModelWrapper;
import rx.functions.Func1;

/**
 * Author: qk329
 * Date: 2016/12/16
 */

public class HttpResultFunc<T> implements Func1<ModelWrapper<T>, T> {

    @Override
    public T call(ModelWrapper<T> tModelWrapper) {
        return tModelWrapper.getResult();
    }
}
