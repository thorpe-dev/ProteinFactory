package uk.ac.ic.doc.protein_factory;

import android.graphics.Canvas;

public class DNANucleotide extends Nucleotide {
    //private final static String TAG = DNANucleotide.class.getSimpleName();
	private Codon codon = null;
	private RNANucleotide attachedTo = null;

	public DNANucleotide(Game g, Codon codon, char type, int i) {
        super(g, type);
        setColour("grey");
        this.y = this.bitmap.getHeight() / 2;
        this.x = this.game.screenWidth() + this.bitmap.getWidth() * i + this.bitmap.getWidth() / 2;
        this.codon = codon;
    }

	protected String partial_bitmap_filename() { return "_backbone_"; }
	
    public void wobbleLeft() { x--; }
    public void computeCodonValidity() {
    	codon.computeValidity();
    }
    
    // Called from RNANucleotide's attach()
    public void attach(RNANucleotide rna) {
    	this.attached = true;
    	this.attachedTo = rna;
    }
    
    public char partnerType() {
    	if(!attached) throw new RuntimeException("This DNA Nucleotide has no partner, so can't determine its type!");
    	else return attachedTo.type(); 
    }
    
    public boolean matchesPartner() {
    	return ((attachedTo != null) && Game.dnaToRNA(this.type) == attachedTo.type());
    }
    
    public void setState(Game.State state) {
    	String colour;
    	
		switch(state) {
		case Good:
			colour = "green";
			break;
		case Acceptable:
			colour = "orange";
			break;
		case Bad:
			colour = "red";
			break;
		default:
			throw new RuntimeException("Unexpected state: " + state);
		}
		
		setColour(colour);
		attachedTo.setColour(colour);
    }
    
}
