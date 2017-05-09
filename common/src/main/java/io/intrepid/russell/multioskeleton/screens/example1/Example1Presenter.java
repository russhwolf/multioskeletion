package io.intrepid.russell.multioskeleton.screens.example1;

import io.intrepid.russell.multioskeleton.base.BasePresenter;
import io.intrepid.russell.multioskeleton.base.PresenterConfiguration;
import io.reactivex.annotations.NonNull;

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
