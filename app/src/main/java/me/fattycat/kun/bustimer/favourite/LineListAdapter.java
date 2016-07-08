package me.fattycat.kun.bustimer.favourite;

import android.content.Context;
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
import me.fattycat.kun.bustimer.model.LineEntity;

/**
 * Author: Kelvinkun
 * Date: 16/6/30
 */

public class LineListAdapter extends RecyclerView.Adapter<LineListAdapter.LineViewHolder> {

    private Context context;
    private List<LineEntity> lineList;

    public LineListAdapter(Context context, List<LineEntity> data) {
        this.context = context;
        if (data == null) {
            this.lineList = new ArrayList<>();
        } else {
            this.lineList = data;
        }
    }

    public void setData(List<LineEntity> data) {
        this.lineList = data;
        notifyDataSetChanged();
    }

    public void addData(LineEntity line) {
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
        LineEntity.ResultEntity line = lineList.get(position).getResult();
        holder.itemLineNameTextView.setText(line.getRunPathName());
        holder.itemLineStationStartTextView.setText(line.getStartStation());
        holder.itemLineStationEndTextView.setText(line.getEndStation());
        holder.itemLineBackForthTimeTextView.setText(String.format("%s ~ %s", line.getStartTime(), line.getEndTime()));
        holder.itemLineTimeIntervalTextView.setText(String.format("%s 分钟", line.getBusInterval()));
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
