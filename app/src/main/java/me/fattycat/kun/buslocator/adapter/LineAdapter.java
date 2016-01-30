package me.fattycat.kun.buslocator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private LayoutInflater mLayoutInflater;
    private String mRunPathName;
    private List<StationAndBus> mLineList;

    private boolean mIfHasBus = false;

    public LineAdapter(Context context) {
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
            holder.tvStationName.setText(data.station.getBusStationName());
            if (position == 0 || mIfHasBus) {
                holder.upPath.setVisibility(View.GONE);
            } else {
                holder.upPath.setVisibility(View.VISIBLE);
            }

            mIfHasBus = false;
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

            if (data.bus.getOutstate().equals("0")) {
                holder.busBg.setBackgroundResource(R.color.green_300);
                holder.tvBusState.setText("到站");
            } else {
                holder.busBg.setBackgroundResource(R.color.light_blue_300);
                holder.tvBusState.setText("途中");
            }

            mIfHasBus = true;
        }
    }

    @Override
    public int getItemCount() {
        return mLineList.size();
    }


    public class LineBusViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.itemLineStation)
        RelativeLayout stationContainer;
        @Bind(R.id.itemLineStationBg)
        View stationBg;
        @Bind(R.id.itemStationName)
        TextView tvStationName;
        @Bind(R.id.itemLineStationUP)
        View upPath;

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
