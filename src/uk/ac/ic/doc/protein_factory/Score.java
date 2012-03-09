package uk.ac.ic.doc.protein_factory;

public class Score {
    private int score = 0;
    private static final int initialLives = 3;
    private int goodCodons = 0;
    private int silentMutations = 0;
    private int missenseMutations = 0;
    private int deletions = 0;
	
	public void codonCompleted(Game.State state) {
		switch(state) {
		case Good: goodCodons++; break;
		case Acceptable: silentMutations++; break;
		case Bad: missenseMutations++; break;
		default: throw new RuntimeException("Unexpected Game.State: " + state);
		}
	}

	public void codonDeleted() {
		deletions++;
	}
	
	public void baseCompleted(Game.State match) {
		if(match == Game.State.Good) score++;
	}

	public void basePairMatch(Game.State match) {
		if(match == Game.State.Good) {
			score++;
		}
	}
	
	public int livesLeft() {
		return (initialLives - (deletions + missenseMutations));
	}
	
	public int score() {
		return score;
	}
	
	public int goodCodons() {
		return goodCodons;
	}
	public int silentMutations() {
		return silentMutations;
	}
}
