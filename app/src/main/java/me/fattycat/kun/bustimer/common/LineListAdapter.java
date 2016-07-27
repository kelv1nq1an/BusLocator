package me.fattycat.kun.bustimer.common;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fattycat.kun.bustimer.net.BusTimerApi;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.detail.DetailActivity;
import me.fattycat.kun.bustimer.model.LineEntity;
import me.fattycat.kun.bustimer.model.LineEntityWrapper;
import me.fattycat.kun.bustimer.model.LineInfoSerializable;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public class LineListAdapter extends RecyclerView.Adapter<LineListAdapter.LineViewHolder> {

    private Context context;
    private List<LineEntityWrapper> lineList;

    public LineListAdapter(Context context, List<LineEntityWrapper> data) {
        this.context = context;
        if (data == null) {
            this.lineList = new ArrayList<>();
        } else {
            this.lineList = data;
        }
    }

    public void setData(List<LineEntityWrapper> data) {
        if (data == null) {
            lineList.clear();
        } else {
            this.lineList = data;
        }
        notifyDataSetChanged();
    }

    public void addData(LineEntityWrapper line) {
        this.lineList.add(line);
        notifyDataSetChanged();
    }

    @Override
    public LineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRoot = LayoutInflater.from(context).inflate(R.layout.item_line, parent, false);
        return new LineViewHolder(itemRoot);
    }

    @Override
    public void onBindViewHolder(LineViewHolder holder, int position) {
        if (position > lineList.size()) {
            return;
        }
        final LineEntity.ResultEntity line = lineList.get(position).getEntity().getResult();
        final String flag = lineList.get(position).getFlag();
        holder.itemLineNameTextView.setText(line.getRunPathName());
        holder.itemLineStationStartTextView.setText(line.getStartStation());
        holder.itemLineStationEndTextView.setText(line.getEndStation());
        String startTime;
        String endTime;
        if (TextUtils.equals(flag, BusTimerApi.FLAG_LINE_GO)) {
            startTime = line.getStartTime();
            endTime = line.getEndTime();
        } else {
            startTime = line.getStartTime1();
            endTime = line.getEndTime1();
        }
        holder.itemLineBackForthTimeTextView.setText(String.format("%s ~ %s", startTime, endTime));
        holder.itemLineTimeIntervalTextView.setText(String.format("%s 分钟", line.getBusInterval()));

        holder.itemlineCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineInfoSerializable lineInfo = new LineInfoSerializable();
                lineInfo.setEndTime1(line.getEndTime1());
                lineInfo.setStartTime(line.getStartTime());
                lineInfo.setStartStation(line.getStartStation());
                lineInfo.setRunFlag(line.getRunFlag());
                lineInfo.setRunPathName(line.getRunPathName());
                lineInfo.setBusInterval(line.getBusInterval());
                lineInfo.setEndStation(line.getEndStation());
                lineInfo.setRunPathId(line.getRunPathId());
                lineInfo.setEndTime(line.getEndTime());
                lineInfo.setStartTime1(line.getStartTime1());
                lineInfo.setFlag(flag);
                context.startActivity(DetailActivity.getCallingIntent(context, lineInfo));
            }
        });
    }

    @Override
    public int getItemCount() {
        return lineList == null ? 0 : lineList.size();
    }

    public void clearData() {
        lineList.clear();
        notifyDataSetChanged();
    }

    class LineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemLineCardView)
        CardView itemlineCardView;
        @BindView(R.id.itemLineStationStartTextView)
        TextView itemLineStationStartTextView;
        @BindView(R.id.itemLineStationEndTextView)
        TextView itemLineStationEndTextView;
        @BindView(R.id.itemLineNameTextView)
        TextView itemLineNameTextView;
        @BindView(R.id.itemLineBackForthTimeTextView)
        TextView itemLineBackForthTimeTextView;
        @BindView(R.id.itemLineTimeIntervalTextView)
        TextView itemLineTimeIntervalTextView;

        LineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
