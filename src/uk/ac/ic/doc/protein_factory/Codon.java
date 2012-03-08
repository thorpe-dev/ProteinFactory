package uk.ac.ic.doc.protein_factory;


/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 08/03/12
 * Time: 09:25
 * To change this template use File | Settings | File Templates.
 */
public class Codon
{
    private Nucleotide[] nucleotides = new Nucleotide[3];

    public Codon(Game g,String s)
    {
        assert (s.length() == 3);
        for (int i = 0; i < s.length(); i++)
        {
            nucleotides[i] = new DNANucleotide(g,s.charAt(i));
        }

    }
}
