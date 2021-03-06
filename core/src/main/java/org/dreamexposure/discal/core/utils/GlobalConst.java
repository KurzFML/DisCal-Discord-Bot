package org.dreamexposure.discal.core.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

import discord4j.common.util.Snowflake;
import discord4j.rest.util.Color;
import okhttp3.MediaType;

public class GlobalConst {
    static {
        String version1;
        String d4jVersion1;

        try {
            final Resource resource = new ClassPathResource("/application.properties");
            final Properties p = PropertiesLoaderUtils.loadProperties(resource);

            version1 = p.getProperty("application.version");
            d4jVersion1 = "Discord4J v" + p.getProperty("library.discord4j.version");
        } catch (final IOException e) {
            version1 = "Unknown";
            d4jVersion1 = "Unknown";
        }
        version = version1;
        d4jVersion = d4jVersion1;
    }

    public static final String version;
    public static final String d4jVersion;

    public static String iconUrl;
    public static final String lineBreak = System.getProperty("line.separator");
    public static final Snowflake novaId = Snowflake.of(130510525770629121L);
    public static final Snowflake xaanitId = Snowflake.of(233611560545812480L);
    public static final Snowflake calId = Snowflake.of(142107863307780097L);
    public static final Snowflake dreamId = Snowflake.of(142107863307780097L);

    public static final Color discalColor = Color.of(56, 138, 237);
    public static final String discalSite = "https://www.discalbot.com";
    public static final String supportInviteLink = "https://discord.gg/2TFqyuy";
    public static final String discalDashboardLink = "https://www.discalbot.com/dashboard";

    public static final String discordApiUrl = "https://discord.com/api/v6";
    public static final String discordCdnUrl = "https://cdn.discordapp.com";

    public static final long oneMinuteMs = 60000;
    public static final long oneHourMs = 3600000;
    public static final long oneDayMs = 86400000;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String NOT_EMPTY = "Not empty"; //Used for returning in mono's that we don't want to be empty.

    //Http status codes we use
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_NOT_ALLOWED = 405;
    public static final int STATUS_AUTHORIZATION_DENIED = 401;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_GONE = 410;
    public static final int STATUS_TEAPOT = 418;
    public static final int STATUS_PRECONDITION_REQUIRED = 428;
    public static final int STATUS_RATE_LIMITED = 429;
    public static final int STATUS_INTERNAL_ERROR = 500;
}