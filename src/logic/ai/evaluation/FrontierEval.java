package logic.ai.evaluation;

import core.Board;
import core.Player;
import logic.FrontierExplorer;

public class FrontierEval implements Evaluation {

	@Override
	public int evaluate(final Board board, final Player player) {
		int frontier = 0;//评估特性5
		int myfrontier = 0;
		int opfrontier = 0;
		myfrontier = FrontierExplorer.explore(board,player.color());
		opfrontier = FrontierExplorer.explore(board, player.opponent().color());
		if(myfrontier>opfrontier){
			frontier = 100*myfrontier/(myfrontier+opfrontier);
		}else if(myfrontier<opfrontier){
			frontier = (-100)*opfrontier/(myfrontier+opfrontier);
		}else{
			frontier =0;
		} 
		return frontier;
	}
}
