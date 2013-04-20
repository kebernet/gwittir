/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.totsp.gwittir.mvc.ui;

import java.util.Collection;
/**
 *
 * @author kebernet
 */
public interface BoundCollectionWidget<T, R> extends BoundWidget<Collection<T>> {


    public Renderer<T, R> getRenderer();

    public void setRenderer(Renderer<T, R> renderer);


}
