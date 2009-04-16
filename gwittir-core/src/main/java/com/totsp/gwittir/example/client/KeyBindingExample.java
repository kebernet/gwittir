package com.totsp.gwittir.example.client;

import com.google.gwt.user.client.Window;
import com.totsp.gwittir.client.action.Action;
import com.totsp.gwittir.client.action.BindingAction;
import com.totsp.gwittir.client.keyboard.KeyBinding;
import com.totsp.gwittir.client.keyboard.KeyBindingEventListener;
import com.totsp.gwittir.client.keyboard.KeyboardController;
import com.totsp.gwittir.client.ui.BoundVerticalPanel;
import com.totsp.gwittir.client.ui.BoundWidget;
import com.totsp.gwittir.client.ui.Button;
import com.totsp.gwittir.client.ui.util.BoundWidgetTypeFactory;

public class KeyBindingExample extends BoundVerticalPanel<Object> {
	
	public KeyBindingExample(){
		super( new BoundWidgetTypeFactory(), null);
		Button keyButton = new Button();
        keyButton.setHTML("<u>S</u>ave");
        keyButton.setKeyBinding(new KeyBinding('S', false, true, false));
        keyButton.setAction(new Action() {
                public void execute(BoundWidget model) {
                    Window.alert("SAVE!");
                }
            });
        add(keyButton);

        final Button suggest = new Button("Suggest");
        suggest.setAction(new BindingAction() {
                public void execute(BoundWidget model) {
                    Window.alert("Suggested");
                }

                public void bind(BoundWidget widget) {
                    KeyboardController.AutoMappedBinding auto = KeyboardController.INSTANCE.findSuggestedMapping(suggest.getText());

                    if (auto != null) {
                        suggest.setHTML(auto.newHtml);
                        suggest.setKeyBinding(auto.binding);
                        auto.binding.addKeyBindingEventListener(new KeyBindingEventListener() {
                                public void onUnbind(KeyBinding binding) {
                                    KeyboardController.AutoMappedBinding auto = KeyboardController.INSTANCE.findSuggestedMapping(suggest.getText());

                                    if (auto != null) {
                                        suggest.setHTML(auto.newHtml);
                                        suggest.setKeyBinding(auto.binding);
                                    } else {
                                        suggest.setValue(suggest.getValue());
                                    }
                                }

                                public void onBind(KeyBinding binding) {
                                }
                            });
                    }
                }

                public void unbind(BoundWidget widget) {
                }

                public void set(BoundWidget widget) {
                }
            });
        add(suggest);
	}

}
