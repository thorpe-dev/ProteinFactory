package uk.ac.ic.doc.protein_factory;


import java.util.Vector;

public class Codon
{
    private Vector<Nucleotide> nucleotides = new Vector<Nucleotide>(3);

    public Codon(Game g,String s)
    {
        assert (s.length() == 3);
        for (int i = 0; i < s.length(); i++)
        {
            nucleotides.add(i,new DNANucleotide(g,s.charAt(i)));
        }

    }
}
