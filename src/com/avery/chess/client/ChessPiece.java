package com.avery.chess.client;

import com.avery.chess.shared.Piece;
import com.google.gwt.user.client.ui.Image;

/**
 * Decorates Piece so we can get it back out of the Image widget.
 * 
 * @author Kevin
 * 
 */
public class ChessPiece extends Image {

	private Piece piece;

	public ChessPiece(Piece p) {
		super();
		piece = p;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece p) {
		piece = p;
	}

}
