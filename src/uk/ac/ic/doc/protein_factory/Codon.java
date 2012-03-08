package uk.ac.ic.doc.protein_factory;


import java.util.LinkedList;

class Codon
{
    public LinkedList<DNANucleotide> getNucleotides() { return nucleotides; }
    private LinkedList<DNANucleotide> nucleotides = new LinkedList<DNANucleotide>();

    public Codon(Game g,String s, int pos)
    {
        assert (s.length() == 3);
        for (int i = 0; i < s.length(); i++)
        {
            nucleotides.add(new DNANucleotide(g,s.charAt(i),pos + i));
        }

    }
    
    public String toString() {
    	String ret = "";
    	for(DNANucleotide nuc : nucleotides) {
    		ret += nuc.type();
    	}
    	return ret;
    }
    
    /*private AminoAcid aminoAcid() {
    	
    }*/
}
