package io.intrepid.russell.multioskeleton.settings;

import apple.foundation.NSUserDefaults;

public class NSUserDefaultsManager implements UserSettings {

    private static final String LAST_IP = "last_ip";

    private static NSUserDefaultsManager instance;

    private final NSUserDefaults preferences;

    public synchronized static UserSettings getInstance() {
        if (instance == null) {
            instance = new NSUserDefaultsManager();
        }
        return instance;
    }

    private NSUserDefaultsManager() {
        this.preferences = NSUserDefaults.standardUserDefaults();
    }


    @Override
    public void setLastIp(String ip) {
        preferences.setObjectForKey(ip, LAST_IP);
    }

    @Override
    public String getLastIp() {
        return preferences.stringForKey(LAST_IP);
    }
}
