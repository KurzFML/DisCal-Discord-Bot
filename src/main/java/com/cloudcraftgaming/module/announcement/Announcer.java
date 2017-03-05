package com.cloudcraftgaming.module.announcement;

import com.cloudcraftgaming.database.DatabaseManager;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Nova Fox on 1/5/2017.
 * Website: www.cloudcraftgaming.com
 * For Project: DisCal
 */
public class Announcer {
    private static Announcer instance;

    private Timer timer;

    private Announcer() {}

    public static Announcer getAnnouncer() {
        if (instance == null) {
            instance = new Announcer();
        }
        return instance;
    }

    public void init() {
        timer = new Timer();
        timer.schedule(new Announce(), 10 * 1000 * 60, 10 * 1000 * 60);
    }

    public void shutdown() {
        timer.cancel();
    }

    public ArrayList<Announcement> getAnnouncements() {
        return DatabaseManager.getManager().getAnnouncements();
    }

    public Boolean deleteAnnouncement(Announcement announcement) {
        return DatabaseManager.getManager().deleteAnnouncement(announcement.getAnnouncementId().toString());
    }
}