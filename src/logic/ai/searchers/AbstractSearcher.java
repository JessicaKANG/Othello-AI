package logic.ai.searchers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import core.Board;
import core.Player;
import core.SquareState;
import logic.ai.evaluation.Evaluation;

/**
 * Abstract searcher class, providing a random choice "search".
 * Implements basic helper methods like min and max and isEndState
 *
 * @author c00kiemon5ter
 */
public abstract class AbstractSearcher implements Searcher, SimpleSearcher {

	@Override
	public abstract SearchResult search(final Board board, final Player player, int alpha,
					    int beta, final int depth, final Evaluation function,int nodenum);

	@Override
	public abstract SearchResult simpleSearch(final Board board, final Player player,
						  final int depth, final Evaluation function,int nodenumb);

	protected int max(int a, int b) {
		return Math.max(a, b);
	}

	protected int min(int a, int b) {
		return Math.min(a, b);
	}
	/**
	 * 随机选择一步移动
	 * @param board
	 * @param player
	 * @return
	 */
	protected Point randomChoice(Board board, Player player) {
		List<Point> possibleMoves = new ArrayList<Point>(board.getPossibleMoves(player));
		int record = (int) Math.random() * (possibleMoves.size() - 1);
		return possibleMoves.get(record);
	}

	/**
	 * Game stops if <br/>
	 * <ol>
	 * <li> board is full</li>
	 * <li> one's score is 0/zero</li>
	 * <li> none has a valid next move</li>
	 * </ol>
	 *
	 * @param board the board to examine
	 * @return if the game is over
	 */
	protected boolean isEndState(final Board board, final Player player) {
		return board.isFull()
		       || board.count(SquareState.BLACK) == 0
		       || board.count(SquareState.WHITE) == 0
		       || (board.getPossibleMoves(player).isEmpty()&&board.getPossibleMoves(player.opponent()).isEmpty());
	}
}

