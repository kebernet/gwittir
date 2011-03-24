/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.client.ui.media;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

/**
 *
 * @author robert.cooper
 */
public class Video extends Media{

    @Override
    protected Element createElement() {
        return DOM.createElement("video");
    }

}
