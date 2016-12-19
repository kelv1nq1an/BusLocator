package me.fattycat.kun.bustimer.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.entity.FavouriteEntity;
import me.fattycat.kun.bustimer.ui.detail.DetailActivity;

/**
 * Author: qk329
 * Date: 2016/12/14
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private List<FavouriteEntity> favouriteEntityList;

    public FavouriteAdapter() {
        favouriteEntityList = new ArrayList<>();
    }

    public void setData(List<FavouriteEntity> data) {
        favouriteEntityList = data;
        notifyDataSetChanged();
    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavouriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_info, parent, false));
    }

    @Override
    public void onBindViewHolder(final FavouriteViewHolder holder, int position) {
        final FavouriteEntity favouriteEntity = favouriteEntityList.get(position);
        holder.itemBusInfoTitleTextView.setText(favouriteEntity.lineName);
        holder.itemBusInfoStartStationTextView.setText(favouriteEntity.startStation);
        holder.itemBusInfoEndStationTextView.setText(favouriteEntity.endStation);
        holder.itemBusInfoStartTimeTextView.setText(String.format("早班%s", favouriteEntity.firstBus));
        holder.itemBusInfoEndTimeTextView.setText(String.format("晚班%s", favouriteEntity.firstBus));
        holder.itemBusInfoGapTimeTextView.setText(String.format("班次%s", favouriteEntity.gapTime));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(DetailActivity.newIntent(holder.itemView.getContext(), favouriteEntity.rpid, favouriteEntity.lineName));
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteEntityList.size();
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_bus_info_title_text_view)
        TextView itemBusInfoTitleTextView;
        @BindView(R.id.item_bus_info_start_station_text_view)
        TextView itemBusInfoStartStationTextView;
        @BindView(R.id.item_bus_info_end_station_text_view)
        TextView itemBusInfoEndStationTextView;
        @BindView(R.id.item_bus_info_start_time_text_view)
        TextView itemBusInfoStartTimeTextView;
        @BindView(R.id.item_bus_info_end_time_text_view)
        TextView itemBusInfoEndTimeTextView;
        @BindView(R.id.item_bus_info_gap_time_text_view)
        TextView itemBusInfoGapTimeTextView;

        FavouriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
