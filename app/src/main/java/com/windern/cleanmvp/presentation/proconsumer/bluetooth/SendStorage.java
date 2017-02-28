package com.windern.cleanmvp.presentation.proconsumer.bluetooth;

import com.windern.cleanmvp.presentation.proconsumer.Product;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by windern on 2017/2/28.
 */

public class SendStorage {
    private Long size = 0L;

    BlockingQueue<BtMessage> queues = new LinkedBlockingQueue<BtMessage>();

    public void push(BtMessage btMessage) throws InterruptedException {
        synchronized (size) {
            queues.put(btMessage);
            size++;
            btMessage.setId(size);
            btMessage.create();
        }
    }

    public BtMessage pop() throws InterruptedException {
        return queues.take();
    }
}
