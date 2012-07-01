package com.avery.chess.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;


public class ChessUI implements EntryPoint {
	
	public void onModuleLoad() {
		RootPanel mainPanel = RootPanel.get("chessBoard");
		//DOM.setInnerHTML(mainPanel.getElement(), "");
		mainPanel.add(new ChessBoard());
	}
	
}
