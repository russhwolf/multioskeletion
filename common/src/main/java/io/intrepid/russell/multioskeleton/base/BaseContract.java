package io.intrepid.russell.multioskeleton.base;

public interface BaseContract {

    interface View {
    }

    interface Presenter<T extends View> {

        void bindView(T view);

        void unbindView();

        void onViewCreated();

        void onViewDestroyed();
    }
}
