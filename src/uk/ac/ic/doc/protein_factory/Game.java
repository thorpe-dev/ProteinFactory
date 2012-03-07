package uk.ac.ic.doc.protein_factory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceView;

public class Game {
	
    private List<RNANucleotide> floatingRNA = new LinkedList<RNANucleotide>();
    private Stack<RNANucleotide> unusedRNA = new Stack<RNANucleotide>();
    private List<DNANucleotide> backboneDNA = new LinkedList<DNANucleotide>();
    private static final int rnaCount = 20;
    protected Random gen = new Random();
    private Context c;
    
    public Game(Context c, SurfaceView s) {
    	this.c = c;

        generateGamePieces(displayWidth()/50);
	    for (int i = 0; i < rnaCount; i++)
	        floatingRNA.add(new RNANucleotide(c, gen));
	    
	    for (int i = 0; i < displayWidth() / 50; i++)
	        backboneDNA.add(new DNANucleotide(c,gen,i));
    }
    
    public List<RNANucleotide> getFloatingRNA() { return this.floatingRNA; }
    private int displayWidth() {
    	return c.getResources().getDisplayMetrics().widthPixels;
    }

    public void drawToCanvas(Canvas canvas)
    {
    	canvas.drawColor(Color.rgb(244, 235, 141));
        
        for (DNANucleotide dna : backboneDNA)
            dna.draw(canvas);
        
        for (RNANucleotide rna : floatingRNA)
            rna.draw(canvas);
    }

    protected void physics()
    {
        Collection<RNANucleotide> rnaToKill = new Stack<RNANucleotide>();

        for (RNANucleotide rna : floatingRNA)
        {
        	if (!rna.isTouched())
            {
        		rna.wobble(gen);
                if (rna.getX() > displayWidth())
                	rnaToKill.add(rna);
                	// TODO: VERIFY THIS WORKS
            }
        }

        // Clean up RNA that went off the screen
        for (RNANucleotide rna : rnaToKill)
        {
        	unusedRNA.push(rna);
            floatingRNA.remove(rna); // TODO: Improve; this is O(n) for a linked list :(
        }

        for (DNANucleotide dna : backboneDNA)
            dna.moveLeft();
    }

    protected void generateGamePieces(int numberToGen)
    {
        DNANucleotide dna;
        RNANucleotide rna;
        for (int i = 0; i <numberToGen;i++)
        {
            int count = gen.nextInt(2) + 1;
            dna = new DNANucleotide(c,gen, i);
            if (!already_exists(dna.getType()))
            {
                 rna = new RNANucleotide(c,gen,dna.getType());
            }
            else
            {
                rna = new RNANucleotide(c,gen);
            }
            backboneDNA.add(dna);
            floatingRNA.add(rna);

            // Add an extra bit of RNA
            floatingRNA.add(new RNANucleotide(c,gen,dna.getType()));
        }
    }

    protected boolean already_exists(char c)
    {
        return false;
    }
}
