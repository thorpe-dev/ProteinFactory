package uk.ac.ic.doc.protein_factory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
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

	    for (int i = 0; i < rnaCount; i++)
	        floatingRNA.add(new RNANucleotide(c, gen));
	    
	    for (int i = 0; i < displayWidth() / 50; i++)
	        backboneDNA.add(new DNANucleotide(c,gen,i));
    }
    
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
       		rna.wobble(gen);
            if (rna.getX() < 0) // TODO: Add offset so the whole image must be off the screen before we kill it
              	rnaToKill.add(rna);
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
    
    public void touch(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
        	RNANucleotide closest = getClosest(e);
        	if(closest != null)
        		closest.setTouched(true);
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
        	for(RNANucleotide rna : floatingRNA) {
        		if(rna.touched())
        			rna.move((int)e.getX(),(int)e.getY());
        	}
        }
        else if (e.getAction() == MotionEvent.ACTION_UP) {
        	for(RNANucleotide rna : floatingRNA) {
        		rna.setTouched(false);
        	}        	
        }
    }
    
    public RNANucleotide getClosest (MotionEvent e)
    {
        RNANucleotide closest_rna = null;
        int closest_dist = (35*35)*2; // Don't return anything further than 35 pixels away 
        							  // Also, magic numbers yay 
        int this_dist;
        for (RNANucleotide rna : floatingRNA)
        {
            this_dist = rna.sqDist((int)e.getX(), (int)e.getY());
            if (this_dist < closest_dist)
            {
                closest_dist = this_dist;
                closest_rna = rna;
            }
        }

        return closest_rna;
    }
}
