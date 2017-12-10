package com.closer.ws.util;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * snowFlake
     */
    public static long snowFlakeId() {
        return SequenceHolder.sequence.nextId();
    }

    /**
     * snowFlake
     */
    public static String snowFlakeStringId() {
        return String.valueOf(SequenceHolder.sequence.nextId());
    }

    private static class SequenceHolder {
        private static final Sequence sequence = new Sequence();
    }

}
