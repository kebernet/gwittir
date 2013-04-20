package com.totsp.gwittir.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.totsp.gwittir.mvc.fx.ui.SoftAnimatedHorizontalScrollbar;
import com.totsp.gwittir.mvc.fx.ui.SoftAnimatedScrollbar;
import com.totsp.gwittir.mvc.fx.ui.SoftHorizontalScrollbar;
import com.totsp.gwittir.mvc.fx.ui.SoftScrollArea;
import com.totsp.gwittir.mvc.fx.ui.SoftScrollbar;
import com.totsp.gwittir.mvc.ui.BoundVerticalPanel;
import com.totsp.gwittir.mvc.ui.Button;
import com.totsp.gwittir.mvc.ui.Image;
import com.totsp.gwittir.mvc.ui.util.BoundWidgetTypeFactory;

public class SoftScrollAreaExample extends BoundVerticalPanel<Object>{

	public SoftScrollAreaExample(){
		super(new BoundWidgetTypeFactory(), null);
		DockPanel scroll = new DockPanel();

        final SoftScrollArea ssp = new SoftScrollArea();
        scroll.add(ssp, DockPanel.CENTER);
        ssp.setWidth("500px");
        ssp.setHeight("500px");
        VerticalPanel vp = new VerticalPanel();
        vp.add(new Image(GWT.getModuleBaseURL() + "crested_butte.jpg"));

        final Button visible = new Button("ensure visible test");
        vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        vp.add(visible);

        vp.add(new Image(GWT.getModuleBaseURL() + "crested_butte.jpg"));
        vp.add(new Image(GWT.getModuleBaseURL() + "crested_butte.jpg"));
        vp.add(new Image(GWT.getModuleBaseURL() + "crested_butte.jpg"));

        ssp.setWidget(vp);

        SoftScrollbar bar = new SoftAnimatedScrollbar(ssp);
        //bar.setLowerWidget( new Image( GWT.getModuleBaseURL()+"gwtip.png") );
        //bar.setHigherWidget( new Image( GWT.getModuleBaseURL()+"gwtip.png") );
        //bar.setBarWidget( new Image( GWT.getModuleBaseURL()+"crested_butte.jpg") );
        bar.setHeight("400px");

        VerticalPanel control = new VerticalPanel();
        Button pageDown = new Button("down",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        ssp.pageDownAnimated();
                    }
                });
        pageDown.setSize("50px", "50px");

        Button pageUp = new Button("up",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        ssp.pageUpAnimated();
                    }
                });
        pageUp.setSize("50px", "50px");
        control.add(pageUp);
        control.add(bar);
        control.add(pageDown);
        scroll.add(control, DockPanel.EAST);

        HorizontalPanel hcontrol = new HorizontalPanel();
        SoftHorizontalScrollbar hbar = new SoftAnimatedHorizontalScrollbar(ssp);
        hbar.setWidth("400px");

        //hbar.setLowerWidget( new Image( GWT.getModuleBaseURL()+"gwtip.png") );
        //hbar.setHigherWidget( new Image( GWT.getModuleBaseURL()+"gwtip.png") );
        //hbar.setBarWidget( new Image( GWT.getModuleBaseURL()+"crested_butte.jpg") );
        Button pageLeft = new Button("<<",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        ssp.pageLeftAnimated();
                    }
                });
        pageLeft.setSize("50px", "50px");

        Button pageRight = new Button(">>",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        ssp.pageRightAnimated();
                    }
                });
        pageRight.setSize("50px", "50px");
        hcontrol.add(pageLeft);
        hcontrol.add(hbar);
        hcontrol.add(pageRight);
        scroll.add(hcontrol, DockPanel.SOUTH);

        Button checkEnsure = new Button("checkEnsure",
                new ClickListener() {
                    public void onClick(Widget sender) {
                        ssp.ensureVisibleAnimated(visible);
                    }
                });
        add(scroll);
        add(checkEnsure);
		
	}

}