package uk.ac.ic.doc.protein_factory;

public class DNANucleotide extends Nucleotide {

    private boolean snapped = false;

	public DNANucleotide(Game g, char c, int i) {
        super(g, "_backbone_", DNANucleotide.class.getSimpleName());
        this.type = c;
        setBitmap("grey");
        this.y = this.bitmap.getHeight() / 2;
        this.x = this.bitmap.getWidth() * i + this.bitmap.getWidth() / 2;
    }

	
    public void wobbleLeft() { x--; }
    
    public boolean snapped() { return this.snapped; }
}
