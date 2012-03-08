package uk.ac.ic.doc.protein_factory;

import java.util.Random;

public class RNANucleotide extends Nucleotide {
    private boolean touched = false;
    private DNANucleotide snappedTo = null;
    
	public RNANucleotide(Game g) {
		this(g, randomType(g.getGen()));
	}
    
    public RNANucleotide(Game g, char type)
    {
        super(g, "_", DNANucleotide.class.getSimpleName());
        this.x = g.displayWidth() - g.getGen().nextInt(2 * g.displayWidth()/3);
        this.y = g.getGen().nextInt(200) + 120;
        this.type = type;
        setBitmap("grey");
    }
	
	public void wobbleLeft() {
		if(snappedTo != null) {
			x--;
		}
		else {
			x += game.gen.nextInt(3) - 2; // Bias to move left
			y += game.gen.nextInt(3) - 1;
		}
	}

	public boolean touched() { return touched; }
	public void setTouched(boolean touched) { this.touched = touched; }
	
	public void snap(DNANucleotide dna)
    {
		this.snappedTo = dna;
		
		Game.State match = game.match(dna, this);
		
		switch(match) {
		case Good:
			setBitmap("green");
			// Increment scoretype
		}
		
		// Set correct bitmap
		// Set DNAs correct bitmap
		// Set DNA as snappedTo
		// Update score
	}
	
    private static char randomType(Random gen)
    {
        switch (gen.nextInt(4))
        {
        case 0:
            return 'a';
        case 1:
            return 'c';
        case 2:
            return 'g';
        default:
            return 'u';
        }
    }
}
