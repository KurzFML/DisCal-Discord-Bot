package org.dreamexposure.discal.server.api.endpoints.v2.event;

import org.dreamexposure.discal.core.database.DatabaseManager;
import org.dreamexposure.discal.core.logger.LogFeed;
import org.dreamexposure.discal.core.logger.object.LogObject;
import org.dreamexposure.discal.core.object.GuildSettings;
import org.dreamexposure.discal.core.object.web.AuthenticationState;
import org.dreamexposure.discal.core.utils.EventUtils;
import org.dreamexposure.discal.core.utils.GlobalConst;
import org.dreamexposure.discal.core.utils.JsonUtils;
import org.dreamexposure.discal.server.utils.Authentication;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import discord4j.common.util.Snowflake;

@RestController
@RequestMapping("/v2/events")
public class DeleteEventEndpoint {
    @SuppressWarnings("ConstantConditions")
    @PostMapping(value = "/delete", produces = "application/json")
    public String deleteEvent(final HttpServletRequest request, final HttpServletResponse response, @RequestBody final String rBody) {
        //Authenticate...
        final AuthenticationState authState = Authentication.authenticate(request);
        if (!authState.isSuccess()) {
            response.setStatus(authState.getStatus());
            response.setContentType("application/json");
            return authState.toJson();
        } else if (authState.isReadOnly()) {
            response.setStatus(GlobalConst.STATUS_AUTHORIZATION_DENIED);
            response.setContentType("application/json");
            return JsonUtils.getJsonResponseMessage("Read-Only key not Allowed");
        }

        //Okay, now handle actual request.
        try {
            final JSONObject requestBody = new JSONObject(rBody);

            final String guildId = requestBody.getString("guild_id");
            final int calNumber = requestBody.getInt("calendar_number");
            final String eventId = requestBody.getString("event_id");


            //okay, lets actually delete the event
            final GuildSettings settings = DatabaseManager.getSettings(Snowflake.of(guildId)).block();

            if (EventUtils.eventExists(settings, calNumber, eventId).block()) {
                if (EventUtils.deleteEvent(settings, calNumber, eventId).block()) {
                    response.setContentType("application/json");
                    response.setStatus(GlobalConst.STATUS_SUCCESS);
                    return JsonUtils.getJsonResponseMessage("Event successfully deleted");
                }


                //Something went wrong, but didn't error.... this should never happen...
                response.setContentType("application/json");
                response.setStatus(GlobalConst.STATUS_INTERNAL_ERROR);

                return JsonUtils.getJsonResponseMessage("Internal Server Error");
            } else {
                response.setContentType("application/json");
                response.setStatus(GlobalConst.STATUS_NOT_FOUND);

                return JsonUtils.getJsonResponseMessage("Event Not Found");
            }
        } catch (final JSONException e) {
            e.printStackTrace();

            response.setContentType("application/json");
            response.setStatus(GlobalConst.STATUS_BAD_REQUEST);

            return JsonUtils.getJsonResponseMessage("Bad Request");
        } catch (final Exception e) {
            LogFeed.log(LogObject.forException("[API-v2]", "update event err", e, this.getClass()));

            response.setContentType("application/json");
            response.setStatus(GlobalConst.STATUS_INTERNAL_ERROR);

            return JsonUtils.getJsonResponseMessage("Internal Server Error");
        }
    }
}