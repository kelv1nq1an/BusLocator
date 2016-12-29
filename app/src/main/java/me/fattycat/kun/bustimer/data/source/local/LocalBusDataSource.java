package me.fattycat.kun.bustimer.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.data.source.BusContract;

/**
 * Author: qk329
 * Date: 2016/12/15
 */

public class LocalBusDataSource extends AbstractLocalDataSource implements BusContract.local {
    private static final String SP_NAME_LOCAL_FAVOURITES = "local_favourites";
    private static final String SP_KEY_LOCAL_FAVOURITES  = "key_local_favourites";
    private SharedPreferences sharedPreferencesFavourites;

    public LocalBusDataSource(Context context) {
        super(context);
        sharedPreferencesFavourites = context.getSharedPreferences(SP_NAME_LOCAL_FAVOURITES, Context.MODE_PRIVATE);
    }

    @Override
    public List<FavouriteEntity> getAllFavouriteLines() {
        String                data                = sharedPreferencesFavourites.getString(SP_KEY_LOCAL_FAVOURITES, null);
        List<FavouriteEntity> favouriteEntityList = new ArrayList<>();
        Object                dataObject          = new Gson().fromJson(data, new TypeToken<ArrayList<FavouriteEntity>>() {}.getType());
        if (dataObject != null) {
            favouriteEntityList.addAll((Collection<? extends FavouriteEntity>) dataObject);
        }
        return favouriteEntityList;
    }

    @Override
    public void saveFavouriteLine(List<FavouriteEntity> favouriteEntityList) {
        String data = new Gson().toJson(favouriteEntityList);
        sharedPreferencesFavourites.edit().putString(SP_KEY_LOCAL_FAVOURITES, data).apply();
    }
}
