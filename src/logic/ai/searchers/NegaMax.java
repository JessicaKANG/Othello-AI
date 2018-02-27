package logic.ai.searchers;

import java.awt.Point;
import java.util.Set;

import core.Board;
import core.Player;
import logic.MoveExplorer;
import logic.ai.evaluation.Evaluation;

/**
 * <p>Negamax search is a slightly variant formulation of minimax search
 * that relies on the zero-sum property of a two-player game.</p>
 * <p>By definition the value of a position to player A in such a game is
 * the negation of the value to player B. Thus, the player on move looks
 * for a move that maximizes the negation of the value of the position
 * resulting from the move: this successor position must by definition
 * have been valued by the opponent. </p>
 * <p>The reasoning of the previous sentence works regardless of whether
 * A or B is on move. This means that a single computation can be used to
 * value all positions</p>
 * <p>This is a coding simplification over minimax, which requires that A
 * select the move with the maximum-valued successor while B selects the
 * move with the minimum-valued successor.</p>
 * 
 * @author c00kiemon5ter
 */
public class NegaMax extends AbstractSearcher implements Searcher, SimpleSearcher {
	private int Nodenum = 0;
	private int Nodenumb = 0;
	@Override
	public SearchResult search(final Board board, final Player player, int alpha, int beta, final int depth, final Evaluation evfunction,int nodenum) {
		if (depth <= 0 || isEndState(board,player)) {/*一条分支递归结束条件*/
			Nodenum++;
			return new SearchResult(null, evfunction.evaluate(board, player),Nodenum);
		} else { /* there's more to check */
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			Nodenum++;
			SearchResult best = new SearchResult(null, alpha,Nodenum);
			if (possibleMoves.isEmpty()) { /* turn is lost - check next player */
				possibleMoves = MoveExplorer.explore(board, player.opponent().color());
				if (possibleMoves.isEmpty()) { /* end of game - is there a winner ? */
					switch (Integer.signum(board.count(player.color()) - board.count(player.opponent().color()))) {
						case -1:
							best = new SearchResult(null, Integer.MIN_VALUE,Nodenum);
							break;
						case 0:
							best = new SearchResult(null, 0,Nodenum);
							break;
						case 1:
							best = new SearchResult(null, Integer.MAX_VALUE,Nodenum);
							break;
					}
				} else { /* game continues - no moves to check */
					best = search(board, player.opponent(), -beta, -alpha, depth - 1, evfunction,Nodenum).negated();
				}
			} else { /* check the score of each move */
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = board.clone();
					subBoard.makeMove(nextPossibleMove, player.color());
					int score = search(subBoard, player.opponent(), -beta, -alpha, depth - 1, evfunction,Nodenum).negated().getScore();
					if (alpha < score) {
						alpha = score;
						best = new SearchResult(nextPossibleMove, score,Nodenum);
					}
					/* Alpha Beta Pruning */
					if (alpha >= beta) {
						return best;
					}
				}
			}
			return best;
		}
	}

	@Override
	public SearchResult simpleSearch(Board board, Player player, int depth, Evaluation evfunction,int nodenumb) {
		if (depth <= 0 || isEndState(board,player)) {
			Nodenumb++;
			return new SearchResult(null, evfunction.evaluate(board, player),Nodenumb);
		} else { /* there's more to check */
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());//获得所有可落子位置
			Nodenumb++;
			SearchResult best = new SearchResult(null, Integer.MIN_VALUE,Nodenumb);
			if (possibleMoves.isEmpty()) { /* turn is lost - check next player */
				possibleMoves = MoveExplorer.explore(board, player.opponent().color());
				if (possibleMoves.isEmpty()) { /* end of game - is there a winner ? */
					switch (Integer.signum(board.count(player.color()) - board.count(player.opponent().color()))) {
						case -1:
							best = new SearchResult(null, Integer.MIN_VALUE,Nodenumb);
							break;
						case 0:
							best = new SearchResult(null, 0,Nodenumb);
							break;
						case 1:
							best = new SearchResult(null, Integer.MAX_VALUE,Nodenumb);
							break;
					}
				} else { /* game continues - no moves to check */
					best = simpleSearch(board, player.opponent(), depth - 1, evfunction,Nodenumb).negated();
				}
			} else { /* check the score of each move */
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = board.clone();//克隆棋盘执行模拟对弈
					subBoard.makeMove(nextPossibleMove, player.color());
					/**********************递归搜索********************/
					int score = simpleSearch(subBoard, player.opponent(), depth - 1, evfunction,Nodenumb).negated().getScore();
					if (best.getScore() < score) {
						/* store the best score and coresponding move */
						best = new SearchResult(nextPossibleMove, score,Nodenumb);
					}
				}
			}			
			return best;
		}
	}
}

