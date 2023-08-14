package com.starlight.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-28  09:42
 * @Description: 雪花算法实现全局唯一的纯数字id
 * 这个工具类中的 `nextId()` 方法将生成一个64位的长整型唯一标识符，其中包含了数据中心ID、机器ID、时间戳以及序列号等信息。你可以通过创建 `SnowflakeIdGenerator` 类的实例来调用 `nextId
 * ()` 方法，例如：
 * <p>
 * ```java
 * SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator();
 * long id = idGenerator.nextId();
 */
public class SnowflakeIdGenerator {
    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;    //机器标识
    private long sequence = 0L; //序列号
    private long lastStamp = -1L;//上一次时间戳

    public SnowflakeIdGenerator() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("./phoenix-parent/src/main/resources/config" + ".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long datacenterId = Long.parseLong(prop.getProperty("datacenterId"));
        long machineId = Long.parseLong(prop.getProperty("workerId"));
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStamp = getNewTimestamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMillis();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastStamp = currStamp;

        //时间戳部分
        return (currStamp - START_STAMP) << TIMESTAMP_LEFT
            //数据中心部分
            | datacenterId << DATACENTER_LEFT
            //机器标识部分
            | machineId << MACHINE_LEFT
            //序列号部分
            | sequence;
    }

    private long getNextMillis() {
        long milliSeconds = getNewTimestamp();
        while (milliSeconds <= lastStamp) {
            milliSeconds = getNewTimestamp();
        }
        return milliSeconds;
    }

    private long getNewTimestamp() {
        return System.currentTimeMillis();
    }
}
