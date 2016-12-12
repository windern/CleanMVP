package com.windern.cleanmvp.data.database;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import java.util.Date;

/**
 * Created by wenxinlin on 2016/12/12.
 */

@Entity
public class Note {
    @Id
    private Long id;

    private String text;
    private String comment = "11";
    private java.util.Date date;

    @Convert(converter = NoteTypeConverter.class, columnType = Integer.class)
    private NoteType noteType = NoteType.TEXT;

    @Generated(hash = 928416958)
    public Note(Long id, String text, String comment, java.util.Date date,
            NoteType noteType) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
        this.noteType = noteType;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public java.util.Date getDate() {
        return this.date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public NoteType getNoteType() {
        return this.noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

}
