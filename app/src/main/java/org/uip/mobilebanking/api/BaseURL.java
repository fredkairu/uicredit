package org.uip.mobilebanking.api;

/**
 * @author Vishwajeet
 * @since 09/06/16
 */

public class BaseURL {

    public static final String API_ENDPOINT = "techsavanna.net:8444";
    public static final String API_PATH = "/uip/api/v1/self/";
    public static final String PROTOCOL_HTTPS = "https://" ;
    public static final String MY_TIME = "status";
    public static final String LIMIT_API = "https://techsavanna.net:8181/mifos/pull_loanlimit.php";
    public static final String TITLE_API = "http://techsavanna.technology/uipcrm/api/?phone_no=";
    public static final String NOTIFICATION_URL = "https://techsavanna.net:8181/mifos/notify.php";
    public static final String CRB_URL = "http://techsavanna.net:7000/call";

    private String url;

    public String getUrl() {
        if (url == null) {
            return PROTOCOL_HTTPS + API_ENDPOINT + API_PATH;
        }
        return url;
    }

    public String getDefaultBaseUrl() {
        return PROTOCOL_HTTPS + API_ENDPOINT;
    }

    public String getUrl(String endpoint) {
        return endpoint + API_PATH;
    }
}
