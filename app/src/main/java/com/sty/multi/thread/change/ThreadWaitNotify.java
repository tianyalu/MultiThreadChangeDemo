package com.sty.multi.thread.change;

import android.util.Log;

/**
 * 线程等待与唤醒：
 * 子线程循环5次，接着主线程循环5次，再回到子线程循环5次，接着再回到主线程循环5次...保证数据正确
 * Created by tian on 2019/11/12.
 */

public class ThreadWaitNotify {
    Object objectLock = new Object();

    public void start() {
        //子线程
        WorkThread workThread = new WorkThread();
        workThread.start();
        //主线程
        for (int i = 0; i < 5; i++) {
            Log.e("sty", Thread.currentThread().getName() + "将要获取锁 --> " );
            synchronized (objectLock) {
                try {
                    objectLock.wait(); //先锁住，等待子线程唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 10; j++) {
                    Log.i("sty", Thread.currentThread().getName() + ": 循环第" + (j+1) + "次");
                }
                objectLock.notify();
                Log.e("sty", Thread.currentThread().getName() + ": 循环了" + (i+1) + "次");
            }
        }
    }

    class WorkThread extends Thread{
        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 5; i++) {
                Log.e("sty", Thread.currentThread().getName() + "将要获取锁 --> " );
                synchronized (objectLock) {
                    for (int j = 0; j < 5; j++) {
                        Log.i("sty", Thread.currentThread().getName() + ": 循环第" + (j+1) + "次");
                    }
                    Log.e("sty", Thread.currentThread().getName() + ": 循环了" + (i+1) + "次");
                    objectLock.notify();
                    try {
                        objectLock.wait(); //等待去执行主线程执行数据
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
