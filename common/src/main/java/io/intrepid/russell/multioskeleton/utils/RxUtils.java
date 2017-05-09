package io.intrepid.russell.multioskeleton.utils;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxUtils {

    @NonNull
    public static Consumer<Throwable> logError() {
        return throwable -> {
            System.err.println("observable stream encountered an error");
            throwable.printStackTrace();
        };
    }

    public static void unsubscribeDisposable(@Nullable Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
