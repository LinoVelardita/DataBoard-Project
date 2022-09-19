import java.util.Vector;

public interface Category_contract<E extends Data>{


    //add 'friend' to friends list, if is not already present
    //@requires: friend != null &
    //           0 <= i < friends.size() ==> friends.get(i) != friend
    //@throws: NullPointerException (unchecked) if friend == null
    //         NoDuplicateException (checked) if friend is already in list
    //@modifies: this
    //@effects: this_pre = < name, {Data_0,...,Data_n}, {friend_0,...,friend_m} >
    //          this_post = < name, {Data_0,...,Data_n}, {friend_0,...,friend_m, friend} >
    //          this_pre --> this_post
    public void addFriend(String friend) throws NullPointerException, NoDuplicateException;


    //add a Data element to category, if is not already present
    //@requires: e != null &
    //           0 <= i < store.size() ==> e != store.get(i)
    //@throws: NullPointerException (unchecked) if store == null
    //         NoDuplicateException (checked) if e is already in stores
    //@modifies: this
    //@effects: this_pre = < name, {Data_0,...,Data_n}, {friend_0,...,friend_m} >
    //          this_post = < name, {Data_0,...,Data_n, e}, {friend_0,...,friend_m} >
    //          this_pre --> this_post
    public void addData(E e)throws NullPointerException, NoDuplicateException;


    //remove a friend from friends list, if it is in list
    //@requires: friend != null &
    //           exists 0 <= i < friends.size() such that friends.get(i) == friend
    //@throws: NullPointerException (unchecked) if friend == null
    //         NoElementException (checked) if friend isn't in list
    //@modifies: this
    //@effects: this_pre = < name, {Data_0,...,Data_n}, {friend_0,...,friend_m} >
    //          this_post = < name, {Data_0,...,Data_n}, {friend_0,...,friend_m}/{friend} >
    //          this_pre --> this_post
    public void removeFriend(String friend) throws NullPointerException, NoElementException;


    //remove a Data element from category, if it is in list
    //@requires: e != null &
    //             exists 0<= i < store.size() such that store.get(i) == e
    //@throws: NullPointerException (unchecked) if e == null
    //         NoElementException (checked) if e isn't in list
    //@modifies: this
    //@effects: this_pre = < name, {Data_0,...,Data_n}, {friend_0,...,friend_m} >
    //          this_post = < name, {Data_0,...,Data_n}/{e}, {friend_0,...,friend_m} >
    //          this_pre --> this_post
    public void removeData(E e) throws NullPointerException, NoElementException;


    //returns the total likes of the category
    //@effects: returns the sum of store.get(i).numLike() ( for 0 <= i < store.size() )
    public int allLikes();


    //returns all Data elements of category
    //@effects: returns store
    public Vector<E> getDataList();


    //returns the friends list associated to the category
    //@effects: returns friends
    public Vector<String> getFriendList();

    //returns name of category
    //@effects: returns this.name
    public String getName();

    //friend 'put like' to the Data e
    //@requires: exists 0 <= i < friends.size() such that friend.get(i) == friend &
    //           friend != null
    //           exists 0 <= i < store.size() such that store.get(i) == e &
    //           e != null
    //@throws: NullPointerexception (unchecked) if friend == null or e == null
    //         NoElementException (checked) if friend isn't in list or e isn't in list
    //@effects: store.get.(i).addLike.(friend) such that store.get.(i) == e
    public void tapLike(String friend, E e) throws NullPointerException, NoElementException, AlreadyLikedException;
}
