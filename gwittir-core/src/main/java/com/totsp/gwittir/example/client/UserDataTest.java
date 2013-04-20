/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.Window;
import com.totsp.gwittir.mvc.util.UnavailableException;
import com.totsp.gwittir.mvc.util.userdata.UserData;

/**
 *
 * @author kebernet
 */
public class UserDataTest {

    private UserData local;

    public UserDataTest() throws UnavailableException{
        this.local = UserData.getInstance();
        local.load("gwittir");
        Window.alert( "Local[test]"+ local.get("test"));
    }

    public void saveValues(){
        local.set("test", "testValue!");
        local.save("gwittir");
    }
}
