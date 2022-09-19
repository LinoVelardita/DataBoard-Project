import java.util.Vector;

public class Category<E extends Data> implements Category_contract<E>{

    /*  Overview: A category is a collection of Data, associated to a list of friends

        Typical element: Category = < name, {Data_0, ..., Data_n}, A={friend_0, ..., friend_m} >
                         Data_i = < elem, likes, B={like_friend_0, ..., like_friend_n} >
                         B is a subset of A

        AF(c): < c.name, {c.store.get(i) | 0 <= i < c.store.size()}, {c.friends.get(j) | 0 <= j < c.friends.size()} >

        RI(c): c.name != null
            c.store != null & c.store.size() >= 0
            c.friends != null & c.friends.size >= 0
            0 <= i < c.store.size() & 0 <= j < c.store.size() & i != j ==> c.store.get(i) != c.store.get(j)
            0 <= i < c.friends.size() & 0 <= j < c.friends.size() & i != j ==> c.friends.get(i) != c.friends.get(j)

     */


    private String name;
    private Vector<E> store;            //Dati contenuti nella categoria
    private Vector<String> friends;     //username

    //@requires: name != null
    //@throws: NullPointerException (unchecked) if name == null
    //@effects: create new object 'Category' with c.name = name
    //          c.store = {} & c.friends = {}
    public Category(String name)throws NullPointerException{
        if(name == null) throw new NullPointerException("NullPointerException: Can't create null Category");
        this.name = name;
        this.store = new Vector<>();
        this.friends = new Vector<>();
    }


    @Override
    public void addFriend(String friend) throws NullPointerException, NoDuplicateException{
        if(friend == null) throw new NullPointerException("NullPointerException 0.1 : Can't add null friend");
        if(friends.contains(friend)) throw new NoDuplicateException("NoDuplicateException: Friend already exists in category");
        friends.addElement(friend);
    }

    @Override
    public void addData(E e) throws NullPointerException, NoDuplicateException{
        if(e == null) throw new NullPointerException("NullPointerException: Can't add null Data");
        for(E elem : store){
            if(e.equals(elem)) throw new NoDuplicateException("NoDuplicateException: this Data already exists");
        }
        store.addElement(e);
    }

    @Override
    public void removeFriend(String friend) throws NullPointerException, NoElementException{
        if(friend == null) throw new NullPointerException("NullPointerException: Can't remove null friend");
        if(!friends.contains(friend)) throw new NoElementException("NoElementException: Collection doesn't contain friend");
        friends.remove(friend);
    }

    @Override
    public void removeData(E e) throws NullPointerException, NoElementException{
        if(e == null) throw new NullPointerException("NullPointerException: Can't remove null Data from Category");
        boolean verify = false;
        for(E elem : store){
            if(e.equals(elem)) verify = true;
        }
        if(!verify) throw new NoElementException("NoElementException: there isn't this Data element");
        store.remove(e);
    }

    @Override
    public int allLikes() {
        int c = 0;
        for(E e : store){
            c += e.numLike();
        }
        return c;
    }

    @Override
    public Vector<E> getDataList() {
        return this.store;
    }

    @Override
    public Vector<String> getFriendList() {
        return this.friends;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void tapLike(String friend, E e) throws NullPointerException, NoElementException, AlreadyLikedException {

        if(e == null) throw new NullPointerException("NullPointerException: Can't like a null Data");
        if(friend == null) throw new NullPointerException("NullPointerException: can't exists null friend");

        boolean verify = false;
        for(String s : friends){
            if(s.equals(friend)) verify = true;
        }
        if(!verify) throw new NoElementException("NoElementException: There isn't this friend in category");

        verify = false;
        for(E elem : store){
            if(e.equals(elem)) verify = true;
        }
        if(!verify) throw new NoElementException("NoElementException: there isn't this Data element");

        e.addLike(friend);
    }

}
