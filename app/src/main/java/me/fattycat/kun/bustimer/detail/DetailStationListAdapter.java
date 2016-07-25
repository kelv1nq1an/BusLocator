package me.fattycat.kun.bustimer.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.model.BusGPSEntity;
import me.fattycat.kun.bustimer.model.StationWrapper;

/**
 * Author: Kelvinkun
 * Date: 16/7/11
 */

class DetailStationListAdapter extends RecyclerView.Adapter<DetailStationListAdapter.DetailStationViewHolder> {

    private Context context;
    private List<StationWrapper> stationListWrapper;

    DetailStationListAdapter(Context context) {
        this.context = context;
        this.stationListWrapper = new ArrayList<>();
    }

    public void setData(List<StationWrapper> data) {
        if (data != null) {
            this.stationListWrapper = data;
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        this.stationListWrapper.clear();
        notifyDataSetChanged();
    }

    @Override
    public DetailStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_station, parent, false);
        return new DetailStationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailStationViewHolder holder, int position) {
        if (position == 0) {
            holder.itemStationTop.setVisibility(View.INVISIBLE);
        } else {
            holder.itemStationTop.setVisibility(View.VISIBLE);
        }
        if (position + 1 == stationListWrapper.size()) {
            holder.itemStationBottom.setVisibility(View.INVISIBLE);
        } else {
            holder.itemStationBottom.setVisibility(View.VISIBLE);
        }

        holder.itemStationName.setText(stationListWrapper.get(position).getStation().getBusStationName());

        if (stationListWrapper.get(position).getBusList() != null && stationListWrapper.get(position).getBusList().size() != 0) {
            for (BusGPSEntity.ResultEntity.ListsEntity bus : stationListWrapper.get(position).getBusList()) {
                View busInStationView = LayoutInflater.from(context).inflate(R.layout.layout_bus_in_station, holder.itemStationBusInStationContainer, false);
                LinearLayout busInStationContainer = (LinearLayout) busInStationView.findViewById(R.id.bus_in_station_root);
                TextView busNumber = (TextView) busInStationView.findViewById(R.id.bus_in_station_name);
                TextView busStatus = (TextView) busInStationView.findViewById(R.id.bus_in_station_status);
                TextView busTime = (TextView) busInStationView.findViewById(R.id.bus_in_station_time);

                int dotBgRes;
                int containerBg;
                String busStatusString;
                if (TextUtils.equals(bus.getOutstate(), "0")) {
                    dotBgRes = R.drawable.bg_dot_arrive;
                    containerBg = R.drawable.bg_bus_in_station_arrive;
                    busStatusString = "到达 " + bus.getBusStationName();
                } else {
                    dotBgRes = R.drawable.bg_dot;
                    containerBg = R.drawable.bg_bus_in_station_leave;
                    busStatusString = "前往 " + stationListWrapper.get(position + 1).getStation().getBusStationName();
                }
                holder.itemStationCenterDot.setBackgroundResource(dotBgRes);
                busInStationContainer.setBackgroundResource(containerBg);
                busNumber.setText(bus.getNumberPlate());
                busStatus.setText(busStatusString);
                busTime.setText(calculateTime(bus.getGPSTime()));
                holder.itemStationBusInStationContainer.removeAllViews();
                holder.itemStationBusInStationContainer.addView(busInStationView);
            }
        } else {
            holder.itemStationCenterDot.setBackgroundResource(R.drawable.bg_dot);
            holder.itemStationBusInStationContainer.removeAllViews();
        }
    }

    private String calculateTime(String originalTime) {
        String gapTime = originalTime.substring(0, originalTime.lastIndexOf(":") + 3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date gpsDate = new Date();
        Date nowDate = Calendar.getInstance().getTime();

        try {
            gpsDate = sdf.parse(gapTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (gpsDate != null && nowDate != null) {
            long timeLong = nowDate.getTime() - gpsDate.getTime();
            if (timeLong < 60 * 1000)
                gapTime = timeLong / 1000 + "秒前";
            else if (timeLong < 60 * 60 * 1000) {
                timeLong = timeLong / 1000 / 60;
                gapTime = timeLong + "分钟前";
            } else if (timeLong < 60 * 60 * 24 * 1000) {
                timeLong = timeLong / 60 / 60 / 1000;
                gapTime = timeLong + "小时前";
            } else {
                gapTime = "超时";
            }
        }
        return gapTime;
    }

    @Override
    public int getItemCount() {
        return stationListWrapper.size();
    }

    class DetailStationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_station_center)
        View itemStationCenterDot;
        @BindView(R.id.item_station_top)
        View itemStationTop;
        @BindView(R.id.item_station_bottom)
        View itemStationBottom;
        @BindView(R.id.item_station_name)
        TextView itemStationName;
        @BindView(R.id.item_station_bus_in_station_container)
        LinearLayout itemStationBusInStationContainer;

        DetailStationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
