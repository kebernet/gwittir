package com.totsp.gwittir.stack;

import com.google.gwt.user.client.rpc.RemoteService;

import java.util.HashMap;

/**
 *
 */
public interface Compressed extends RemoteService {

    public void compressed(HashMap<Character, String> map, String value);
}
