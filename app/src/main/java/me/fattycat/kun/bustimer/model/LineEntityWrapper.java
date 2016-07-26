package me.fattycat.kun.bustimer.model;

/**
 * Author: Kelvinkun
 * Date: 16/7/19
 */

public class LineEntityWrapper {
    private LineEntity entity;
    private String flag;

    public LineEntityWrapper(LineEntity lineEntity, String flag) {
        this.entity = lineEntity;
        this.flag = flag;
    }

    public LineEntity getEntity() {
        return entity;
    }

    public void setEntity(LineEntity entity) {
        this.entity = entity;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
