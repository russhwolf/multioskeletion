package io.intrepid.russell.multioskeleton.base;

import android.util.Log;

import org.moe.natj.general.Pointer;

import apple.uikit.UIApplication;
import apple.uikit.UIViewController;
import io.intrepid.russell.multioskeleton.Main;


public class BaseViewController extends UIViewController {
    // TODO are there other relevant lifecycle hooks?

    private static final String TAG = BaseViewController.class.getSimpleName();

    protected BaseViewController(Pointer peer) {
        super(peer);
    }

    @Override
    public void viewDidLoad() {
        Log.v(TAG, "Lifecycle viewDidLoad: " + this);
        super.viewDidLoad();
    }

    @Override
    public void viewWillAppear(boolean animated) {
        Log.v(TAG, "Lifecycle viewWillAppear: " + this);
        super.viewWillAppear(animated);
    }

    @Override
    public void viewDidAppear(boolean animated) {
        Log.v(TAG, "Lifecycle viewDiviewDidAppeardLoad: " + this);
        super.viewDidAppear(animated);
    }

    @Override
    public void viewWillDisappear(boolean animated) {
        Log.v(TAG, "Lifecycle viewWillDisappear: " + this);
        super.viewWillDisappear(animated);
    }

    @Override
    public void viewDidDisappear(boolean animated) {
        Log.v(TAG, "Lifecycle viewDidDisappear: " + this);
        super.viewDidDisappear(animated);
    }

    public Main getApplicationDelegate() {
        return (Main) UIApplication.sharedApplication().delegate();
    }
}
