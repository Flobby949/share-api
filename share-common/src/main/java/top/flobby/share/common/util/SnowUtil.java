package top.flobby.share.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * @author : Flobby
 * @program : share-api
 * @description : 雪花算法工具类
 * @create : 2023-10-07 13:49
 **/

public class SnowUtil {
    /**
     * 数据中心
     */
    private static final long DATA_CENTER_ID = 1;

    /**
     * 机器标识
     */
    private static final long WORKER_ID = 1;

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID).nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID).nextIdStr();
    }
}
