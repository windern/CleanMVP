package com.windern.cleanmvp.presentation.proconsumer.bluetooth;

/**
 * Created by windern on 2017/2/28.
 */

public class SendProducer {
    public static SendProducer getInstance(){
        return SendProducerHolder.sendProducer;
    }

    private static class SendProducerHolder{
        private final static SendProducer sendProducer = new SendProducer();
    }

    private SendStorage sendStorage;

    public void setSendStorage(SendStorage sendStorage) {
        this.sendStorage = sendStorage;
    }

    public BtMessage sendMessage(String message){
        BtMessage btMessage = null;

        try {
            btMessage = new BtMessage();
            btMessage.setMesssage(message);
            sendStorage.push(btMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return btMessage;
    }
}
