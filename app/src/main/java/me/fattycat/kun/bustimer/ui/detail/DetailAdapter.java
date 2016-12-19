package me.fattycat.kun.bustimer.ui.detail;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.model.BusGPSModel;
import me.fattycat.kun.bustimer.data.model.StationModel;

/**
 * Author: qk329
 * Date: 2016/12/19
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_START  = 1;
    private static final int TYPE_END    = 2;
    private static final int TYPE_NORMAL = 3;

    private List<DetailWrapper> detailWrappers;

    public DetailAdapter() {
        detailWrappers = new ArrayList<>();
    }

    public void setData(List<DetailWrapper> data) {
        this.detailWrappers.clear();
        this.detailWrappers.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_START;
        } else if (position == detailWrappers.size() - 1) {
            return TYPE_END;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_START) {
            return new StartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_start, parent, false));
        } else if (viewType == TYPE_END) {
            return new EndViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_end, parent, false));
        }
        return new StationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailWrapper               detailWrapper = detailWrappers.get(position);
        StationModel.StationEntity  stationEntity = detailWrapper.getStationEntity();
        List<BusGPSModel.BusEntity> busEntityList = detailWrapper.getBusEntityList();
        String                      stationNameNext;
        if (position + 1 < detailWrappers.size()) {
            stationNameNext = detailWrappers.get(position + 1).getStationEntity().getBusStationName();
        } else {
            stationNameNext = stationEntity.getBusStationName();
        }

        if (holder instanceof StartViewHolder) {
            StartViewHolder startViewHolder = (StartViewHolder) holder;
            startViewHolder.detailStationLeftTextView.setText(String.format("首:%s", stationEntity.getBusStationName()));
            processBus(startViewHolder.detailBusContainer, busEntityList, stationNameNext);
        } else if (holder instanceof EndViewHolder) {
            EndViewHolder endViewHolder = (EndViewHolder) holder;
            if (position % 2 == 1) {
                endViewHolder.detailStationRightTextView.setText(String.format("末:%s", stationEntity.getBusStationName()));
                endViewHolder.detailStationRightTextView.setVisibility(View.VISIBLE);
                endViewHolder.detailStationLeftTextView.setVisibility(View.GONE);
            } else {
                endViewHolder.detailStationLeftTextView.setText(stationEntity.getBusStationName());
                endViewHolder.detailStationLeftTextView.setVisibility(View.VISIBLE);
                endViewHolder.detailStationRightTextView.setVisibility(View.GONE);
            }
            processBus(endViewHolder.detailBusContainer, busEntityList, stationNameNext);
        } else {
            StationViewHolder stationViewHolder = (StationViewHolder) holder;
            if (position % 2 == 1) {
                stationViewHolder.detailStationRightTextView.setText(stationEntity.getBusStationName());
                stationViewHolder.detailStationRightTextView.setVisibility(View.VISIBLE);
                stationViewHolder.detailStationLeftTextView.setVisibility(View.GONE);
            } else {
                stationViewHolder.detailStationLeftTextView.setText(stationEntity.getBusStationName());
                stationViewHolder.detailStationLeftTextView.setVisibility(View.VISIBLE);
                stationViewHolder.detailStationRightTextView.setVisibility(View.GONE);
            }
            processBus(stationViewHolder.detailBusContainer, busEntityList, stationNameNext);
        }
    }

    @Override
    public int getItemCount() {
        return detailWrappers.size();
    }

    private void processBus(LinearLayout busContainer, List<BusGPSModel.BusEntity> busEntityList, String detailWrapperNext) {
        busContainer.removeAllViews();
        for (int i = 0; i < busEntityList.size(); i++) {
            BusGPSModel.BusEntity busEntity = busEntityList.get(i);
            BusViewHolder busViewHolder =
                    new BusViewHolder(LayoutInflater.from(busContainer.getContext()).inflate(R.layout.item_bus, busContainer, false));

            String busDetail = busEntity.getNumberPlate();
            if (TextUtils.equals(busEntity.getOutstate(), "0")) {
                busDetail = busDetail.concat(" 到站\n已到达: ").concat(busEntity.getBusStationName());
                busViewHolder.itemBusLeftTextView.setText(busDetail);
                busViewHolder.itemBusLineLeft.setVisibility(View.VISIBLE);
                busViewHolder.itemBusLeftTextView.setVisibility(View.VISIBLE);
                busViewHolder.itemBusLineRight.setVisibility(View.GONE);
                busViewHolder.itemBusRightTextView.setVisibility(View.GONE);
                busViewHolder.itemBusDotCenter.setBackground(ContextCompat.getDrawable(busContainer.getContext(), R.drawable.ic_dot_arrived));
                busViewHolder.itemBusLineLeft.setBackgroundColor(ContextCompat.getColor(busContainer.getContext(), R.color.colorRed));
                busViewHolder.itemBusLeftTextView.setBackground(ContextCompat.getDrawable(busContainer.getContext(), R.drawable.bg_bus_arrived));
            } else {
                busDetail = busDetail.concat(" 在途中\n下一站: ").concat(detailWrapperNext);
                busViewHolder.itemBusRightTextView.setText(busDetail);
                busViewHolder.itemBusLineLeft.setVisibility(View.GONE);
                busViewHolder.itemBusLineRight.setVisibility(View.GONE);
                busViewHolder.itemBusLineRight.setVisibility(View.VISIBLE);
                busViewHolder.itemBusRightTextView.setVisibility(View.VISIBLE);
                busViewHolder.itemBusDotCenter.setBackground(ContextCompat.getDrawable(busContainer.getContext(), R.drawable.ic_dot_going));
                busViewHolder.itemBusLineRight.setBackgroundColor(ContextCompat.getColor(busContainer.getContext(), R.color.green_200));
                busViewHolder.itemBusRightTextView.setBackground(ContextCompat.getDrawable(busContainer.getContext(), R.drawable.bg_bus_going));
            }
            busContainer.addView(busViewHolder.itemBusRoot);
        }
    }

    class StationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_station_left_text_view)
        TextView     detailStationLeftTextView;
        @BindView(R.id.detail_station_right_text_view)
        TextView     detailStationRightTextView;
        @BindView(R.id.detail_bus_container)
        LinearLayout detailBusContainer;

        public StationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class StartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_station_left_text_view)
        TextView     detailStationLeftTextView;
        @BindView(R.id.detail_bus_container)
        LinearLayout detailBusContainer;

        public StartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class EndViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_station_left_text_view)
        TextView     detailStationLeftTextView;
        @BindView(R.id.detail_station_right_text_view)
        TextView     detailStationRightTextView;
        @BindView(R.id.detail_bus_container)
        LinearLayout detailBusContainer;

        public EndViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BusViewHolder {
        @BindView(R.id.item_bus_root)
        RelativeLayout itemBusRoot;
        @BindView(R.id.item_bus_dot_center)
        View           itemBusDotCenter;
        @BindView(R.id.item_bus_line_left)
        View           itemBusLineLeft;
        @BindView(R.id.item_bus_left_text_view)
        TextView       itemBusLeftTextView;
        @BindView(R.id.item_bus_line_right)
        View           itemBusLineRight;
        @BindView(R.id.item_bus_right_text_view)
        TextView       itemBusRightTextView;

        public BusViewHolder(View busView) {
            ButterKnife.bind(this, busView);
        }
    }
}
