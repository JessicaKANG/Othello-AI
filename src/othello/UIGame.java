package othello;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JRadioButtonMenuItem;

import core.SquareState;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import logic.Controller;
import logic.DifficultyLevel;
import ui.BoardUI;
import ui.ImageComponent;
import ui.SquareImgFactory.SquareType;
import utils.Transform;

/**
 * The UI Game. Draw window and changes listening to events.
 *
 * @author c00kiemon5ter
 */
public class UIGame implements Runnable {

	private Controller controller = Controller.getInstance();
	private BoardUI boardUI;
	private Set<Point> possblMoves;

	public UIGame() {
		this.controller.init();
		initBoardUI();
	}

	private void initBoardUI() {
		boardUI = new BoardUI();
		boardUI.setVisible(true);
		boardUI.getNewGameItem().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boardUI.dispose();
				controller.init();
				initBoardUI();
				run();
			}
		});
		for (final JRadioButtonMenuItem diffbutton : boardUI.getDifficulties()) {
			diffbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (diffbutton.getText().equalsIgnoreCase(DifficultyLevel.EASY.description())) {
						controller.setDifficulty(DifficultyLevel.EASY);
					} else if (diffbutton.getText().equalsIgnoreCase(DifficultyLevel.NORMAL.description())) {
						controller.setDifficulty(DifficultyLevel.NORMAL);
					} else if (diffbutton.getText().equalsIgnoreCase(DifficultyLevel.HARD.description())) {
						controller.setDifficulty(DifficultyLevel.HARD);
					} else if (diffbutton.getText().equalsIgnoreCase(DifficultyLevel.HEROIC.description())) {
						controller.setDifficulty(DifficultyLevel.HEROIC);
					}
				}
			});
		}
	}

	@Override
	public void run() {
		if (controller.endOfGame()) {
			gameEnd();
		} else {
			possblMoves = markPossibleMoves();			
			if (possblMoves.isEmpty()) {
				pass();
				run();
			}else{
				if (controller.currentPlayer() != boardUI.getPlayerSelection()
			    && boardUI.againstRobots()) {
				Point computerMove = controller.evalMove();
				boardUI.unmarkPossibleMoves(possblMoves);			
				makeMove(computerMove);
				afterMove();
			}/*else  {
					boardUI.unmarkPossibleMoves(possblMoves);
					List<Point> possibleMove = new ArrayList();
					Iterator iterator = possblMoves.iterator();
					while(iterator.hasNext())possibleMove.add((Point) iterator.next());
					Random random = new Random();
					Point randomMove = possibleMove.get(random.nextInt(possibleMove.size()));
					makeMove(randomMove);
					afterMove();
				}*/
			}
			
		}
	}

	private Set<Point> markPossibleMoves() {
		Set<Point> moves = controller.markPossibleMoves();
		controller.unmarkPossibleMoves();
		if (!moves.isEmpty()) {
			SquareType color = controller.currentPlayer().color() == SquareState.WHITE
					   ? SquareType.PSSBLWHT : SquareType.PSSBLBLK;
			boardUI.markPossibleMoves(moves, color);
		}
		
		updateListeners();
		return moves;
	}

	private void updateListeners() {
		
					for (ImageComponent imgComp : boardUI.getSquares()) {
						if (imgComp.getMouseListeners().length != 0) {
							continue;
						}
						imgComp.addMouseListener(new MouseAdapter() {
			
							@Override
							public void mouseClicked(MouseEvent evt) {
								clickzWasHappend(evt.getComponent());
							}
						});
					}	
	}

	private void clickzWasHappend(Component imgComp) {
		int index = boardUI.getSquares().indexOf(imgComp);
		Point selectedMove = Transform.indexToPoint(index);
		if (possblMoves.contains(selectedMove)) {
			boardUI.unmarkPossibleMoves(possblMoves);
			makeMove(selectedMove);
			afterMove();
		}
	}

	private void makeMove(Point move) {
		SquareType color = controller.currentPlayer().color() == SquareState.WHITE
				   ? SquareType.WHITE : SquareType.BLACK;
		Set<Point> squaresToChange = controller.makeMove(move);
		boardUI.fill(squaresToChange, color);
		
	}

	private void afterMove() {
		updateStats();
		changeTurn();
		run();
	}

	private void pass() {
		lostTurn();
		updateStats();
	}

	private void lostTurn() {
		boardUI.notifyLostTurn(controller.currentPlayer());
		changeTurn();
	}

	private void changeTurn() {
		controller.changeTurn();
		boardUI.updateTurn(controller.currentPlayer().toString());
	}

	private void updateStats() {
		boardUI.updateScore(controller.getBlackScore(), controller.getWhiteScore());
	}

	private void gameEnd() {
		int score = controller.getWhiteScore()-controller.getBlackScore();
		//WriteXLS(3,score);
		updateStats();
		if (controller.isDraw()) {
			boardUI.declareDraw();
		} else {
			boardUI.declareWinner(controller.getWinner().toString());
		}
	}
	public void WriteXLS(int type,int score){
		try {
			Workbook wb = Workbook.getWorkbook(new File("List.xls")); 
			WritableWorkbook book= Workbook.createWorkbook(new File("List.xls"),wb); 
			WritableSheet sheet=book.getSheet(0);            
            //写入内容
            int currentrows = sheet.getRows() ; 
            if (type==1){
            	sheet.addCell(new Label(1,currentrows,Long.toString(score)));
            }else if(type==2){
            	sheet.addCell(new Label(3,currentrows,Long.toString(score)));
            }else if(type ==3){
            	sheet.addCell(new Label(7,currentrows,Long.toString(score)));
            }
            //写入数据
            book.write(); 
            //关闭文件
            book.close(); 
		} catch (IOException | WriteException | BiffException e) {
			e.printStackTrace();
		}
		
	}
}

