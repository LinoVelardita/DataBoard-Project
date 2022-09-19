import java.io.*;
import java.util.Vector;

public class Data<E extends Serializable & Comparable<E>>  implements Data_contract{

    /*
        Overview: Data is a generic object that can 'gets like' from friends
                  ( every friend can like it only one times )

        Typical element: < e, likes, {f_liked_0, ..., f_liked_n} >

        AF(c): < c.elem, c.friends.size(), {friends.get(i) | 0 <= i < friends.size()} >

        RI(c): c.elem != null &
               c.friends != null &
               forall i,j: 0 <= i,j < c.friends.size() & i != j ==> c.friends.get(i) != c.friends.get(j)
     */


    protected E elem;
    protected Vector<String> friends; //friends that have already liked
    /*private ByteArrayOutputStream bos;
    private ObjectOutputStream os;
    private ByteArrayInputStream bais;
    private ObjectInputStream ois;*/

    //@requires: e != null
    //@throws: NullPointerException
    //@effects: create new Data object with c.elem = e & c.friends = {}
    public Data(E e)throws NullPointerException{
        if(e == null) throw new NullPointerException("NullPointerException: Can't create null Data");
        this.elem = e;
        this.friends = new Vector<String>();
    }


    @Override
    public Data deepCopy() throws RuntimeException {

        try{

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);


            oos.writeObject(this);


            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);

            return (Data) ois.readObject();}

            catch(Exception e){
            e.getCause();
            //e.getSuppressed();
            throw new RuntimeException();
            }

    }

    @Override
    public E getDato() {
        return this.elem;
    }

    @Override
    public void addLike(String friend) throws NullPointerException, AlreadyLikedException{
        if(friend == null) throw new NullPointerException("NullPointerException: 'null friend' can't add like");
        if(friends.contains(friend)) throw new AlreadyLikedException("AlreadyLikedException 0.1: This friend has already like");
        friends.addElement(friend);
    }

    @Override
    public int numLike(){
        return this.friends.size();
    }

    @Override
    public Vector<String> getLikeList() {
        return friends;
    }

    @Override
    public int compareTo(Data d)throws NullPointerException{
       /* if(d == null) throw new NullPointerException("NullPointerException: Can't compare null object");
        if( this.numLike() - d.numLike() == 0 ) return 1;
        else return this.numLike() - d.numLike(); */
        //if(d.equals(this)) return 0;
        return ( (this.numLike() - d.numLike()) == 0 ? 1 : this.numLike()-d.numLike() );
    }

    @Override
    public void display(){
        System.out.println(this.toString() + "\n");
    }

    @Override
    public String toString(){
        return  "|| Element " + this.elem + "\n" +
                "|| " + this.numLike() + " likes" + "\n";
    }



    @Override
    public boolean equals(Data d) throws NullPointerException{
        if(d == null) throw new NullPointerException("NullPointerException: Can't compare null Data");
        String s1 = "" + d.getDato();
        String s2 = "" + this.elem;
        if(s1.equals(s2)) return true;
        return false;
    }

}
