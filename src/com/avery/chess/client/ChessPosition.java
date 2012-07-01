package com.avery.chess.client;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.avery.chess.shared.Pawn;
import com.avery.chess.shared.Piece;
import com.avery.chess.shared.Position;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Decorates Position so we can get it back out of the panel.
 * 
 * @author Kevin
 *
 */
public class ChessPosition extends SimplePanel {
	
	private Position position;
	
	public ChessPosition (Position p, DragController dragController) {
		super();
		position = p;
		
		int row = p.getY();
		int col = p.getX();
		
		if ((row % 2 == 0 && col % 2 == 1)
				|| (row % 2 == 1 && col % 2 == 0)) {
			addStyleName("black-square");
		} else {
			addStyleName("white-square");
		}
		
		if (p.hasPiece())
			setWidget(createDraggablePiece(p.getPiece(), dragController));
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position p) {
		position = p;
	}
	
	private Widget createDraggablePiece(Piece p, DragController dragController) {
		ChessPiece pieceImg = new ChessPiece(p);
		String color = p.isWhite() ? "white" : "black";

		if (p instanceof Pawn) {
			pieceImg.setUrl("/images/pawn_" + color + ".png");
		}

		dragController.makeDraggable(pieceImg);

		return pieceImg;
	}

}
