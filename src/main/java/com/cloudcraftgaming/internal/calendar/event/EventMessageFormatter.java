package com.cloudcraftgaming.internal.calendar.event;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import javax.annotation.Nullable;

/**
 * Created by Nova Fox on 1/3/2017.
 * Website: www.cloudcraftgaming.com
 * For Project: DisCal
 */
@SuppressWarnings("Duplicates")
public class EventMessageFormatter {
    private static String lineBreak = System.getProperty("line.separator");

    public static String getFormatEventMessage(Event event) {
        return "~-~-~- Event Info ~-~-~-" + lineBreak
                + "Event ID: " + event.getId() + lineBreak + lineBreak
                + "Summary: " + event.getSummary() + lineBreak + lineBreak
                + "Description: " + event.getDescription() + lineBreak + lineBreak
                + "Start Date (yyyy/MM/dd): " + getHumanReadableDate(event) + lineBreak
                + "Start Time (HH:mm): " + getHumanReadableTime(event, true) + lineBreak
                + "End Date (yyyy/MM/dd): " + getHumanReadableDate(event) + lineBreak
                + "End Time (HH:mm): " + getHumanReadableTime(event, false) + lineBreak
                + "TimeZone: " + event.getStart().getTimeZone();
    }

    public static String getFormatEventMessage(PreEvent event) {
        return "~-~-~- Event Info ~-~-~-" + lineBreak
                + "Event ID: null until creation completed" + lineBreak + lineBreak
                + "Summary: " + event.getSummary() + lineBreak + lineBreak
                + "Description: " + event.getDescription() + lineBreak + lineBreak
                + "[REQ] Start Date (yyyy/MM/dd): " + getHumanReadableDate(event.getStartDateTime()) + lineBreak
                + "[REQ] Start Time (HH:mm): " + getHumanReadableTime(event.getStartDateTime()) + lineBreak
                + "[REQ] End Date (yyyy/MM/dd): " + getHumanReadableDate(event.getEndDateTime()) + lineBreak
                + "[REQ] End Time (HH:mm): " + getHumanReadableTime(event.getEndDateTime()) + lineBreak
                + "TimeZone: " + event.getTimeZone();
    }

    private static String getHumanReadableDate(Event event) {
        String[] dateArray = event.getStart().getDateTime().toStringRfc3339().split("-");
        String year = dateArray[0];
        String month = dateArray[1];
        String day = dateArray[2].substring(0, 2);

        return year + "/" + month + "/" + day;
    }

    public static String getHumanReadableDate(@Nullable EventDateTime eventDateTime) {
        if (eventDateTime == null) {
            return "Not Set";
        } else {
            if (eventDateTime.getDateTime() != null) {
                String[] dateArray = eventDateTime.getDateTime().toStringRfc3339().split("-");
                String year = dateArray[0];
                String month = dateArray[1];
                String day = dateArray[2].substring(0, 2);

                return year + "/" + month + "/" + day;
            } else {
                String[] dateArray = eventDateTime.getDate().toStringRfc3339().split("-");
                String year = dateArray[0];
                String month = dateArray[1];
                String day = dateArray[2].substring(0, 2);

                return year + "/" + month + "/" + day;
            }
        }
    }

    private static String getHumanReadableTime(Event event, boolean start) {
        if (start) {
            String[] timeArray = event.getStart().getDateTime().toStringRfc3339().split(":");
            String suffix = "";
            String hour = timeArray[0].substring(11, 13);

            //Convert hour from 24 to 12...
            try {
                Integer hRaw = Integer.valueOf(hour);
                if (hRaw > 12) {
                    hour = String.valueOf(hRaw - 12);
                    suffix = "PM";
                } else {
                    suffix = "AM";
                }
            } catch (NumberFormatException e) {
                //I Dunno... just should catch the error now and not crash anything...
            }

            String minute = timeArray[1];

            return hour + ":" + minute + suffix;
        } else {
            String[] timeArray = event.getEnd().getDateTime().toStringRfc3339().split(":");
            String suffix = "";
            String hour = timeArray[0].substring(11, 13);

            //Convert hour from 24 to 12...
            try {
                Integer hRaw = Integer.valueOf(hour);
                if (hRaw > 12) {
                    hour = String.valueOf(hRaw - 12);
                    suffix = "PM";
                } else {
                    suffix = "AM";
                }
            } catch (NumberFormatException e) {
                //I Dunno... just should catch the error now and not crash anything...
            }

            String minute = timeArray[1];

            return hour + ":" + minute + suffix;
        }
    }

    public static String getHumanReadableTime(@Nullable EventDateTime eventDateTime) {
        if (eventDateTime == null) {
            return "Not Set";
        } else {
            if (eventDateTime.getDateTime() != null) {
                String[] timeArray = eventDateTime.getDateTime().toStringRfc3339().split(":");
                String suffix = "";
                String hour = timeArray[0].substring(11, 13);

                //Convert hour from 24 to 12...
                try {
                    Integer hRaw = Integer.valueOf(hour);
                    if (hRaw > 12) {
                        hour = String.valueOf(hRaw - 12);
                        suffix = "PM";
                    } else {
                        suffix = "AM";
                    }
                } catch (NumberFormatException e) {
                    //I Dunno... just should catch the error now and not crash anything...
                }

                String minute = timeArray[1];

                return hour + ":" + minute + suffix;
            } else {
                String[] timeArray = eventDateTime.getDate().toStringRfc3339().split(":");
                String suffix = "";
                String hour = timeArray[0].substring(11, 13);

                //Convert hour from 24 to 12...
                try {
                    Integer hRaw = Integer.valueOf(hour);
                    if (hRaw > 12) {
                        hour = String.valueOf(hRaw - 12);
                        suffix = "PM";
                    } else {
                        suffix = "AM";
                    }
                } catch (NumberFormatException e) {
                    //I Dunno... just should catch the error now and not crash anything...
                }

                String minute = timeArray[1];

                return hour + ":" + minute + suffix;
            }
        }
    }
}