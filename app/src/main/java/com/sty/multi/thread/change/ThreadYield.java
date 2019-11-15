package com.sty.multi.thread.change;

import android.util.Log;

/**
 * yield()[礼让]的作用是指定的线程先礼让一下其它线程，让其它线程先执行。yield()会礼让给同优先级或者更高优先级
 * 的线程，不过yield()只是把本线程的状态打回就绪状态，所以执行该方法后，有可能马上又执行，或者也可能等待很长时间。
 * Created by tian on 2019/11/15.
 */

public class ThreadYield {
    private int index = 0;  //避免线程的索引相互影响

    public void start()  {
        Thread thread1 = new MyThread("主线程");
        Thread thread2 = new MyThread("线程二");
        thread1.start();
        thread2.start();
    }

    class MyThread extends Thread{
        public MyThread(String name) {
            super(name);
        }

        @Override
        public synchronized void run() {
            super.run();
            for (int i = index; i < 10; i++) {
                Log.w("sty", getName() + "在运行,i的值为：" + i + " 优先级为： " + getPriority());
                if(i == 2) {
                    Log.w("sty", getName() + "-> 礼让");
                    Thread.yield();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
