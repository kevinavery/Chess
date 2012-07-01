package com.avery.chess.client;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.avery.chess.shared.Board;
import com.avery.chess.shared.Position;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SimplePanel;

public class ChessBoard extends SimplePanel {

	private static final String CSS_CHESS_BOARD = "chess-board";
	
	private PickupDragController dragController;
	
	private List<ChessPosition> chessPositions;

	public ChessBoard() {
		
		addStyleName(CSS_CHESS_BOARD);
		
		AbsolutePanel boundaryPanel = new AbsolutePanel();
		boundaryPanel.setPixelSize(800, 800);
		setWidget(boundaryPanel);

		Grid grid = new Grid(8, 8);
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		boundaryPanel.add(grid);

		dragController = new PickupDragController(boundaryPanel, false);
		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorConstrainedToBoundaryPanel(true);
		dragController.addDragHandler(new PieceDragHandler());
		
		chessPositions = new ArrayList<ChessPosition>();

		Board board = new Board();
		for (Position pos : board.getPositions()) {

			ChessPosition chessPosition = new ChessPosition(pos, dragController);
			
			chessPosition.setPixelSize(100, 100);
			int row = pos.getY();
			int col = pos.getX();
			grid.setWidget(row, col, chessPosition);
			// TODO center widget in cell
			// grid.getCellFormatter().setStyleName(row, col, styleName);

			chessPositions.add(chessPosition);

			// Register a drop controller for the panel in the current cell
			ChessPositionDropController dropController = new ChessPositionDropController(chessPosition, chessPositions, board);
			dragController.registerDropController(dropController);
			
		}
	}

	public DragController getDragController() {
		return dragController;
	}

		

}
