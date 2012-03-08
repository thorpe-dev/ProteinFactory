package uk.ac.ic.doc.protein_factory;


import java.util.LinkedList;

class Codon
{
    public LinkedList<DNANucleotide> getNucleotides() { return nucleotides; }
    private LinkedList<DNANucleotide> nucleotides = new LinkedList<DNANucleotide>();
    private Game game;

    public Codon(Game g,String s, int pos)
    {
    	this.game = g;
        assert (s.length() == 3);
        for (int i = 0; i < s.length(); i++)
        {
            nucleotides.add(new DNANucleotide(g, this, s.charAt(i), pos + i));
        }

    }
    
    public String toString() {
    	String ret = "";
    	for(DNANucleotide nuc : nucleotides) {
    		ret += nuc.type();
    	}
    	return ret;
    }
    
    private String rnaString() {
    	String ret = "";
    	for(DNANucleotide nuc : nucleotides) {
    		ret += nuc.partnerType();
    	}
    	return ret;
    }    
    
    public boolean exactMatch() {
    	for(DNANucleotide dna : nucleotides) {
    		if(!dna.matchesPartner())
    			return false;
    	}
    	return true;
    }
    
    public boolean computeValidity() {
    	Game.State state;
    	
    	for(DNANucleotide dna : nucleotides) {
    		if(!dna.attached())
    			return false;
    	}
    	
    	if(this.exactMatch())
    		state = Game.State.Good;
    	else if(Game.codonGroups.sameGroup(Game.dnaToRNA(this.toString()), this.rnaString()))
    		state = Game.State.Acceptable;
    	else
    		state = Game.State.Bad;
    	
    	// Update score
    	
		for(DNANucleotide dna : nucleotides) {
			dna.setState(state);
		}
    	
    	return true;
    }
}
