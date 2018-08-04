package com.example.basedemo.util;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangyp
 */
@Slf4j
public class GlobalEvent {

    private static EventBus instance = new EventBus("global");

    static {
        instance.register(new DeadEventListener());
    }

    public static void register( @NonNull Object eventListener ) {
        instance.register(eventListener);
    }


    public static void unregister( @NonNull Object eventListener ) {
        instance.unregister(eventListener);
    }


    public static void post( @NonNull Object event ) {
        instance.post(event);
    }


    /**
     * 处理投递失败的事件
     */
    private static class DeadEventListener {

        @Subscribe
        public void handlerDeadEvent( DeadEvent deadEvent ) {
            log.warn("全局事件没有订阅者接收消息, event: {}, source: {}", deadEvent.getEvent(), deadEvent.getSource());
        }
    }
}
