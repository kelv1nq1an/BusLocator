package me.fattycat.kun.bustimer.search;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Author: Kelvinkun
 * Date: 16/7/7
 */

public class LinesSearchSuggestion implements SearchSuggestion {
    private String lineName;

    public LinesSearchSuggestion(String suggestion) {
        this.lineName = suggestion;
    }

    public LinesSearchSuggestion(Parcel source) {
        this.lineName = source.readString();
    }

    public static final Creator<LinesSearchSuggestion> CREATOR = new Creator<LinesSearchSuggestion>() {
        @Override
        public LinesSearchSuggestion createFromParcel(Parcel parcel) {
            return new LinesSearchSuggestion(parcel);
        }

        @Override
        public LinesSearchSuggestion[] newArray(int i) {
            return new LinesSearchSuggestion[i];
        }
    };

    @Override
    public String getBody() {
        return lineName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lineName);
    }
}
