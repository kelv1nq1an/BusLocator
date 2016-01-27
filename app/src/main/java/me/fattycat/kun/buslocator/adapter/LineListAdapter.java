package me.fattycat.kun.buslocator.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.fattycat.kun.buslocator.Event;
import me.fattycat.kun.buslocator.R;
import me.fattycat.kun.buslocator.model.LineList;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Descirption:
 */
public class LineListAdapter extends RecyclerView.Adapter<LineListAdapter.LineListViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<LineList.ResultEntity.LinesEntity> mLinesEntities;

    public LineListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mLinesEntities = new ArrayList<>();
    }

    public void refreshData(List<LineList.ResultEntity.LinesEntity> data) {
        clearData();

        if (data != null) {
            mLinesEntities.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        mLinesEntities.clear();
        notifyDataSetChanged();
    }

    @Override
    public LineListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_line, parent, false);
        return new LineListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LineListViewHolder holder, int position) {
        final LineList.ResultEntity.LinesEntity linesEntity = mLinesEntities.get(position);
        int index = linesEntity.getRunPathName().indexOf("路");
        String lineName;

        if (index + 1 < linesEntity.getRunPathName().length()) {
            lineName = linesEntity.getRunPathName().replace("路", "路(") + ")";
        } else {
            lineName = linesEntity.getRunPathName();
        }

        holder.tvLineName.setText(lineName);
        holder.tvStartStation.setText(linesEntity.getStartName());
        holder.tvEndStation.setText(linesEntity.getEndName());
        holder.lineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event.SearchLineEvent(linesEntity.getRunPathId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLinesEntities == null ? 0 : mLinesEntities.size();
    }

    public class LineListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_line_card)
        CardView lineCard;
        @Bind(R.id.item_line_Name)
        TextView tvLineName;
        @Bind(R.id.item_line_start_station)
        TextView tvStartStation;
        @Bind(R.id.item_line_end_station)
        TextView tvEndStation;

        public LineListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
