package org.dreamexposure.discal.server.api.endpoints.v2.account;

import org.dreamexposure.discal.core.crypto.KeyGenerator;
import org.dreamexposure.discal.core.logger.LogFeed;
import org.dreamexposure.discal.core.logger.object.LogObject;
import org.dreamexposure.discal.core.object.web.AuthenticationState;
import org.dreamexposure.discal.core.utils.GlobalConst;
import org.dreamexposure.discal.core.utils.JsonUtils;
import org.dreamexposure.discal.server.utils.Authentication;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v2/account/key")
public class KeyRequestEndpoint {
    @PostMapping("/readonly/get")
    public String getReadonlyKey(final HttpServletRequest request, final HttpServletResponse response) {
        //Check auth, must be from within DisCal network, as this is generating an API key...
        final AuthenticationState authState = Authentication.authenticate(request);
        if (!authState.isSuccess()) {
            response.setStatus(authState.getStatus());
            response.setContentType("application/json");
            return authState.toJson();
        } else if (!authState.isFromDiscalNetwork()) {
            response.setStatus(GlobalConst.STATUS_AUTHORIZATION_DENIED);
            response.setContentType("application/json");
            return JsonUtils.getJsonResponseMessage("Unauthorized to use this Endpoint.");
        }

        try {
            //Generate readonly API key. This key should only be valid for 1 hour...
            final String key = KeyGenerator.csRandomAlphaNumericString(64);

            //Save key to memory... so it can be used for authentication just like the others
            Authentication.saveReadOnlyKey(key);

            //Return key to web....
            final JSONObject responseBody = new JSONObject();
            responseBody.put("key", key);

            response.setContentType("application/json");
            response.setStatus(GlobalConst.STATUS_SUCCESS);
            return responseBody.toString();
        } catch (final JSONException e) {
            e.printStackTrace();

            response.setContentType("application/json");
            response.setStatus(GlobalConst.STATUS_BAD_REQUEST);
            return JsonUtils.getJsonResponseMessage("Bad Request");
        } catch (final Exception e) {
            LogFeed.log(LogObject
                .forException("[API-v2]", "read-only key req err", e, this.getClass()));

            response.setContentType("application/json");
            response.setStatus(GlobalConst.STATUS_INTERNAL_ERROR);
            return JsonUtils.getJsonResponseMessage("Internal Server Error");
        }
    }
}

