package io.intrepid.russell.multioskeleton.logging;

import android.util.Log;

/**
 * TODO just no-op for now
 */
public class IosCrashReporter implements CrashReporter {
    private static IosCrashReporter instance;

    public static void init() {
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
