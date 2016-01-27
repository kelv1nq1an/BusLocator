package me.fattycat.kun.buslocator;

/**
 * Author: Kelvinkun
 * Time: 16/1/27
 * Descirption:
 */
public class Event {

    public static class SearchLineEvent {
        public String runPathId;

        public SearchLineEvent(String runPathId) {
            this.runPathId = runPathId;
        }
    }
}
