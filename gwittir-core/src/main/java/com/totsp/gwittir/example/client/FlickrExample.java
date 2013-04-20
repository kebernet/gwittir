package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.totsp.gwittir.mvc.beans.Binding;
import com.totsp.gwittir.mvc.beans.Converter;
import com.totsp.gwittir.mvc.fx.ui.ReflectedImageGroup;
import com.totsp.gwittir.mvc.fx.ui.SoftScrollArea;
import com.totsp.gwittir.mvc.jsni.flickr.FlickrPhoto;
import com.totsp.gwittir.mvc.jsni.flickr.FlickrSearch;
import com.totsp.gwittir.mvc.ui.AbstractBoundWidget;
import com.totsp.gwittir.mvc.ui.Image;
import com.totsp.gwittir.mvc.ui.Label;
import com.totsp.gwittir.mvc.ui.Renderer;
import com.totsp.gwittir.mvc.ui.TextBox;


public class FlickrExample extends AbstractBoundWidget<Object> {
    public FlickrExample() {
        super();
        VerticalPanel vp = new VerticalPanel();
        this.initWidget(vp);

        TextBox box = new TextBox(false);
        Label title = new Label();

        vp.add(box);
        vp.add(title);

        ReflectedImageGroup group = new ReflectedImageGroup(100, 75, .2, .5);
        FlickrSearch search = new FlickrSearch();
        group.setRenderer(
            new Renderer() {
                public Object render(Object o) {
                    return ((FlickrPhoto) o).getThumbnail();
                }
            });

        Binding images = new Binding(group, "value", search, "photos");

        images.getChildren()
              .add(
            new Binding(
                box, "value",
                new Converter() {
                public Object convert(Object original) {
                    if (original == null) {
                        return original;
                    } else {
                        return original.toString()
                                       .split(",");
                    }
                }
            }, search, "tags",
                new Converter() {
                public Object convert(Object original) {
                    if (original == null) {
                        return original;
                    } else {
                        String[] strings = (String[]) original;
                        StringBuffer ret = new StringBuffer();

                        for (int i = 0; i < strings.length; i++) {
                            ret.append(strings[i]);

                            if (i < (strings.length - 1)) {
                                ret.append(",");
                            }
                        }

                        return ret.toString();
                    }
                }
            }));
        images.getChildren()
              .add(new Binding(title, "value", search, "title"));
        images.setLeft();
        images.bind();

        SoftScrollArea mmsa = new SoftScrollArea();
        mmsa.addMouseListener(mmsa.MOUSE_MOVE_SCROLL_LISTENER);
        mmsa.setHeight("190px");
        mmsa.setWidth("800px");
        mmsa.setWidget(group);
        vp.add(mmsa);

        Image larger = new Image();
        Converter<FlickrPhoto, String> converter = new Converter<FlickrPhoto, String>() {
                public String convert(FlickrPhoto o) {
                    return o.getNormal();
                }
            };

        Binding bigBinding = new Binding(larger, "value", null, group, "selected", converter);
        bigBinding.bind();
        vp.add(larger);
    }

   

    public Object getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
