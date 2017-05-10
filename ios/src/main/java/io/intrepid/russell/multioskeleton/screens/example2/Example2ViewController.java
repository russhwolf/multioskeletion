package io.intrepid.russell.multioskeleton.screens.example2;

import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IBOutlet;
import org.moe.natj.objc.ann.ObjCClassName;
import org.moe.natj.objc.ann.Property;
import org.moe.natj.objc.ann.Selector;

import apple.uikit.UILabel;
import io.intrepid.russell.multioskeleton.base.BaseMvpViewController;
import io.intrepid.russell.multioskeleton.base.PresenterConfiguration;

@org.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("Example2ViewController")
@RegisterOnStartup
@SuppressWarnings("JniMissingFunction")
public class Example2ViewController extends BaseMvpViewController<Example2Contract.Presenter> implements Example2Contract.View {

    @Owned
    @Selector("alloc")
    public static native Example2ViewController alloc();

    @Selector("init")
    public native Example2ViewController init();

    protected Example2ViewController(Pointer peer) {
        super(peer);
    }

    public Example2Contract.Presenter createPresenter(PresenterConfiguration configuration) {
        return new Example2Presenter(this, configuration);
    }

    @Override
    public void showCurrentIpAddress(String ip) {
        getCurrentIpView().setText("Your current Ip address is " + ip);
    }

    @Override
    public void showPreviousIpAddress(String ip) {
        getPreviousIpView().setAlpha(1);
        getPreviousIpView().setText("Your previous Ip address is " + ip);
    }

    @Override
    public void hidePreviousIpAddress() {
        getPreviousIpView().setAlpha(0);
    }

    @IBOutlet
    @Selector("currentIpView")
    @Property
    public native UILabel getCurrentIpView();

    @IBOutlet
    @Selector("previousIpView")
    @Property
    public native UILabel getPreviousIpView();
}
