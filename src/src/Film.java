import java.io.Serializable;
import java.util.Comparator;
import java.util.Vector;

public class Film<E extends Serializable & Comparable<E>> extends Data {

    private int durata;

    public Film(E e, int min) throws NullPointerException, IllegalArgumentException {
        super(e);
        if(min<=0) throw new IllegalArgumentException("IllegalArgumentException: running time of the film must be > 0");
        durata = min;
    }

    public int getRunning(){
        return this.durata;
    }

    public String toString(){
        return  "|| Element: " + this.getDato() + "  Running time: " + durata + "\n" +
                "|| " + this.numLike() + " likes" + "\n";
    }

    public int compareTo(Data f) throws NullPointerException{
        return ( (this.numLike() - f.numLike()) == 0 ? 1 : this.numLike()-f.numLike() );
    }



}
