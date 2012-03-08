package uk.ac.ic.doc.protein_factory;

import java.util.*;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class Game {
	
    private Collection<RNANucleotide> floatingRNA = new LinkedList<RNANucleotide>();
    private Collection<RNANucleotide> unusedRNA = new Stack<RNANucleotide>();
    private List<DNANucleotide> backboneDNA = new LinkedList<DNANucleotide>();
    private static final int TOUCH_ACCURACY = 50; // px
    private static final int SNAP_ACCURACY = 50; // px
    public static final int SNAP_OFFSET = 85; // px
    protected Random gen = new Random();
    private Context c;

    public Random getGen() { return gen; }

    public Resources getResources() { return c.getResources(); }

    private static final String DNA_input = "gctacaatcaaaaaccatcagcaagcaggaaggttattgtttcaacatggccctgtggat";

    private Paint paint = new Paint();
    
    public enum State { Good, Acceptable, Bad, None }
    
    public Game(Context c, SurfaceView s) {
    	this.c = c;

        paint.setColor(Color.BLACK);
        paint.setTextSize(64);

        generateGamePieces(displayWidth() / 50);
    }
    
    /* Called regularly by main loop */
    public void drawToCanvas(Canvas canvas)
    {
    	canvas.drawColor(Color.rgb(244, 235, 141));
        canvas.drawText("Score",displayWidth() - 170, displayHeight() - 20, paint);
        
        for (DNANucleotide dna : backboneDNA)
            dna.draw(canvas);
        
        for (RNANucleotide rna : floatingRNA)
            rna.draw(canvas);

    }

    /* Called regularly by main loop */
    public void physics()
    {
        RNANucleotide rna;
        for (Iterator<RNANucleotide> i = floatingRNA.iterator(); i.hasNext();)
        {
            rna = i.next();
        	if(!rna.touched()) {
        		rna.wobbleLeft();
        		if (rna.getX() + (rna.getWidth() / 2) < 0)
                {
                    unusedRNA.add(rna);
                    i.remove();
                }
        	}
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

    public State match(char dna, char rna) {
    	if(rna=='T') return State.Bad;
    	
    	if((dna=='G' && rna=='C') ||
    	   (dna=='A' && rna=='U') ||
    	   (dna=='C' && rna=='G') ||
           (dna=='T' && rna=='A'))
           return State.Good;

    	// TODO: Needs a lot more work
    	return State.Acceptable;
    }
    
    private int displayWidth() {return c.getResources().getDisplayMetrics().widthPixels; }
    
    private int displayHeight()
    {
        return c.getResources().getDisplayMetrics().heightPixels;
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
    	if(nearest != null && !nearest.snapped())
    		rna.snap(nearest);
    }

    protected void generateGamePieces(int numberToGen)
    {
        DNANucleotide dna;
        RNANucleotide rna;
        for (int i = 0; i <numberToGen;i++)
        {
            dna = new DNANucleotide(this, i);
            if (!already_exists(dna.type()))
            {
                 rna = new RNANucleotide(this,dna.type(),displayWidth());
            }
            else
            {
                rna = new RNANucleotide(this, displayWidth());
            }
            backboneDNA.add(dna);
            floatingRNA.add(rna);

            // Add an extra bit of RNA
            floatingRNA.add(new RNANucleotide(this,dna.type(),displayWidth()));
        }
    }

    //TODO make this return an actual boolean, based on whether there is that type already in the floatingRNA
    protected boolean already_exists(char c)
    {
        char rna_type;
        switch (c)
        {
            case 'A':
                rna_type = 'U';
                break;
            case 'C':
                rna_type = 'G';
                break;
            case 'G':
                rna_type = 'C';
                break;
            default:
                rna_type = 'A';
                break;
        }
        for (RNANucleotide rna : floatingRNA)
        {
            if (rna.type(rna_type))
                return true;
        }
        return false;
    }
}
