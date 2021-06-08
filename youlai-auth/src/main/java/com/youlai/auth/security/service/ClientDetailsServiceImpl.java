package com.youlai.auth.security.service;
import com.youlai.admin.api.OAuthClientFeignClient;
import com.youlai.admin.pojo.entity.SysOauthClient;
import com.youlai.auth.common.enums.PasswordEncoderTypeEnum;
import com.youlai.common.result.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;


@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private OAuthClientFeignClient oAuthClientFeignClient;

    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId) {
        try {
            Result<SysOauthClient> result = oAuthClientFeignClient.getOAuthClientById(clientId);
            if (Result.success().getCode().equals(result.getCode())) {
                SysOauthClient client = result.getData();
                BaseClientDetails clientDetails = new BaseClientDetails(
                        client.getClientId(),
                        client.getResourceIds(),
                        client.getScope(),
                        client.getAuthorizedGrantTypes(),
                        client.getAuthorities(),
                        client.getWebServerRedirectUri());
                clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
                return clientDetails;
            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (EmptyResultDataAccessException var4) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }
}
