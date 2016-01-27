package me.fattycat.kun.buslocator.model;

import android.graphics.drawable.Drawable;

import com.quinny898.library.persistentsearch.SearchResult;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Descirption:
 */
public class LinesResult extends SearchResult {
    public String runPathId;
    public String runPathName;

    public LinesResult(String runPathId, String title) {
        super(title);
        this.runPathId = runPathId;
        this.runPathName = title;
    }

    public LinesResult(String title, Drawable icon) {
        super(title, icon);
    }

    public LinesResult(int viewType, String title) {
        super(viewType, title);
    }
}
