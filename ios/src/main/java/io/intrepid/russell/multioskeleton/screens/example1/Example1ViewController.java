package io.intrepid.russell.multioskeleton.screens.example1;

import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IBAction;
import org.moe.natj.objc.ann.ObjCClassName;
import org.moe.natj.objc.ann.Selector;

import apple.uikit.UIButton;
import apple.uikit.UIViewController;
import io.intrepid.russell.multioskeleton.base.BaseMvpViewController;
import io.intrepid.russell.multioskeleton.base.PresenterConfiguration;
import io.intrepid.russell.multioskeleton.screens.example2.Example2ViewController;

@org.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("Example1ViewController")
@RegisterOnStartup
@SuppressWarnings("JniMissingFunction")
public class Example1ViewController extends BaseMvpViewController<Example1Contract.Presenter> implements Example1Contract.View {

    @Owned
    @Selector("alloc")
    public static native Example1ViewController alloc();

    @Selector("init")
    public native Example1ViewController init();

    protected Example1ViewController(Pointer peer) {
        super(peer);
    }

    public Example1Contract.Presenter createPresenter(PresenterConfiguration configuration) {
        return new Example1Presenter(this, configuration);
    }

    @Override
    public void gotoExample2() {
        UIViewController viewController = Example2ViewController.alloc().init();
        navigationController().pushViewControllerAnimated(viewController, true);
    }

    @IBAction
    @Selector("buttonPress:")
    public void onButtonPress(UIButton sender) {
        presenter.onButtonClick();
    }
}
