package com.have.fun.eskimoboy.core.services.fvlocity.token.impl;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.have.fun.eskimoboy.core.services.fvlocity.token.FakeVlocityTokenService;
import com.have.fun.eskimoboy.core.services.http.HttpClientService;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.have.fun.eskimoboy.core.services.fvlocity.token.impl.FakeVlocityTokenServiceImpl.FakeVlocityTokenServiceDesignate;

import java.io.IOException;
import java.util.List;

@Component(service = FakeVlocityTokenService.class, configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true)
@Designate(ocd = FakeVlocityTokenServiceDesignate.class)
public class FakeVlocityTokenServiceImpl implements FakeVlocityTokenService {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";

    @Reference
    private HttpClientService httpClientService;

    private String tokenUrl;
    private String username;
    private String password;
    private String grantType;
    private String clientId;
    private String clientSecret;

    @Activate
    @Modified
    protected void init(FakeVlocityTokenServiceDesignate designate) {
        tokenUrl = designate.url() + designate.token__path();
        username = designate.username();
        password = designate.password();
        grantType = designate.grant__type();
        clientId = designate.client__id();
        clientSecret = designate.client__secret();
    }

    @Override
    public JsonObject generateToken() {
        try {
            HttpPost httpPost = new HttpPost(tokenUrl);
            List<BasicNameValuePair> urlParameters = ImmutableList.of(
                    new BasicNameValuePair(USERNAME, username),
                    new BasicNameValuePair(PASSWORD, password),
                    new BasicNameValuePair(GRANT_TYPE, grantType),
                    new BasicNameValuePair(CLIENT_ID, clientId),
                    new BasicNameValuePair(CLIENT_SECRET, clientSecret)
            );
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
            return httpClientService.execute(httpPost);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @ObjectClassDefinition(name = "Token properties")
    @interface FakeVlocityTokenServiceDesignate  {
        @AttributeDefinition(name = "url")
        String url();

        @AttributeDefinition(name = "token path")
        String token__path();

        @AttributeDefinition(name = "user name")
        String username();

        @AttributeDefinition(name = "password")
        String password();

        @AttributeDefinition(name = "grant type")
        String grant__type();

        @AttributeDefinition(name = "client id")
        String client__id();

        @AttributeDefinition(name ="client secret")
        String client__secret();
    }
}

