package com.sty.multi.thread.change;

import android.util.Log;

/**
 * join()[阻塞]的作用是让指定的线程先执行完再执行其它线程，而且会阻塞主线程
 * Created by tian on 2019/11/15.
 */

public class ThreadJoin {

    public void start()  {

        try {
            Thread thread1 = new MyThread("主线程");
            Thread thread2 = new MyThread("线程二");
            thread1.start();
            thread1.join();
            Log.i("sty", "主线程 等待中...");
            thread2.start();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    class MyThread extends Thread{
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            super.run();
            try {
                Log.i("sty", getName() + "-> 在运行...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
