package hu.ait.android.sugarorm.model;

import com.orm.SugarRecord;

public class Note extends SugarRecord {
    private String description;
    private String createDate;

    public Note() {
    }

    public Note(String description, String createDate) {
        this.description = description;
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}
