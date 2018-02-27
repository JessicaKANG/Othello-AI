package logic.ai.evaluation;

import core.Board;
import core.Player;

public class MobilityEval implements Evaluation {

	@Override
	public int evaluate(final Board board, final Player player) {
		int mymobility = 0;
		int opmobility = 0;
		int mobility = 0;
		mymobility = board.getPossibleMoves(player).size();
		opmobility = board.getPossibleMoves(player.opponent()).size();
		if(mymobility>opmobility){
			mobility = 100*mymobility/(mymobility+opmobility);
		}else if(mymobility<opmobility){
			mobility = (-100)*opmobility/(mymobility+opmobility);
		}else{
			mobility =0;
		}
		return mobility;
	}
}

