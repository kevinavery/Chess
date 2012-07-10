package com.avery.chess.shared;

public class Knight extends Piece {

	public Knight(Board b, Position p, boolean isWhite) {
		super(b, p, isWhite);
	}
	
	@Override
	public void updateValidPositions() {
		validPositions.clear();
		
		for (int dx = -2, dy; dx <= 2; dx+= 1) {
			if (dx < 0) {
				dy = dx + 3;
				checkAndAdd(dx, dy);
				dy = dy * -1;
				checkAndAdd(dx, dy);
			}
			else if (dx > 0) {
				dy = dx - 3;
				checkAndAdd(dx, dy);
				dy = dy * -1;
				checkAndAdd(dx, dy);
			}
		}
	}
	
	private void checkAndAdd(int dx, int dy) {
		int x = position.getX()+dx;
		int y = position.getY()+dy;
		
		if (board.fits(x,y)) {
			Position pos = board.getPosition(x, y);
			pos.addListener(this, true);
			if (!pos.hasPiece() || isOppenentPiece(pos)) {
				validPositions.add(pos);
			}
		}
	}
	

}
