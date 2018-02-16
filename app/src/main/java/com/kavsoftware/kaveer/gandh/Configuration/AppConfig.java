package com.kavsoftware.kaveer.gandh.Configuration;

/**
 * Created by kaveer on 1/6/2018.
 */

public class AppConfig {
    private String LoginEndPoint = "http://kaveer.ddns.net:9292/v1/account/login" ;
    private String TokenEndPoint = "http://kaveer.ddns.net:9292/v1/access/token" ;
    private String ValidTokenEndPoint = "http://kaveer.ddns.net:9292/v1/access/validtoken" ;
    private String SignUpEndPoint = "http://kaveer.ddns.net:9292/v1/account/signup" ;

    public String getLoginEndPoint() {
        return LoginEndPoint;
    }

    public String getSignUpEndPoint() {
        return SignUpEndPoint;
    }

    public String getTokenEndPoint() {
        return TokenEndPoint;
    }

    public String getValidTokenEndPoint() {
        return ValidTokenEndPoint;
    }
}
