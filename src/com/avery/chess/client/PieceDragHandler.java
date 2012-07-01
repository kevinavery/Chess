package com.avery.chess.client;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class PieceDragHandler implements DragHandler {

	@Override
	public void onDragEnd(DragEndEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDragStart(DragStartEvent event) {
		//String log = ((Image)event.getSource()).toString();
		//Label label  = new Label(log);
		//RootPanel.get("logs").add(label);
	}

	@Override
	public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreviewDragStart(DragStartEvent event)
			throws VetoDragException {
		// TODO Auto-generated method stub
		
	}

}
