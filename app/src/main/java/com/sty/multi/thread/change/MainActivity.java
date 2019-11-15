package com.sty.multi.thread.change;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ThreadWaitNotify threadWaitNotify = new ThreadWaitNotify();
        threadWaitNotify.start();

//        ThreadJoin threadJoin = new ThreadJoin();
//        threadJoin.start();

//        ThreadYield threadYield = new ThreadYield();
//        threadYield.start();
    }
}
