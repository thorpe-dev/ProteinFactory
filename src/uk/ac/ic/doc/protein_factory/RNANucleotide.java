package uk.ac.ic.doc.protein_factory;

import java.util.Random;

public class RNANucleotide extends Nucleotide {
    //private final static String TAG = RNANucleotide.class.getSimpleName();
    private boolean touched = false;
    private DNANucleotide attachedTo = null;
    
	public RNANucleotide(Game g) {
		this(g, randomType(g.getGen()));
	}
    
    public RNANucleotide(Game g, char type)
    {
        super(g, type);
        this.x = g.getGen().nextInt(g.screenWidth());
        this.y = g.getGen().nextInt(g.screenHeight()-230) + 180;
        setBitmap("grey");
    }
    
    protected String partial_bitmap_filename() { return "_"; }
	
	public void wobbleLeft() {
		if(attached) {
			x--;
		}
		else {
			x += game.gen.nextInt(3) - 2; // Bias to move left
			y += game.gen.nextInt(3) - 1;
		}
	}

	public boolean touched() { return touched; }
	public void setTouched(boolean touched) { this.touched = touched; }
	
	public void attach(DNANucleotide dna)
    {
		this.attachedTo = dna;
		this.attached = true;
		
		Game.State match = game.match(dna, this);
		
		switch(match) {
		case Good:
			setBitmap("green");
			dna.setBitmap("green");
			// Increment score? Or are we just keeping track of "lives"?
			break;
		case Acceptable:
			setBitmap("orange");
			dna.setBitmap("orange");
			break;
		case Bad:
			setBitmap("red");
			dna.setBitmap("red");
			// Decrement score/lives
			break;
		default:
			throw new RuntimeException("Unexpected match value for DNA/RNA: " + match);
		}

		dna.setAttached(true);
		
		// Update score
	}
	
    private static char randomType(Random gen)
    {
        switch (gen.nextInt(4))
        {
        case 0:
            return 'A';
        case 1:
            return 'C';
        case 2:
            return 'G';
        default:
            return 'U';
        }
    }
}
