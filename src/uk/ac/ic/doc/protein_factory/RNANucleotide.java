package uk.ac.ic.doc.protein_factory;

import java.util.Random;

import android.content.Context;

public class RNANucleotide extends Nucleotide {
    private boolean touched; // if the dna has been touched
    
	public RNANucleotide(Context c, Random gen) {
		super(c, gen);
		// TODO Auto-generated constructor stub
	}

    public boolean isTouched() { return this.touched;}

    public void setTouched(boolean touched) {this.touched = touched;}

    public void actionDown(int eventX, int eventY) {
        if ((eventX >= (x - bitmap.getWidth() / 2 )) && (eventX <= (x + bitmap.getHeight() / 2)) &&
                ((eventY >= (y - bitmap.getWidth() / 2 )) && (eventY <= (y + bitmap.getHeight() / 2))))
            setTouched(true);
        else
            setTouched(false);
    }
}
