package com.windern.cleanmvp.data.database;

/**
 * Created by wenxinlin on 2016/12/12.
 */

public enum NoteType {
    TEXT(0, "text"), LIST(1, "list"), PICTURE(2, "picture");

    private int value;
    private String text;

    private NoteType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
