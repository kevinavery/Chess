package com.avery.chess.shared;

public class Rook extends Piece {

	public Rook(Board b, Position p, boolean isWhite) {
		super(b, p, isWhite);
	}

	@Override
	public void updateValidPositions() {
		validPositions.clear();
		MoveUtil.straights(this);
	}

}
