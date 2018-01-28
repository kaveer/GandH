package com.kavsoftware.kaveer.gandh.Configuration;

/**
 * Created by kaveer on 1/6/2018.
 */

public class AppConfig {
    private String LoginEndPoint = "http://kaveer.ddns.net:9292/v1/account/login" ;
    private String TokenEndPoint = "http://kaveer.ddns.net:9292/v1/access/token" ;

    public String getLoginEndPoint() {
        return LoginEndPoint;
    }

    public String getTokenEndPoint() {
        return TokenEndPoint;
    }
}
