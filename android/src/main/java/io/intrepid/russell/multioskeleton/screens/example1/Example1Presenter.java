package io.intrepid.russell.multioskeleton.screens.example1;

import android.support.annotation.NonNull;

import io.intrepid.russell.multioskeleton.base.BasePresenter;
import io.intrepid.russell.multioskeleton.base.PresenterConfiguration;

class Example1Presenter extends BasePresenter<Example1Contract.View> implements Example1Contract.Presenter {

    Example1Presenter(@NonNull Example1Contract.View view,
                      @NonNull PresenterConfiguration configuration) {
        super(view, configuration);
    }

    @Override
    public void onButtonClick() {
        view.gotoExample2();
    }
}
