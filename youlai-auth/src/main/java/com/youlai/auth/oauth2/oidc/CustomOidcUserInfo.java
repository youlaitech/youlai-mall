package com.youlai.auth.oauth2.oidc;

import cn.hutool.core.lang.Assert;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 自定义 OidcUserInfo
 *
 * @author Ray Hao
 * @since 3.1.0
 */
public class CustomOidcUserInfo extends OidcUserInfo {
    private static final long serialVersionUID = 610L;
    private final Map<String, Object> claims;

    public CustomOidcUserInfo(Map<String, Object> claims) {
        super(claims);
        Assert.notEmpty(claims, "claims cannot be empty");
        this.claims = Collections.unmodifiableMap(new LinkedHashMap(claims));
    }

    public Map<String, Object> getClaims() {
        return this.claims;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            CustomOidcUserInfo that = (CustomOidcUserInfo)obj;
            return this.getClaims().equals(that.getClaims());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.getClaims().hashCode();
    }

    public static Builder customBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private final Map<String, Object> claims = new LinkedHashMap();

        private Builder() {
        }

        public Builder claim(String name, Object value) {
            this.claims.put(name, value);
            return this;
        }

        public Builder claims(Consumer<Map<String, Object>> claimsConsumer) {
            claimsConsumer.accept(this.claims);
            return this;
        }

        public Builder username(String username) {
            return this.claim("username", username);
        }

        public Builder nickname(String nickname) {
            return this.claim("nickname", nickname);
        }

        public Builder description(String description) {
            return this.claim("description", description);
        }

        public Builder status(Integer status) {
            return this.claim("status", status);
        }

        public Builder phoneNumber(String phoneNumber) {
            return this.claim("phone_number", phoneNumber);
        }

        public Builder email(String email) {
            return this.claim("email", email);
        }

        public Builder profile(String profile) {
            return this.claim("profile", profile);
        }

        public Builder address(String address) {
            return this.claim("address", address);
        }

        public CustomOidcUserInfo build() {
            return new CustomOidcUserInfo(this.claims);
        }
    }
}
