package io.intrepid.russell.multioskeleton.screens.example2;

import android.content.Intent;
import android.support.v4.app.Fragment;

import io.intrepid.russell.multioskeleton.base.BaseFragmentActivity;

public class Example2Activity extends BaseFragmentActivity {

    @Override
    protected Fragment createFragment(Intent intent) {
        return new Example2Fragment();
    }
}
