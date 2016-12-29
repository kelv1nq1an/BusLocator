package me.fattycat.kun.bustimer.ui.search;

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
import me.fattycat.kun.bustimer.data.model.SearchModel;
import me.fattycat.kun.bustimer.ui.detail.DetailActivity;

/**
 * Author: qk329
 * Date: 2016/12/15
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchResultHolder> {

    private List<SearchModel.LinesEntity> linesEntityList;

    public SearchAdapter() {
        linesEntityList = new ArrayList<>();
    }

    public void setData(List<SearchModel.LinesEntity> linesEntities) {
        this.linesEntityList = linesEntities;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.linesEntityList.clear();
        notifyDataSetChanged();
    }

    @Override
    public SearchResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchResultHolder holder, int position) {
        final SearchModel.LinesEntity linesEntity = linesEntityList.get(position);
        final Context                 context     = holder.itemView.getContext();
        holder.itemSearchResultTitleTextView.setText(linesEntity.getRunPathName());
        holder.itemSearchResultStartStationTextView.setText(linesEntity.getStartName());
        holder.itemSearchResultEndStationTextView.setText(linesEntity.getEndName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(DetailActivity.newIntent(context, linesEntity.getRunPathId(), linesEntity.getRunPathName()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return linesEntityList.size();
    }

    class SearchResultHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_search_result_title_text_view)
        TextView itemSearchResultTitleTextView;
        @BindView(R.id.item_search_result_start_station_text_view)
        TextView itemSearchResultStartStationTextView;
        @BindView(R.id.item_search_result_end_station_text_view)
        TextView itemSearchResultEndStationTextView;

        SearchResultHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
