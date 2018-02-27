package logic;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import XLS.WriteXLS;
import core.Board;
import core.Player;
import core.SquareState;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import logic.ai.evaluation.ConnerclosennessEval;
import logic.ai.evaluation.ConrneroccupancyEval;
import logic.ai.evaluation.Evaluation;
import logic.ai.evaluation.FrontierEval;
import logic.ai.evaluation.MobilityEval;
import logic.ai.evaluation.PositionEval;
import logic.ai.evaluation.ScoreCornerWeightEval;
import logic.ai.evaluation.ScoreDiffEval;
import logic.ai.evaluation.ScoreEval;
import logic.ai.searchers.AbstractSearcher;
import logic.ai.searchers.NegaMax;
import logic.ai.searchers.SearchResult;

/**
 * Controller is the basic coordinator and communication means
 * from the game abstraction to the model manipulation.
 * 联系抽象的游戏和操纵模型
 * @author c00kiemon5ter
 */
public final class Controller {
	/**
	 * {@code turn} has two values
	 * <ul>
	 * <li>false : if it the black player plays
	 * <li>true  : if it the white player plays
	 * </ul>
	 */
	private Board board;
	private Player player;
	public static final int DEFAULT_DEPTH = 3;
	private static int depth = DEFAULT_DEPTH;
	/* 0: all good , 1: one cant move , 2: none can move */
	private final short CANMOVE = 0, CANNOTMOVE = 2;
	private short canMove = CANMOVE;
	/**
	 * 构造器
	 * 新建棋盘并初始化棋盘和玩家，默认为黑色
	 */
	private Controller() {
		this.board = new Board();
		init();
	}
	
	public void init() {
		board.init();
		player = Player.BLACK;
		canMove = CANMOVE;
	}

	public Set<Point> markPossibleMoves() {
		Set<Point> moves = board.getPossibleMoves(player);
		board.markPossibleMoves(moves);
		canMove = moves.isEmpty() ? ++canMove : CANMOVE;
		return moves;
	}

	public void unmarkPossibleMoves() {
		board.unmarkPossibleMoves();
	}

	public Set<Point> makeMove(Point move) {
		return board.makeMove(move, player.color());
	}

	private int calcScore(SquareState state) {
		return board.count(state);
	}

	public int getBlackScore() {
		return board.count(SquareState.BLACK);
	}

	public int getWhiteScore() {
		return board.count(SquareState.WHITE);
	}

	public Player getWinner() {
		return getBlackScore() < getWhiteScore() ? Player.WHITE : Player.BLACK;
	}

	public boolean isDraw() {
		return getBlackScore() == getWhiteScore();
	}

	/**
	 * Game stops if <br/>
	 * <ol>
	 * <li> board is full</li>
	 * <li> one's score is 0/zero</li>
	 * <li> none has a valid next move</li>
	 * </ol>
	 *
	 * @return if the game is over
	 */
	public boolean endOfGame() {
		return board.isFull() || checkZeroScore() || canMove == CANNOTMOVE;
	}

	private boolean checkZeroScore() {
		return getBlackScore() == 0 || getWhiteScore() == 0;
	}

	public void changeTurn() {
		player = player.opponent();
	}

	public Player currentPlayer() {
		return player;
	}

	public String boardWithTurn() {
		return board.toStringWithStatsTurn(player);
	}

	public void setDifficulty(DifficultyLevel level) {
		depth = level.level();
	}

	public Point evalMove() {
		int nodenum = 0;
		int nodenumb = 0;
		AbstractSearcher searcher;
		Evaluation evalfunc1;
		Evaluation evalfunc2;
		Evaluation evalfunc3;
		Evaluation evalfunc4;
		Evaluation evalfunc5;
		Evaluation evalfunc6;
		Evaluation evalfunc7;
		searcher = new NegaMax();
		evalfunc1 = new ScoreEval();		
		evalfunc2 = new ScoreDiffEval();
		evalfunc3 = new MobilityEval();
		evalfunc4 = new PositionEval();
		evalfunc5 = new FrontierEval();
		evalfunc6 = new ConnerclosennessEval();
		evalfunc7 = new ConrneroccupancyEval();
		
//		evalfunc = new ScoreCornerWeightEval(15,2,5);
//		return searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, evalfunc).getPoint();
		long begin=System.currentTimeMillis();		
		SearchResult resultp = searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, 5, evalfunc1,nodenum);
		int Nodenump = resultp.getnodenum();
		long timep = System.currentTimeMillis()-begin;
/*		SearchResult result2 = searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, evalfunc2,nodenum);
		SearchResult result3 = searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, evalfunc3,nodenum);
		SearchResult result4 = searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, evalfunc4,nodenum);
		SearchResult result5 = searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, evalfunc5,nodenum);
		SearchResult result6 = searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, evalfunc6,nodenum);
		SearchResult result7 = searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, evalfunc7,nodenum);
*/
		begin=System.currentTimeMillis();
		SearchResult result = searcher.simpleSearch(board, player, 4, evalfunc1,nodenumb);
		int Nodenum = result.getnodenum();
		long time = System.currentTimeMillis()-begin;
		System.out.println("不剪枝搜索节点数： "+Nodenum);
		System.out.println("运行时间："+time);
		WriteXLS(Nodenump,timep,Nodenum,time);
		return resultp.getPoint();
	}


	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
	public void WriteXLS(int nodenum, long time, int nodenump, long timep){
		try {
			Workbook wb = Workbook.getWorkbook(new File("List.xls")); 
			WritableWorkbook book= Workbook.createWorkbook(new File("List.xls"),wb); 
			WritableSheet sheet=book.getSheet(0);            
            //写入内容
            int currentrows = sheet.getRows() ; 
            System.out.println(currentrows);
            sheet.addCell(new Label(0,currentrows,Integer.toString(nodenum)));
            sheet.addCell(new Label(1,currentrows,Long.toString(time)));
            sheet.addCell(new Label(2,currentrows,Integer.toString(nodenump)));
            sheet.addCell(new Label(3,currentrows,Long.toString(timep)));
            //写入数据
            book.write(); 
            //关闭文件
            book.close(); 
		} catch (IOException | WriteException | BiffException e) {
			e.printStackTrace();
		}
		
	}
}

