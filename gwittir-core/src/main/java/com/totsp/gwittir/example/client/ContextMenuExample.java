package com.totsp.gwittir.example.client;

import com.totsp.gwittir.client.ui.BoundVerticalPanel;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.ContextMenuPanel;
import com.totsp.gwittir.client.ui.Label;
import com.totsp.gwittir.client.ui.ContextMenuPanel.MenuItem;
import com.totsp.gwittir.client.ui.ContextMenuPanel.SubMenu;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;

public class ContextMenuExample extends BoundVerticalPanel<Object>{

	
	public ContextMenuExample(){
		super(new BoundWidgetTypeFactory(), null);
		
		Label hasContext = new Label("RightClickMe");
                ContextMenuPanel ctx = new ContextMenuPanel(hasContext);
        ctx.addMenuItemWidget(new Button("Item 1"));
        ctx.addMenuItemWidget(new Button("Item 2"));
       
        Button ctxB = new MenuItem("Rick roll");
        ctx = new ContextMenuPanel(ctxB);
        ctx.addMenuItemWidget(new MenuItem("Item 1"));
        ctx.addMenuItemWidget(new MenuItem("Item 2"));

        SubMenu sub = new SubMenu("Sub");
        sub.addMenuItemWidget(new MenuItem("Sub Item 1"));
        sub.addMenuItemWidget(new MenuItem("Sub Item 2"));

        SubMenu sub2 = new SubMenu("Sub2");
        sub2.addMenuItemWidget(new MenuItem("Sub2 Item 1"));
        sub2.addMenuItemWidget(new MenuItem("Sub2 Item 2"));
        sub.addMenuItemWidget(sub2);
        ctx.addMenuItemWidget(sub);
        add(ctx);
        
	}
}
