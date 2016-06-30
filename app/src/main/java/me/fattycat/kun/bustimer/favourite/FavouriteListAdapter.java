package me.fattycat.kun.bustimer.favourite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.FavouriteViewHolder> {

    private Context context;
    private List<String> favouriteData;

    FavouriteListAdapter(Context context, List<String> data) {
        this.context = context;
        this.favouriteData = data;
    }


    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRoot = LayoutInflater.from(context).inflate(R.layout.item_favourite, parent, false);

        return new FavouriteViewHolder(itemRoot);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        holder.itemBusNameTextView.setText("227路");
        holder.itemBusStationStartTextView.setText("阳光e驾塘桥分校");
        holder.itemBusStationEndTextView.setText("人民西路停车场");
        holder.itemBusBackForthTimeTextView.setText("05:45 - 18:00");
        holder.itemBusTimeIntervalTextView.setText("15~20分钟");
    }

    @Override
    public int getItemCount() {
        return favouriteData == null ? 0 : favouriteData.size();
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemBusStationStartTextView)
        TextView itemBusStationStartTextView;
        @BindView(R.id.itemBusStationEndTextView)
        TextView itemBusStationEndTextView;
        @BindView(R.id.itemBusNameTextView)
        TextView itemBusNameTextView;
        @BindView(R.id.itemBusBackForthTimeTextView)
        TextView itemBusBackForthTimeTextView;
        @BindView(R.id.itemBusTimeIntervalTextView)
        TextView itemBusTimeIntervalTextView;

        FavouriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
