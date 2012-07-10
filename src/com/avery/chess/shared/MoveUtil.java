package com.avery.chess.shared;

public class MoveUtil {
	
	public static void diagonals(Piece piece) {
		for (int i = 0; i < 4; i++) {
			int dx = 0, dy = 0;
			while (true) {
				if (i == 0) {
					// check top left
					dx--;
					dy--;
				}
				else if (i == 1) {
					// check top right
					dx++;
					dy--;					
				}
				else if (i == 2) {
					// check bottom left
					dx--;
					dy++;
				}
				else if (i == 3) {
					// check bottom right
					dx++;
					dy++;
				}
				
				if (!MoveUtil.checkAndAdd(dx, dy, piece))
					break;
			}
		}
	}
	
	public static void straights(Piece piece) {
		for (int i = 0; i < 4; i++) {
			int dx = 0, dy = 0;
			while (true) {
				if (i == 0) {
					// check top
					dy--;
				}
				else if (i == 1) {
					// check bottom
					dy++;					
				}
				else if (i == 2) {
					// check left
					dx--;
				}
				else if (i == 3) {
					// check right
					dx++;
				}
				
				if (!MoveUtil.checkAndAdd(dx, dy, piece))
					break;
			}
		}
	}
	
	/*
	 * Adds positions to the piece's list of validPositions and
	 * adds the listeners even if it the position has a friendly piece.
	 * 
	 * Returns true if postion at +dx,+dy has an empty position.
	 */
	private static boolean checkAndAdd(int dx, int dy, Piece piece) {
		int x = piece.position.getX() + dx;
		int y = piece.position.getY() + dy;
		
		if (piece.board.fits(x, y)) {
			Position pos = piece.board.getPosition(x, y);
			pos.addListener(piece, true);
			
			if (!pos.hasPiece()) {
				piece.getValidPostions().add(pos);
				return true;
			}
			
			if (piece.isOppenentPiece(pos)) 
				piece.getValidPostions().add(pos);
		}
		
		return false;
	}
	
}


