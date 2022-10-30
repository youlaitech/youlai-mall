/**
  OAuth2 数据库
  SQL脚本在线地址: https://github.com/spring-attic/spring-security-oauth/blob/main/spring-security-oauth2/src/test/resources/schema.sql
 */

use oauth2;
create table oauth_client_details (
                                      client_id VARCHAR(256) PRIMARY KEY,
                                      resource_ids VARCHAR(256),
                                      client_secret VARCHAR(256),
                                      scope VARCHAR(256),
                                      authorized_grant_types VARCHAR(256),
                                      web_server_redirect_uri VARCHAR(256),
                                      authorities VARCHAR(256),
                                      access_token_validity INTEGER,
                                      refresh_token_validity INTEGER,
                                      additional_information VARCHAR(4096),
                                      autoapprove VARCHAR(256)
);

create table oauth_client_token (
                                    token_id VARCHAR(256),
                                    token LONGBLOB,
                                    authentication_id VARCHAR(256) PRIMARY KEY,
                                    user_name VARCHAR(256),
                                    client_id VARCHAR(256)
);

create table oauth_access_token (
                                    token_id VARCHAR(256),
                                    token LONGBLOB,
                                    authentication_id VARCHAR(256) PRIMARY KEY,
                                    user_name VARCHAR(256),
                                    client_id VARCHAR(256),
                                    authentication LONGBLOB,
                                    refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
                                     token_id VARCHAR(256),
                                     token LONGBLOB,
                                     authentication LONGBLOB
);

create table oauth_code (
                            code VARCHAR(256), authentication LONGBLOB
);

create table oauth_approvals (
                                 userId VARCHAR(256),
                                 clientId VARCHAR(256),
                                 scope VARCHAR(256),
                                 status VARCHAR(10),
                                 expiresAt TIMESTAMP,
                                 lastModifiedAt TIMESTAMP
);
