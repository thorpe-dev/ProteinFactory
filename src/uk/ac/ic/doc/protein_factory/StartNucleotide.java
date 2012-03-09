package uk.ac.ic.doc.protein_factory;

import android.util.Log;

import java.security.PublicKey;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 09/03/12
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class StartNucleotide extends Nucleotide {

    private final static String TAG = StartNucleotide.class.getSimpleName();
    private boolean start;
    private boolean noLongerRender = false;

    public boolean isNoLongerRender() {
        return noLongerRender;
    }

    public void setNoLongerRender(boolean noLongerRender) {
        this.noLongerRender = noLongerRender;
    }

    public StartNucleotide(Game g,boolean start)
    {
        super(g,'q');
        this.start=start;
        setColour(getTerminal());

        if (start)
        Log.d(TAG,"starting start nucleotide");
        else
            Log.d(TAG,"starting end nucleotide");
    }

    public void wobbleLeft()
    {
        x--;
    }

    protected String partial_bitmap_filename()
    {
            return "_backbone_";
    }
    
    private String getTerminal()
    {
        if (start)
            return "start";
        else
            return "end";
    }
}
