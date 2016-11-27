package com.btanabe.busnotifier.model.types;

import lombok.Data;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class Agency {
    private String id;
    private String name;
    private String url;
    private String timezone;
    private String lang;
    private String phone;
    private String disclaimer;
    private String email;
    private String fareUrl;
    private boolean privateService;
}
