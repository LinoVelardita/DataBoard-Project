import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

public interface Data_contract<E extends Serializable> extends Serializable {

    //create a deep copy of this using interface Serializable
    //@requires: Data is Serializable
    //@throws: RuntimeException (uncheked) if Serialization fails
    //@effects: create a deepcopy of this
    public Data deepCopy() throws RuntimeException;

    //returns the element
    //@effects: returns this.elem
    public E getDato();

    //friend 'put likes' to the elem, if he not already done
    //@requires: friend != null &
    //           0 <= i < friends.size() ==> friends.get(i) != friend
    //@throws: NullPointerException (unchecked) if friend == null
    //         AlreadyLikedException (checked) if friend is in list
    //@modifies: this
    //@effects: this_pre = < e, likes, {f_liked_0, ..., f_liked_n} >
    //          this_post = < e, likes++, {f_liked_0, ..., f_liked_n, friend} >
    //          this_pre --> this_post
    public void addLike(String friend) throws NullPointerException, AlreadyLikedException;

    //returns the number of likes that get the elem
    //@effects: returns friends.size() ( > 0)
    public int numLike();

    //returns the List of Friend that put Like
    //@effects: return friends
    public Vector<String> getLikeList();

    //compare the number of like of this and Data d
    //@requires: d != null
    //@throws: NullPointerException (unchecked) if d == null
    //@effects: returns a int:
    //          positive if this.likes > d.likes
    //          negative if this.likes < d.likes
    //          0 if this.likes == d.likes
    public int compareTo(Data d)throws NullPointerException;

    //display the data information
    //@effects: print the result of method toString
    public void display();

    //collect the information about Data in a String
    //@effects: return a String s in this format:
    //          Element "data"
    //          number of like
    //          List of "liker"
    public String toString();



    //return true if this & Data d have the same value, else false
    //@requires: d != null
    //@throws: NullPointerException (unchecked) if d == null
    //@effects: if d.getDato() == this.elem return true, else return false
    public boolean equals(Data d)throws NullPointerException;


}
