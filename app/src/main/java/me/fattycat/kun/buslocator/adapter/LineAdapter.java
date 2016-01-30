package me.fattycat.kun.buslocator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.fattycat.kun.buslocator.R;
import me.fattycat.kun.buslocator.dao.StationAndBus;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Descirption:
 */
public class LineAdapter extends RecyclerView.Adapter<LineAdapter.LineBusViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String mRunPathName;
    private List<StationAndBus> mLineList;

    public LineAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mLineList = new ArrayList<>();
        mRunPathName = "";
    }

    public void setRunPathName(String runPathName) {
        mRunPathName = runPathName;
    }

    public void refreshData(List<StationAndBus> data) {
        clearData();

        if (data != null) {
            mLineList.addAll(data);
            notifyDataSetChanged();
        }
    }

    private void clearData() {
        mLineList.clear();
        notifyDataSetChanged();
    }

    @Override
    public LineBusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_line, parent, false);
        return new LineBusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LineBusViewHolder holder, int position) {
        StationAndBus data = mLineList.get(position);
        if (data.type == StationAndBus.DAO_TYPE_STATION) {
            holder.stationContainer.setVisibility(View.VISIBLE);
            holder.busContainer.setVisibility(View.GONE);
            holder.busBg.setVisibility(View.GONE);
            holder.tvStationName.setText(data.station.getBusStationName());

            if (data.busState != 0 || position == getItemCount() - 1) {
                holder.stationLine.setVisibility(View.GONE);
            } else {
                holder.stationLine.setVisibility(View.VISIBLE);
            }

            if (data.busState == StationAndBus.DAO_STATE_ARRIVE) {
                holder.stationContainer.setBackgroundResource(R.color.green_200);
                holder.tvStationName.setTextColor(Color.WHITE);
                holder.ivStationLogo.setImageResource(R.drawable.ic_directions_bus_white_24dp);
            } else {
                holder.stationContainer.setBackgroundResource(R.color.grey_200);
                holder.tvStationName.setTextColor(mContext.getResources().getColor(R.color.color_primary_text));
                holder.ivStationLogo.setImageResource(R.drawable.ic_directions_bus_black_24dp);
            }
        } else if (data.type == StationAndBus.DAO_TYPE_BUS) {
            String busName = String.format("%s（%s）", mRunPathName, data.bus.getNumberPlate());

            String time = data.bus.getGPSTime().substring(0, data.bus.getGPSTime().lastIndexOf(":") + 3);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date gpsDate = new Date();
            Date nowDate = Calendar.getInstance().getTime();

            try {
                gpsDate = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (gpsDate != null && nowDate != null) {
                long timeLong = nowDate.getTime() - gpsDate.getTime();
                if (timeLong < 60 * 1000)
                    time = timeLong / 1000 + "秒前";
                else if (timeLong < 60 * 60 * 1000) {
                    timeLong = timeLong / 1000 / 60;
                    time = timeLong + "分钟前";
                } else if (timeLong < 60 * 60 * 24 * 1000) {
                    timeLong = timeLong / 60 / 60 / 1000;
                    time = timeLong + "小时前";
                } else {
                    time = "超时";
                }
            }

            holder.stationContainer.setVisibility(View.GONE);
            holder.busContainer.setVisibility(View.VISIBLE);
            holder.tvBusName.setText(busName);
            holder.tvBusTime.setText(time);

            holder.busBg.setVisibility(View.VISIBLE);
            if (data.bus.getOutstate().equals("0")) {
                holder.busContainer.setBackgroundResource(R.color.green_200);
                holder.busBg.setBackgroundResource(R.color.green_200);
                holder.tvBusState.setText("到站");
            } else {
                holder.busContainer.setBackgroundResource(R.color.grey_200);
                holder.busBg.setBackgroundResource(R.color.light_blue_300);
                holder.tvBusState.setText("途中");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mLineList.size();
    }

    public class LineBusViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.itemLineStation)
        RelativeLayout stationContainer;
        @Bind(R.id.itemStationLogo)
        ImageView ivStationLogo;
        @Bind(R.id.itemStationLine)
        View stationLine;
        @Bind(R.id.itemStationName)
        TextView tvStationName;
        @Bind(R.id.itemLineBus)
        RelativeLayout busContainer;
        @Bind(R.id.itemBusBg)
        View busBg;
        @Bind(R.id.itemBusState)
        TextView tvBusState;
        @Bind(R.id.itemBusName)
        TextView tvBusName;
        @Bind(R.id.itemBusGPSTime)
        TextView tvBusTime;

        public LineBusViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
