/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.totsp.gwittir.client.ui;

import java.util.Collection;


/**
 *
 * @author kebernet
 */
public interface BoundCollectionWidget<T, R> extends BoundWidget<Collection<T>> {
    public void setRenderer(Renderer<T, R> renderer);

    public Renderer<T, R> getRenderer();
}
