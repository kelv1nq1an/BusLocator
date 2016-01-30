/*
 * Copyright (C) 2016 FattycatR<kelv1nq1an>
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.fattycat.kun.buslocator.dao;

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
