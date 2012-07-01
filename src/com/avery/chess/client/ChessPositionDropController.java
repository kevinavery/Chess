package com.avery.chess.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.avery.chess.shared.Board;
import com.avery.chess.shared.Piece;
import com.avery.chess.shared.Position;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ChessPositionDropController extends SimpleDropController {

	private final ChessPosition dropTarget;
	private List<ChessPosition> positions;
	private Board board;
	
	private List<Position> validPositions;

	public ChessPositionDropController(ChessPosition dropTarget, List<ChessPosition> positions, Board board) {
		super(dropTarget);
		this.dropTarget = dropTarget;
		this.positions = positions;
		this.board = board;
	}

	@Override
	public void onMove(DragContext context) {
		if (validPositions == null) {
			validPositions = getPieceFromContext(context).getValidPostions(board);
			for (Position pos : validPositions) {
				positions.get(pos.getIndex()).addStyleName("valid-position");
			}
		}
		
		super.onMove(context);
	}

	@Override
	public void onDrop(DragContext context) {
		log("Drop: "+getPieceFromContext(context).toStringWithPosition());
		
		clearValidPositions();
		
		board.move(getPieceFromContext(context).getPosition(), dropTarget.getPosition());
		dropTarget.setWidget(null);
		dropTarget.setWidget(context.draggable);
		
		super.onDrop(context);
	}
	
	private void clearValidPositions() {
		for (Position pos : validPositions) {
			positions.get(pos.getIndex()).removeStyleName("valid-position");
		}
		validPositions = null;
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		log("PreviewDrop: "+dropTarget.getPosition().toStringWithPiece());
		
		Piece p = getPieceFromContext(context);
		if (!validPositions.contains(dropTarget.getPosition())) {
			clearValidPositions();
			throw new VetoDragException();
		}
		
		super.onPreviewDrop(context);
	}

	@Override
	public void onEnter(DragContext context) {
		Piece p = getPieceFromContext(context);
		//log("Enter: "+p.toStringWithPosition());
		
		log("Enter: "+dropTarget.getPosition().toStringWithPiece());
	
		super.onEnter(context);
	}

	@Override
	public void onLeave(DragContext context) {
		Piece p = getPieceFromContext(context);
		//log("Leave: "+p.toStringWithPosition());
		
		log("Leave: "+dropTarget.getPosition().toStringWithPiece());
		
		super.onLeave(context);
	}
	
	private static Piece getPieceFromContext(DragContext context) {
		return ((ChessPiece)context.draggable).getPiece();
	}
	
	private static void log(String s) {
		Label label  = new Label(s);
		RootPanel.get("logs").add(label);
	}
	
}