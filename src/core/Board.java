package core;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logic.MoveExplorer;

public final class Board implements Cloneable {
	public static final int BOARD_LENGTH = 8;//定义棋盘尺寸，落点
	public static final int BOARD_WIDTH = 8;
	private Map<Point, SquareState> board;
	
	/**
	 * 空白棋盘构造器
	 */
	public Board() {
		//board是一个哈希表，key是落点，value是落点状态；黑白棋中包含8*8个落点
		board = new HashMap<Point, SquareState>(BOARD_LENGTH * BOARD_WIDTH);
		init();
	}
	/**
	 * Deep copy constructor. 
	 * 深度拷贝构造器，拷贝引入的棋盘
	 * @param board
	 */
	private Board(Map<Point, SquareState> board) {
		this.board = new HashMap<Point, SquareState>(board.size());
		for (Point point : board.keySet()) {//循环map的key
			this.board.put(new Point(point), board.get(point));
		}
	}
	/**
	 * 初始化棋盘的方法
	 * 先把每个落点设置为空，再落开盘四子
	 */
	public void init() {
		Point point = new Point();//二维坐标
		for (point.x = 0; point.x < BOARD_LENGTH; point.x++) {
			for (point.y = 0; point.y < BOARD_WIDTH; point.y++) {
				board.put(new Point(point), SquareState.EMPTY);
			}
		}
		board.put(new Point(3, 3), SquareState.WHITE);
		board.put(new Point(3, 4), SquareState.BLACK);
		board.put(new Point(4, 3), SquareState.BLACK);
		board.put(new Point(4, 4), SquareState.WHITE);
	}
	/**
	 * 获取落点状态方法
	 * @param point落点坐标
	 * @return
	 */
	public SquareState getSquareState(Point point) {
		return board.get(point);
	}
	/**
	 * 获取落点坐标集合
	 * @param state落点状态
	 * @return
	 */
	public Set<Point> getSquares(SquareState state) {
		Set<Point> points = new HashSet<Point>();
		for (Point point : board.keySet()) {
			if (board.get(point) == state) {
				points.add(point);
			}
		}
		return points;
	}
	/**
	 * 判断棋盘饱和
	 * @return
	 */
	public boolean isFull() {
		for (Point point : board.keySet()) {
			if (board.get(point) == SquareState.EMPTY) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 数棋子
	 * @param state棋子颜色
	 * @return
	 */
	public int count(SquareState state) {
		int count = 0;
		for (Point point : board.keySet()) {
			if (board.get(point) == state) {
				count++;
			}
		}
		return count;
	}
	/**
	 * 探索可落子位置集合
	 * @param player玩家
	 * @return
	 */
	public Set<Point> getPossibleMoves(Player player) {
		return MoveExplorer.explore(this, player.color());
	}
	/**
	 * 标记可落子位置
	 * @param possibleMoves
	 */
	public void markPossibleMoves(Set<Point> possibleMoves) {
		for (Point point : possibleMoves) {
			board.put(point, SquareState.PSSBL);
		}
	}
	/**
	 * 解除可落子位置标记
	 */
	public void unmarkPossibleMoves() {
		for (Point point : board.keySet()) {
			if (board.get(point) == SquareState.PSSBL) {
				board.put(point, SquareState.EMPTY);
			}
		}
	}
	/**
	 * 标记落点状态
	 * @param points
	 * @param state
	 */
	public void markState(Set<Point> points, SquareState state) {
		for (Point point : points) {
			board.put(point, state);
		}
	}
	/**
	 * 落子
	 * @param move落点
	 * @param state棋子颜色
	 * @return//返回此次落子我方新增的落点集合
	 */
	public Set<Point> makeMove(Point move, SquareState state) {
		board.put(move, state);//把棋子放到棋盘上
		Set<Point> changedSquares = MoveExplorer.squaresToFill(this, move);//获得待翻转坐标集合
		markState(changedSquares, state);//翻转棋子
		changedSquares.add(move);
		return changedSquares;
	}
	/**
	 * 以字符形式打印棋盘
	 */
	@Override
	public String toString() {
		Point point = new Point();
		StringBuilder sb = new StringBuilder();
		sb.append("  A B C D E F G H");
		for (point.x = 0; point.x < BOARD_LENGTH; point.x++) {
			sb.append('\n').append(point.x + 1);
			for (point.y = 0; point.y < BOARD_WIDTH; point.y++) {
				sb.append(' ').append(board.get(point).symbol());
			}
		}
		sb.append('\n');
		return sb.toString();
	}
	/**
	 * 打印棋子数量
	 * @return
	 */
	public String toStringWithStats() {
		StringBuilder sb = new StringBuilder();
		String[] rows = toString().split("\n");//把棋盘按行拆分
		for (int row = 0; row < rows.length; row++) {
			sb.append('\n').append(rows[row]);
			switch (row) {
				case 2:
					sb.append('\t').append(SquareState.BLACK.symbol()).
						append(' ').append(Player.BLACK).
						append(": ").append(count(SquareState.BLACK));
					break;
				case 4:
					sb.append('\t').append(SquareState.WHITE.symbol()).
						append(' ').append(Player.WHITE).
						append(": ").append(count(SquareState.WHITE));
					break;
			}
		}
		sb.append('\n');
		return sb.toString();
	}
	/**
	 * 打印棋子数量和待落子一方
	 * @param player
	 * @return
	 */
	public String toStringWithStatsTurn(Player player) {
		StringBuilder sb = new StringBuilder();
		String[] rows = toString().split("\n");
		for (int row = 0; row < rows.length; row++) {
			sb.append('\n').append(rows[row]);
			switch (row) {
				case 2:
					sb.append('\t').append(SquareState.BLACK.symbol()).
						append(' ').append(Player.BLACK).
						append(": ").append(count(SquareState.BLACK));
					break;
				case 4:
					sb.append('\t').append(SquareState.WHITE.symbol()).
						append(' ').append(Player.WHITE).
						append(": ").append(count(SquareState.WHITE));
					break;
				case 6:
					sb.append('\t').append(player).append("'s turn!");
					break;
			}
		}
		sb.append('\n');
		return sb.toString();
	}

	/**
	 * Deep copy of this board.
	 *
	 * @return
	 */
	@Override
	public Board clone() {
		return new Board(this.board);
	}

}
