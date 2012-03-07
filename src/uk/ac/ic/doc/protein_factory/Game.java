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
	
    private Collection<RNANucleotide> floatingRNA = new LinkedList<RNANucleotide>();
    private Stack<RNANucleotide> unusedRNA = new Stack<RNANucleotide>();
    private List<DNANucleotide> backboneDNA = new LinkedList<DNANucleotide>();
    private static final int rnaCount = 20;
    private static final int TOUCH_ACCURACY = 50; // px
    private static final int SNAP_ACCURACY = 50; // px
    public static final int SNAP_OFFSET = 85; // px
    protected Random gen = new Random();
    private Context c;
    
    public Game(Context c, SurfaceView s) {
    	this.c = c;

        generateGamePieces(displayWidth()/50);
    }
    
    /* Called regularly by main loop */
    public void drawToCanvas(Canvas canvas)
    {
    	canvas.drawColor(Color.rgb(244, 235, 141));
        
        for (DNANucleotide dna : backboneDNA)
            dna.draw(canvas);
        
        for (RNANucleotide rna : floatingRNA)
            rna.draw(canvas);

    }

    /* Called regularly by main loop */
    public void physics()
    {
        Collection<RNANucleotide> rnaToKill = new Stack<RNANucleotide>();
        
        for (RNANucleotide rna : floatingRNA)
        {
        	if(!rna.touched()) {
        		rna.wobbleLeft();
        		if (rna.getX() < 0) // TODO: Add offset so the whole image must be off the screen before we kill it
        			rnaToKill.add(rna);
        	}
        }

        // Clean up RNA that went off the screen
        for (RNANucleotide rna : rnaToKill)
        {
        	unusedRNA.push(rna);
            floatingRNA.remove(rna); // TODO: Improve; this is O(n) for a linked list :(
        }

        for (DNANucleotide dna : backboneDNA)
            dna.wobbleLeft();
    }
    
    /* Can arrive at any time */
    public void touch(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
        	RNANucleotide closest = (RNANucleotide) closestNucleotide((int)e.getX(), (int)e.getY(), TOUCH_ACCURACY, floatingRNA);
        	if(closest != null)
        		closest.setTouched(true);
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
        	for(RNANucleotide rna : floatingRNA) {
        		if(rna.touched()) {
        			rna.move((int)e.getX(),(int)e.getY());
        			attemptSnapToBackBone(rna);
        		}
        	}
        }
        else if (e.getAction() == MotionEvent.ACTION_UP) {
        	for(RNANucleotide rna : floatingRNA) {
        		if(rna.touched()) {
        			rna.setTouched(false);
       				attemptAttachToBackBone(rna);
        		}
        	}        	
        }
    }

    private int displayWidth() {
    	return c.getResources().getDisplayMetrics().widthPixels;
    }
    
	private Nucleotide closestNucleotide (int x, int y, int maxdist, Collection<? extends Nucleotide> collection)
    {
        Nucleotide closest_rna = null;
        int closest_dist = (maxdist*maxdist); // Don't return anything further than maxdist pixels away 
        							  // Also, magic numbers yay 
        int this_dist;
        for (Nucleotide rna : collection)
        {
            this_dist = rna.sqDist(x, y);
            if (this_dist < closest_dist)
            {
                closest_dist = this_dist;
                closest_rna = rna;
            }
        }

        return closest_rna;
    }
	
	
    private void attemptSnapToBackBone(RNANucleotide rna) {
    	DNANucleotide nearest = (DNANucleotide) closestNucleotide(rna.getX(), rna.getY()-SNAP_OFFSET, SNAP_ACCURACY, backboneDNA);
    	if(nearest != null && !nearest.snapped())
    		rna.move(nearest.getX()-2, nearest.getY()+SNAP_OFFSET); // getX()-2 is a hack - I think the images aren't aligned properly?
    }
	
    private void attemptAttachToBackBone(RNANucleotide rna) {
    	DNANucleotide nearest = (DNANucleotide) closestNucleotide(rna.getX(), rna.getY()-SNAP_OFFSET, SNAP_ACCURACY, backboneDNA);
    	if(nearest != null)
    		rna.snap(nearest, SNAP_OFFSET);
    }

    protected void generateGamePieces(int numberToGen)
    {
        DNANucleotide dna;
        RNANucleotide rna;
        for (int i = 0; i <numberToGen;i++)
        {
            int count = gen.nextInt(2) + 1;
            dna = new DNANucleotide(c,this,gen, i);
            if (!already_exists(dna.getType()))
            {
                 rna = new RNANucleotide(c,this,gen,dna.getType(),displayWidth());
            }
            else
            {
                rna = new RNANucleotide(c,this, gen,displayWidth());
            }
            backboneDNA.add(dna);
            floatingRNA.add(rna);

            // Add an extra bit of RNA
            floatingRNA.add(new RNANucleotide(c,this,gen,dna.getType(),displayWidth()));
        }
    }

    //TODO make this return an actual boolean, based on whether there is that type already in the floatingRNA
    protected boolean already_exists(char c)
    {
        char rna_type;
        switch (c)
        {
            case 'a':
                rna_type = 'a';
                break;
            case 'c':
                rna_type = 'a';
                break;
            case 'g':
                rna_type = 'a';
                break;
            default:
                rna_type = 'a';
                break;
        }
        for (RNANucleotide rna : floatingRNA)
        {
            if (rna.getType() == rna_type)
                return true;
        }
        return false;
    }
}
