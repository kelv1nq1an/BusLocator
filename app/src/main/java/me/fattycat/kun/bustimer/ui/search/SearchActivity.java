package me.fattycat.kun.bustimer.ui.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fattycat.kun.bustimer.R;
import me.fattycat.kun.bustimer.data.model.SearchModel;
import me.fattycat.kun.bustimer.ui.view.MultiStateView;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    @BindView(R.id.search_edit_text)
    EditText       searchEditText;
    @BindView(R.id.search_result_recycler_view)
    RecyclerView   searchResultRecyclerView;
    @BindView(R.id.search_state_view)
    MultiStateView searchStateView;

    private SearchContract.Presenter presenter;
    private SearchAdapter            searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchAdapter = new SearchAdapter();
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultRecyclerView.setAdapter(searchAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
        initMultiStateView();
        new SearchPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    private void initMultiStateView() {
        searchStateView.addViewForStatus(MultiStateView.STATE_EMPTY, R.layout.layout_empty);
        searchStateView.addViewForStatus(MultiStateView.STATE_FAIL, R.layout.layout_error);

        searchStateView.setOnInflateListener(new MultiStateView.OnInflateListener() {
            @Override
            public void onInflate(int state, View view) {
                if (state == MultiStateView.STATE_FAIL) {
                    view.findViewById(R.id.layout_error_retry_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            search(searchEditText.getText().toString());
                        }
                    });
                }
            }
        });
    }

    private void search(String name) {
        if (TextUtils.isEmpty(name)) {
            searchAdapter.clearData();
        } else {
            presenter.searchByName(name);
        }
    }

    @OnClick(R.id.search_close_image_view)
    public void onClose() {
        finish();
    }

    @Override
    public void onSearchStart() {

    }

    @Override
    public void onSearchSuccess(SearchModel searchModel) {
        if (searchModel != null && searchModel.getLines().size() != 0) {
            searchStateView.setViewState(MultiStateView.STATE_CONTENT);
            searchAdapter.setData(searchModel.getLines());
        } else {
            searchStateView.setViewState(MultiStateView.STATE_EMPTY);
        }
    }

    @Override
    public void onSearchError() {
        searchStateView.setViewState(MultiStateView.STATE_FAIL);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
