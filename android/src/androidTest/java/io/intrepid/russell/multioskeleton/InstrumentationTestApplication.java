package io.intrepid.russell.multioskeleton;

import android.os.AsyncTask;

import org.mockito.Mockito;

import io.intrepid.russell.multioskeleton.base.PresenterConfiguration;
import io.intrepid.russell.multioskeleton.logging.CrashReporter;
import io.intrepid.russell.multioskeleton.rest.RestApi;
import io.intrepid.russell.multioskeleton.rest.RetrofitClient;
import io.intrepid.russell.multioskeleton.settings.SharePreferencesManager;
import io.intrepid.russell.multioskeleton.settings.UserSettings;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InstrumentationTestApplication extends MultioskeletonApplication {
    private static RestApi restApiOverride = null;
    private static UserSettings userSettingsOverride = null;

    @Override
    public PresenterConfiguration getPresenterConfiguration() {
        return new PresenterConfiguration(
                // using AsyncTask executor since Espresso automatically waits for it to clear before proceeding
                Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR),
                AndroidSchedulers.mainThread(),
                userSettingsOverride != null ? userSettingsOverride : SharePreferencesManager.getInstance(this),
                restApiOverride != null ? restApiOverride : RetrofitClient.getApi(),
                Mockito.mock(CrashReporter.class));
    }

    public static void overrideRestApi(RestApi restApi) {
        restApiOverride = restApi;
    }

    public static void clearRestApiOverride() {
        restApiOverride = null;
    }

    public static void overrideUserSettings(UserSettings userSettings) {
        userSettingsOverride = userSettings;
    }

    public static void clearUserSettingsOverride() {
        userSettingsOverride = null;
    }
}
