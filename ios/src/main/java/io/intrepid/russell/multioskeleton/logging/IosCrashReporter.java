package io.intrepid.russell.multioskeleton.logging;


import android.util.Log;

import org.moe.binding.crashlytics.Crashlytics;
import org.moe.binding.fabric.Fabric;

import apple.foundation.NSArray;

public class IosCrashReporter implements CrashReporter {

    private static IosCrashReporter instance;

    public static void init() {
        Fabric.with(NSArray.arrayWithObject(Crashlytics.class_objc_static()));
        instance = new IosCrashReporter();
    }

    public static CrashReporter getInstance() {
        return instance;
    }

    private IosCrashReporter() {
    }

    @Override
    public void logException(Throwable throwable) {
        // TODO
        throwable.printStackTrace();
    }

    @Override
    public void log(int priority, String tag, String message) {
        // TODO
        Log.println(priority, tag, message);
    }
}
