
package com.neko233.game.common.timeTask;

/**
 * 定时任务接口
 */
public interface ITimerTask {

    /**
     * 获取在什么时候运行
     *
     * @return 时间戳
     */
    long getRunAtTime();

    /**
     * 执行任务
     */
    void doTask();

}
