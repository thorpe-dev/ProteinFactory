package uk.ac.ic.doc.protein_factory;

import java.util.*;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;

public class Game {

    private final Collection<RNANucleotide> floatingRNA = Collections.synchronizedList(new LinkedList<RNANucleotide>());
    private final LinkedList<DNANucleotide> backboneDNA = new LinkedList<DNANucleotide>();
    private static final String DNAInput = "atggccctgtggatgcgcttcctgcccctgctggccctgctcttcctctgggag";
    private final Vector<String> splitInput;

    private static final int TOUCH_ACCURACY = 50; // px
    private static final int SNAP_ACCURACY = 50; // px
    private static final int SNAP_OFFSET = 85; // px

    final Random gen = new Random();
    public static final CodonGroups codonGroups = new CodonGroups(); 
    private final Context c;

    private final Paint paint = new Paint();
    private final Bitmap background;

    public Score score = new Score();

    public Random getGen() { return gen; }
    public Resources getResources() { return c.getResources(); }


    public static enum State { Good, Acceptable, Bad }

    private static final String TAG = Game.class.getSimpleName();

    public Game(Context c) {
        this.c = c;

        assert(DNAInput.length()%3==0);
        
        paint.setColor(Color.BLACK);
        paint.setTextSize(64);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_texture);

        splitInput = split();
        generateGamePieces();
    }

    /* Called regularly by main loop */
    public void drawToCanvas(Canvas canvas)
    {
        canvas.drawBitmap(background, 0, 0, paint);

		synchronized(floatingRNA) {
        	for (RNANucleotide rna : floatingRNA)
        	    rna.draw(canvas);
		}

        for (DNANucleotide dna : backboneDNA)
            if (dna.getX() - 50 < screenWidth())
                dna.draw(canvas);

        canvas.drawText("Score: "+score.score(), screenWidth() - 270, screenHeight() - 10, paint);
        canvas.drawText("Lives: " + score.livesLeft(), 10, screenHeight()-10, paint);

    }

    /* Called regularly by main loop */
    public void physics()
    {
        RNANucleotide rna;
        synchronized(floatingRNA) {
	        for (Iterator<RNANucleotide> i = floatingRNA.iterator(); i.hasNext();)
	        {
	            rna = i.next();
	            if(!rna.touched()) {
	                rna.wobbleLeft();
	                if (rna.getX() + (rna.getWidth() / 2) < 0)
	                {
	                	if(rna.attached()) {
	                		i.remove();
	                	}
	                	else
	                		rna.setX(screenWidth() + rna.getWidth()/2);
	                }
	            }
	        }
        }

        DNANucleotide dna;
        for (Iterator<DNANucleotide> i = backboneDNA.iterator(); i.hasNext();)
        {
            dna = i.next();
            dna.wobbleLeft();
            if (dna.offScreen()) {
            	dna.checkCodonForDeletion();
            	i.remove();
            }
        }
    }

    /* Can arrive at any time */
    public void touch(MotionEvent e) {
    	synchronized(floatingRNA) {
	        if (e.getAction() == MotionEvent.ACTION_DOWN) {
	            RNANucleotide closest = (RNANucleotide) closestNucleotide((int)e.getX(), (int)e.getY(), TOUCH_ACCURACY, floatingRNA);
	            if(closest != null && !closest.attached())
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
	                    attemptSnapToBackBone(rna);
	                    attemptAttachToBackBone(rna);
	                }
	            }
	        }
	    }
    }
    
    // Called once at the end of the game
    public void drawScores(Canvas canvas) {
    	canvas.drawBitmap(background, 0, 0, paint);
    	// green Correct codons
    	// orange Silent mutations
    	// red Missense mutations
    	// Deletions
        canvas.drawText("Score: " + score.score(), screenWidth() - 270, screenHeight() - 10, paint);
        canvas.drawText("Lives: " + score.livesLeft(), 10, screenHeight()-10, paint);
    }

    public int screenWidth() {return c.getResources().getDisplayMetrics().widthPixels; }
    public int screenHeight() { return c.getResources().getDisplayMetrics().heightPixels; }

    
    private Nucleotide closestNucleotide (int x, int y, int maxdist, Collection<? extends Nucleotide> collection)
    {
        Nucleotide closest_rna = null;
        int closest_dist = (maxdist*maxdist); // Don't return anything further than maxdist pixels away 
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
        if(nearest != null && !nearest.attached())
            rna.move(nearest.getX(), nearest.getY() + SNAP_OFFSET);
    }

    private void attemptAttachToBackBone(RNANucleotide rna) {
        DNANucleotide nearest = (DNANucleotide) closestNucleotide(rna.getX(), rna.getY()-SNAP_OFFSET, SNAP_ACCURACY, backboneDNA);
        if(nearest != null && !nearest.attached())
            rna.attach(nearest);
    }

    private Vector<String> split()
    {
        Vector<String> val = new Vector<String>(Game.DNAInput.length()/3);
        for (int i = 0; i < Game.DNAInput.length()/3; i++)
        {
            val.add(i, Game.DNAInput.substring(i * 3, (i * 3) + 3));
        }
        return val;
    }

    void generateGamePieces()
    {
        Codon c;

        c = new Codon(this,"ATG",0);
        backboneDNA.addAll(c.getNucleotides());
        for (int i = 0; i < splitInput.size();i++)
        {
            c = new Codon(this,splitInput.get(i),i*3 + 3);
            backboneDNA.addAll(c.getNucleotides());
        }
        
        int end = gen.nextInt(3);
        String endSequence;
        switch (end)
        {
            case 0:
                endSequence = "TAA";
                break;
            case 1:
                endSequence = "TAG";
                break;
            default:
                endSequence = "TGA";
                break;
        }

        c = new Codon(this,endSequence,splitInput.size()*3+3);

        for (int i=0;i<10;i++)
        {
        	synchronized(floatingRNA) {
	        	// And also some random ones
	            floatingRNA.add(new RNANucleotide(this));
        	}
        }
    }

    public static char dnaToRNA(char c)
    {
        switch (c)
        {
            case 'A':
                return 'U';
            case 'C':
                return 'G';
            case 'G':
                return 'C';
            case 'T':
                return 'A';
            default:
                throw new RuntimeException("Unexpected DNA type: " + c);
        }
    }
    
    public static String dnaToRNA(String codon) {
    	String rnaCodon = "";
    	for(int i=0; i < codon.length(); i++) {
    		rnaCodon += dnaToRNA(codon.charAt(i));
    	}
    	return rnaCodon;
    }
    
    public boolean gameOver() {
    	if(score.livesLeft() <= 0)
    		return true;
    	
    	if(backboneDNA.isEmpty())
    		return true;
    	
    	for(DNANucleotide dna : backboneDNA) {
    		if(!dna.attached())
    			return false;
    	}
    	return true;
    }
}
