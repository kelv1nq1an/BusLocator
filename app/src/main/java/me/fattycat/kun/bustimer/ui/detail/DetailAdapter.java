package me.fattycat.kun.bustimer.ui.detail;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.event.StationAlarmEvent;
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
        Context                     context       = holder.itemView.getContext();
        String                      stationNameNext;
        if (position + 1 < detailWrappers.size()) {
            stationNameNext = detailWrappers.get(position + 1).getStationEntity().getBusStationName();
        } else {
            stationNameNext = stationEntity.getBusStationName();
        }

        if (holder instanceof StartViewHolder) {
            StartViewHolder startViewHolder = (StartViewHolder) holder;
            startViewHolder.detailStationLeftTextView.setText(stationEntity.getBusStationName());
            processBus(startViewHolder.detailBusContainer, busEntityList, stationNameNext);
        } else if (holder instanceof EndViewHolder) {
            EndViewHolder endViewHolder = (EndViewHolder) holder;
            if (position % 2 == 1) {
                setStationTitle(endViewHolder.detailStationLeftTextView, endViewHolder.detailStationRightTextView, stationEntity.getBusStationName(), false, stationEntity.isHasAlarm());
            } else {
                setStationTitle(endViewHolder.detailStationLeftTextView, endViewHolder.detailStationRightTextView, stationEntity.getBusStationName(), true, stationEntity.isHasAlarm());
            }
            processBus(endViewHolder.detailBusContainer, busEntityList, stationNameNext);
        } else {
            StationViewHolder stationViewHolder = (StationViewHolder) holder;

            TextView textViewShow;
            if (position % 2 == 1) {
                textViewShow = stationViewHolder.detailStationRightTextView;
                setStationTitle(stationViewHolder.detailStationLeftTextView, stationViewHolder.detailStationRightTextView, stationEntity.getBusStationName(), false, stationEntity.isHasAlarm());
            } else {
                textViewShow = stationViewHolder.detailStationLeftTextView;
                setStationTitle(stationViewHolder.detailStationLeftTextView, stationViewHolder.detailStationRightTextView, stationEntity.getBusStationName(), true, stationEntity.isHasAlarm());
            }

            if (stationEntity.isHasAlarm()) {
                updateStation(textViewShow, context, true);
            } else {
                updateStation(textViewShow, context, false);
            }

            processBus(stationViewHolder.detailBusContainer, busEntityList, stationNameNext);
        }
    }

    @Override
    public int getItemCount() {
        return detailWrappers.size();
    }

    private void setStationTitle(TextView stationTitleLeftView, TextView stationTitleRightView, String stationName, boolean isLeft, boolean hasAalarm) {
        if (isLeft) {
            stationTitleLeftView.setText(stationName);
            stationTitleLeftView.setVisibility(View.VISIBLE);
            stationTitleRightView.setVisibility(View.GONE);
            addOnStationClickListener(stationName, stationTitleLeftView, hasAalarm);
        } else {
            stationTitleRightView.setText(stationName);
            stationTitleRightView.setVisibility(View.VISIBLE);
            stationTitleLeftView.setVisibility(View.GONE);
            addOnStationClickListener(stationName, stationTitleRightView, hasAalarm);
        }
    }

    private void addOnStationClickListener(final String stationName, TextView stationView, final boolean hasAlarm) {
        stationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StationAlarmEvent(stationName, hasAlarm));
            }
        });
    }

    private void updateStation(TextView stationView, Context context, boolean hasAlarm) {
        int colorAlarm     = R.color.white;
        int drawableAlarm  = R.drawable.bg_station_alarm;
        int colorNormal    = R.color.textColor;
        int drawableNormal = R.drawable.bg_station;
        int color          = colorNormal;
        int drawable       = drawableNormal;
        if (hasAlarm) {
            color = colorAlarm;
            drawable = drawableAlarm;
        }
        stationView.setTextColor(ContextCompat.getColor(context, color));
        stationView.setBackground(ContextCompat.getDrawable(context, drawable));
    }

    private void processBus(LinearLayout busContainer, List<BusGPSModel.BusEntity> busEntityList, String detailWrapperNext) {
        busContainer.removeAllViews();
        for (int i = 0; i < busEntityList.size(); i++) {
            BusGPSModel.BusEntity busEntity = busEntityList.get(i);
            BusViewHolder busViewHolder =
                    new BusViewHolder(LayoutInflater.from(busContainer.getContext()).inflate(R.layout.item_bus, busContainer, false));

            String busDetail = busEntity.getNumberPlate();
            if (TextUtils.equals(busEntity.getOutstate(), "0")) {
                busDetail = busDetail.concat(" 已到达\n").concat(busEntity.getBusStationName());
                busViewHolder.itemBusLeftTextView.setText(busDetail);
                busViewHolder.itemBusLeftTextView.setVisibility(View.VISIBLE);
                busViewHolder.itemBusLineLeft.setVisibility(View.VISIBLE);
                busViewHolder.itemBusDotCenter.setBackground(ContextCompat.getDrawable(busContainer.getContext(), R.drawable.ic_dot_arrived));
            } else {
                busDetail = busDetail.concat(" 正前往\n").concat(detailWrapperNext);
                busViewHolder.itemBusRightTextView.setText(busDetail);
                busViewHolder.itemBusRightTextView.setVisibility(View.VISIBLE);
                busViewHolder.itemBusLineRight.setVisibility(View.VISIBLE);
                busViewHolder.itemBusDotCenter.setBackground(ContextCompat.getDrawable(busContainer.getContext(), R.drawable.ic_dot_going));
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
