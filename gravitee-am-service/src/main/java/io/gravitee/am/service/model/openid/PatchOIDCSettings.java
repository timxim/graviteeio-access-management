/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.am.service.model.openid;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.gravitee.am.model.oidc.ClientRegistrationSettings;
import io.gravitee.am.model.oidc.OIDCSettings;

import java.util.Optional;

/**
 * @author Alexandre FARIA (contact at alexandrefaria.net)
 * @author GraviteeSource Team
 */
public class PatchOIDCSettings {

    public PatchOIDCSettings() {}

    @JsonProperty("clientRegistrationSettings")
    private Optional<PatchClientRegistrationSettings> clientRegistrationSettings;

    public Optional<PatchClientRegistrationSettings> getClientRegistrationSettings() {
        return clientRegistrationSettings;
    }

    public void setClientRegistrationSettings(Optional<PatchClientRegistrationSettings> clientRegistrationSettings) {
        this.clientRegistrationSettings = clientRegistrationSettings;
    }

    public OIDCSettings patch(OIDCSettings toPatch) {

        //If source may be null, in such case init with default values
        OIDCSettings result=toPatch!=null?toPatch:OIDCSettings.defaultSettings();

        if(getClientRegistrationSettings()!=null) {
            //If present apply settings, else return default settings.
            if(getClientRegistrationSettings().isPresent()) {
                PatchClientRegistrationSettings patcher = getClientRegistrationSettings().get();
                ClientRegistrationSettings source = result.getClientRegistrationSettings();
                result.setClientRegistrationSettings(patcher.patch(source));
            } else {
                result.setClientRegistrationSettings(ClientRegistrationSettings.defaultSettings());
            }
        }

        return result;
    }
}
