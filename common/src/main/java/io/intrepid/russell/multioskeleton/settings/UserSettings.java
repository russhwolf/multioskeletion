package io.intrepid.russell.multioskeleton.settings;

import io.reactivex.annotations.Nullable;

public interface UserSettings {

    void setLastIp(String ip);

    @Nullable
    String getLastIp();
}
