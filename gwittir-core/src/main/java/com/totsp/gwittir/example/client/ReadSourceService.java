package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.rpc.RemoteService;


public interface ReadSourceService extends RemoteService {
    public String getSource(String classname);
}
