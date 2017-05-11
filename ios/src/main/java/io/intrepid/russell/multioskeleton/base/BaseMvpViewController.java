package io.intrepid.russell.multioskeleton.base;

import android.annotation.NonNull;

import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCClassName;

@org.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("BaseMvpViewController")
@RegisterOnStartup
public abstract class BaseMvpViewController<T extends BaseContract.Presenter> extends BaseViewController implements BaseContract.View {
    // TODO is presenter connecting to correct lifecycle events?

    protected T presenter;

    protected BaseMvpViewController(Pointer peer) {
        super(peer);
    }

    @NonNull
    public abstract T createPresenter(PresenterConfiguration configuration);

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        PresenterConfiguration configuration = getApplicationDelegate().getPresenterConfiguration();
        presenter = createPresenter(configuration);
    }

    @Override
    public void viewWillAppear(boolean animated) {
        super.viewWillAppear(animated);
        presenter.onViewCreated();
    }

    @Override
    public void viewDidAppear(boolean animated) {
        super.viewDidAppear(animated);
        //noinspection unchecked
        presenter.bindView(this);
    }

    @Override
    public void viewWillDisappear(boolean animated) {
        super.viewWillDisappear(animated);
        presenter.unbindView();
    }

    @Override
    public void viewDidDisappear(boolean animated) {
        super.viewDidDisappear(animated);
        presenter.onViewDestroyed();
    }
}
