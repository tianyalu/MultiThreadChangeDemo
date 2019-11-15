## MultiThreadChangeDemo 多线程调度测试demo
### 一、概念
#### 1.1 对象锁与类锁
##### 类锁：
```android 
    //同步class对象，类锁
    public void syncClassMethod() {
        synchroninzed (SynchronizedDemo.class) {
            //...
        }
    }
```
```android 
    //同步静态方法，类锁
    public static synchronized void syncStaticMethod() {
        //...
    }
```
##### 对象锁：
```android 
    //同步方法，对象锁
    public synchronized void syncMethod() {
        //...
    }
```
```android 
    //同步块，对象锁
    public void syncThis() {
        synchronized (this) {
            //...
        }
    }
```

#### 1.2 总结
* Thread是个线程，而且有自己的生命周期
* 对于线程常用的操作有：wait（等待）、notify（唤醒）、notifyAll、sleep（睡眠）、join（阻塞）、yield（礼让）
* wait、notify、notifyAll都必须在synchronized中执行，否则会抛出异常
* synchronized关键字和ReentrantLock锁都是辅助线程同步使用的
* 一个对象只有一个锁
* 不同对象实例的对象锁是互不干扰的，但是每个类只有一个类锁
* 类锁和对象锁互相不干扰（线程不安全）

#### 1.3 线程的生命周期
![image](https://github.com/tianyalu/MultiThreadChangeDemo/blob/master/show/thread_lifecycle.png)


### 二、 线程同步
#### 2.1 wait(),notify()
子线程循环5次，接着主线程循环5次，再回到子线程循环5次，接着再回到主线程循环5次...保证数据正确
##### 2.1.1 核心代码
```android 
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
```
##### 2.1.2 打印日志
![image](https://github.com/tianyalu/MultiThreadChangeDemo/blob/master/show/thread_wait_notify.png)


#### 2.2 join()
join()[阻塞]的作用是让指定的线程先执行完再执行其它线程，而且会阻塞主线程
##### 2.2.1 核心代码
```android 
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
```
##### 2.2.2 打印日志
![image](https://github.com/tianyalu/MultiThreadChangeDemo/blob/master/show/thread_join.png)

#### 2.3 join()
yield()[礼让]的作用是指定的线程先礼让一下其它线程，让其它线程先执行。  
**注意**：  
yield()会礼让给同优先级或者更高优先级的线程，不过yield()只是把本线程的状态打回就绪状态，所以执行该方法后，
有可能马上又执行，或者也可能等待很长时间。
##### 2.3.1 核心代码
```android 
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
```
##### 2.3.2 打印日志
![image](https://github.com/tianyalu/MultiThreadChangeDemo/blob/master/show/thread_yield.png)  



参考：[多线程wait、notify、sleep、join、yield、synchronized关键字 深入了解线程(Thread)](https://course.study.163.com/480000005355162/learning)
