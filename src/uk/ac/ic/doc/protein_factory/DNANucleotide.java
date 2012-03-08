package uk.ac.ic.doc.protein_factory;

public class DNANucleotide extends Nucleotide {
    //private final static String TAG = DNANucleotide.class.getSimpleName();

	public DNANucleotide(Game g, char type, int i) {
        super(g, type);
        setBitmap("grey");
        this.y = this.bitmap.getHeight() / 2;
        this.x = this.bitmap.getWidth() * i + this.bitmap.getWidth() / 2;
    }

	protected String partial_bitmap_filename() { return "_backbone_"; }
	
    public void wobbleLeft() { x--; }
}
