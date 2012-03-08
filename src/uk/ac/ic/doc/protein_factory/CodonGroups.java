package uk.ac.ic.doc.protein_factory;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CodonGroups {
	
	public static enum AminoAcid {
    	Phe, Leu, Ile, Met, 
    	Val, Ser, Pro, Thr,
    	Ala, Tyr, His, Gin,
    	Asn, Lys, Asp, Glu,
    	Cys, Trp, Arg, Gly,
    	STOP
    }
    
	private Map<String, AminoAcid> codonsToAminoAcids = new HashMap<String, AminoAcid>(100);
	private Map<AminoAcid, Collection<String>> aminoAcidsToCodons = new EnumMap<AminoAcid, Collection<String>>(AminoAcid.class);
	
	public boolean sameGroup(Codon a, Codon b) {
		AminoAcid aminoAcid = codonsToAminoAcids.get(a.toString());
		Collection<String> group = aminoAcidsToCodons.get(aminoAcid);
		
		return group.contains(b.toString());
	}
	
	private void associate(String codon, AminoAcid aminoAcid) {
		codonsToAminoAcids.put(codon, aminoAcid);
		aminoAcidsToCodons.get(aminoAcid).add(codon);
	}
	
	public CodonGroups() {
		for(AminoAcid aminoAcid : AminoAcid.values()) {
			aminoAcidsToCodons.put(aminoAcid, new LinkedList<String>());
		}
			
		associate("TTT", AminoAcid.Phe); 
		associate("TTC", AminoAcid.Phe); 
		associate("TTA", AminoAcid.Leu); 
		associate("TTG", AminoAcid.Leu); 
		associate("CTT", AminoAcid.Leu); 
		associate("CTC", AminoAcid.Leu); 
		associate("CTA", AminoAcid.Leu); 
		associate("CTG", AminoAcid.Leu); 
		associate("ATT", AminoAcid.Ile); 
		associate("ATC", AminoAcid.Ile); 
		associate("ATA", AminoAcid.Ile); 
		associate("ATG", AminoAcid.Met); 
		associate("GTT", AminoAcid.Val); 
		associate("GTC", AminoAcid.Val); 
		associate("GTA", AminoAcid.Val); 
		associate("GTG", AminoAcid.Val); 
		associate("TCT", AminoAcid.Ser); 
		associate("TCC", AminoAcid.Ser); 
		associate("TCA", AminoAcid.Ser); 
		associate("TCG", AminoAcid.Ser); 
		associate("CCT", AminoAcid.Pro); 
		associate("CCC", AminoAcid.Pro); 
		associate("CCA", AminoAcid.Pro); 
		associate("CCG", AminoAcid.Pro); 
		associate("ACT", AminoAcid.Thr); 
		associate("ACC", AminoAcid.Thr); 
		associate("ACA", AminoAcid.Thr); 
		associate("ACG", AminoAcid.Thr); 
		associate("GCT", AminoAcid.Ala); 
		associate("GCC", AminoAcid.Ala); 
		associate("GCA", AminoAcid.Ala); 
		associate("GCG", AminoAcid.Ala); 
		associate("TAT", AminoAcid.Tyr); 
		associate("TAC", AminoAcid.Tyr); 
		associate("TAA", AminoAcid.STOP); 
		associate("TAG", AminoAcid.STOP); 
		associate("CAT", AminoAcid.His); 
		associate("CAC", AminoAcid.His); 
		associate("CAA", AminoAcid.Gin); 
		associate("CAG", AminoAcid.Gin); 
		associate("AAT", AminoAcid.Asn); 
		associate("AAC", AminoAcid.Asn); 
		associate("AAA", AminoAcid.Lys); 
		associate("AAG", AminoAcid.Lys); 
		associate("GAT", AminoAcid.Asp); 
		associate("GAC", AminoAcid.Asp); 
		associate("GAA", AminoAcid.Glu); 
		associate("GAG", AminoAcid.Glu); 
		associate("TGT", AminoAcid.Cys); 
		associate("TGC", AminoAcid.Cys); 
		associate("TGA", AminoAcid.STOP); 
		associate("TGG", AminoAcid.Trp); 
		associate("CGT", AminoAcid.Arg); 
		associate("CGC", AminoAcid.Arg); 
		associate("CGA", AminoAcid.Arg); 
		associate("CGG", AminoAcid.Arg); 
		associate("AGT", AminoAcid.Ser); 
		associate("AGC", AminoAcid.Ser); 
		associate("AGA", AminoAcid.Arg); 
		associate("AGG", AminoAcid.Arg); 
		associate("GGT", AminoAcid.Gly); 
		associate("GGC", AminoAcid.Gly); 
		associate("GGA", AminoAcid.Gly); 
		associate("GGG", AminoAcid.Gly);
	}
}
