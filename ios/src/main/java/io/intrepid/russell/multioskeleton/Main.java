package io.intrepid.russell.multioskeleton;

import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.objc.ann.Selector;

import apple.NSObject;
import apple.foundation.NSDictionary;
import apple.uikit.UIApplication;
import apple.uikit.UIWindow;
import apple.uikit.c.UIKit;
import apple.uikit.protocol.UIApplicationDelegate;
import io.intrepid.russell.multioskeleton.base.PresenterConfiguration;
import io.intrepid.russell.multioskeleton.logging.IosCrashReporter;
import io.intrepid.russell.multioskeleton.rest.RetrofitClient;
import io.intrepid.russell.multioskeleton.settings.NSUserDefaultsManager;
import io.reactivex.schedulers.Schedulers;
import rx.ios.schedulers.IOSSchedulers;

@RegisterOnStartup
public class Main extends NSObject implements UIApplicationDelegate {

    public static void main(String[] args) {
        UIKit.UIApplicationMain(0, null, null, Main.class.getName());
    }

    /**
     * @noinspection JniMissingFunction
     */
    @Selector("alloc")
    public static native Main alloc();

    protected Main(Pointer peer) {
        super(peer);
    }

    private UIWindow window;

    @Override
    public boolean applicationDidFinishLaunchingWithOptions(UIApplication application, NSDictionary launchOptions) {
        return true;
    }

    @Override
    public void setWindow(UIWindow value) {
        window = value;
    }

    @Override
    public UIWindow window() {
        return window;
    }

    public PresenterConfiguration getPresenterConfiguration() {
        return new PresenterConfiguration(
                Schedulers.io(),
                IOSSchedulers.mainThread(),
                NSUserDefaultsManager.getInstance(),
                RetrofitClient.getApi(),
                IosCrashReporter.getInstance()
        );
    }
}
