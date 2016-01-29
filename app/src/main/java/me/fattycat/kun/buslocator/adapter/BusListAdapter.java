package me.fattycat.kun.buslocator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.fattycat.kun.buslocator.R;
import me.fattycat.kun.buslocator.model.BusGPSEntity;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Descirption:
 */
public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.LineListViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String mRunPathName;
    private List<BusGPSEntity.ResultEntity.ListsEntity> mBusEntities;

    public BusListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mBusEntities = new ArrayList<>();
        mRunPathName = "";
    }

    public void setRunPathName(String runPathName) {
        mRunPathName = runPathName;
    }

    public void refreshData(List<BusGPSEntity.ResultEntity.ListsEntity> data) {
        clearData();

        if (data != null) {
            mBusEntities.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        mBusEntities.clear();
        notifyDataSetChanged();
    }

    @Override
    public LineListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_bus, parent, false);
        return new LineListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LineListViewHolder holder, int position) {
        final BusGPSEntity.ResultEntity.ListsEntity bus = mBusEntities.get(position);

        String busName = String.format("%s（%s）", mRunPathName, bus.getNumberPlate());
        String time = bus.getGPSTime();
        int start = time.indexOf(":") - 2;
        int end = time.lastIndexOf(":") + 3;

        holder.tvLineName.setText(busName);
        holder.tvGPSStation.setText(String.format("%s", bus.getBusStationName()));

        holder.tvGPSTime.setText(bus.getGPSTime().substring(start, end));
    }

    @Override
    public int getItemCount() {
        return mBusEntities == null ? 0 : mBusEntities.size();
    }

    public class LineListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.itemBusName)
        TextView tvLineName;
        @Bind(R.id.itemBusGPSStation)
        TextView tvGPSStation;
        @Bind(R.id.itemBusGPSTime)
        TextView tvGPSTime;

        public LineListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
