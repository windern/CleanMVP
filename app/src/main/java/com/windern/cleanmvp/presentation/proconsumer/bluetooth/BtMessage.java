package com.windern.cleanmvp.presentation.proconsumer.bluetooth;

import rx.subjects.PublishSubject;

/**
 * Created by windern on 2017/2/28.
 */

public class BtMessage {
    private long id;

    private String messsage;

    private PublishSubject<String> publishSubject;

    public BtMessage(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public void create(){
        publishSubject = PublishSubject.create();
    }

    public PublishSubject<String> getPublishSubject() {
        return publishSubject;
    }
}
