import java.util.Iterator;
import java.util.List;

public interface DataBoard<E extends Data> {

    //*valid passw:
    //              passw != null
    //              21 > passw.length > 5
    //              !passw.contains(" ")



    // Creates the identity of a category of Data element
    //@requires: Category != null, !Exists Category, valid passw*
    //@throws: NullPointerException (unchecked) if Category == null || passw == null
    //         InvalidPasswordException (checked) if passw != c.password
    //         NoDuplicateException (checked) if Category already exists in Board
    //@modifies: this
    //@effects: this_pre = < User_passw, { (Data_0, Category_0), ..., (Data_n, Category_m)} >
    //          this_post = < User_passw, { (Data_0, Category_0), ..., (Data_n, Category_m), (Category_m+1) } >
    //          this_pre --> this_post
    public void createCategory(String Category, String passw) throws NullPointerException, InvalidPasswordException, NoDuplicateException;


    // Remove a category
    //@requires: Category != null, Exists Category, valid passw*
    //@throws:  NullPointerException (unchecked) if Category == null || passw == null
    //          InvalidPasswordException (checked) if passw != c.password
    //          NoElementException (checked) if Category doesn't exists
    //@modifies: this
    //@effects: this_pre = < User_passw, { (Data_0, Category_0), ..., (Data_n, Category_m)} >
    //          this_post = < User_passw, { (Data_0, Category_0), ..., (Data_n, Category_m)} / {Data, Category_j} > such that Category_j == Category
    //          this_pre --> this_post
    public void removeCategory(String Category, String passw) throws NullPointerException, InvalidPasswordException, NoElementException;


    // Add a friend to a Category
    //@requires: Category != null, Exists Categroy, friend != null, !Exists friend in Category, valid passw*
    //@throws: NullPointerException (unchecked) if Category == null || passw == null || friend == null
    //         InvalidPasswordException (checked) if passw != c.password
    //         NoElementException (checked) if Category isn't in Board
    //         NoDuplicateException (checked) if friend is already in Category
    //@modifies: this
    //@effects: this_pre.Category = < name, {Data_0, ..., Data_n}, {friend_0, ..., friend_m} >
    //          this_post.Category = < name, {Data_0, ..., Data_n}, {friend_0, ..., friend_m} U {friend} >
    //          this_pre --> this_post
    public void addFriend(String Category, String passw, String friend) throws NullPointerException, InvalidPasswordException, NoElementException, NoDuplicateException;


    // Remove a friend from Category
    //@requires: Category != null, Exists Category, friend != null, Exists friend in Category, valid passw*
    //@throws: NullPointerException (unchecked) if Category == null || passw == null || friend == null
    //         InvalidPasswordException (checked) if passw != c.password
    //         NoElementException (chekced) if !Exists Category || !Exists friend
    //@modifies: this
    //@effects: this_pre.Category = < name, {Data_0, ..., Data_n}, {friend_0, ..., friend_m} >
    //          this_post.Category = < name, {Data_0, ..., Data_n}, {friend_0, ..., friend_m} / {friend} >
    //          this_pre --> this_post
    public void removeFriend(String Category, String passw, String friend) throws NullPointerException, InvalidPasswordException, NoElementException;


    // Insert Data in a specific Category
    //@requires: valid passw*, Data != null, !Exists Data in Board, Category!= null, Exists Category in Board
    //@throws: NullPointerException (unchecked) if passw == null || Data == null || Category == null
    //         InvalidPasswordException (checked) if passw != c.password
    //         NoElementException (checked) if !Exists Category
    //         NoDuplicateException (chekced) if Exists Data in Board
    //@modifies: this
    //@effects: this_pre = < User_passw, { (Data_0, Category_0), ..., (Data_n, Category_m)} >
    //          this_post = < User_passw, {(Data_0, Category_0), ..., (Data_n, Category_m)} U {(dato, Category_i)} > such that Category_i == cat
    //          such that Category_i == Category
    //          this_pre --> this_post
    public boolean put(String passw, E dato, String cat) throws NullPointerException, InvalidPasswordException, NoElementException,
                                                                NoDuplicateException;


    // Get a (deep) copy of Data elem in Board
    //@requires: Data != null, Exists Data, valid passw*
    //@throws: NullPointerException (unchecked) if Data == null || passw == null
    //         InvalidPasswordExcepton (checked) if passw != c.password
    //         NoElementException (checked) if !Exists Data
    //         RunTimeException (uncheked) if Data is not serializable
    //@effects: return a deep copy of Data, if exists
    public E get(String passw, E dato) throws NullPointerException, InvalidPasswordException, NoElementException, RuntimeException;

    // Remove Data in Board
    //@requires: Data != null, Exists Data, valid passw*
    //@throws: NullPointerException (unchecked) if Data == null || passw == null
    //         InvalidPasswordException (checked) if passw != c.password
    //         NoElementException (checked) if Data !Exists
    //@modifies: this
    //@effects: this_pre = < User_passw, { (Data_0, Category_0), ..., (Data_n, Category_m) } >
    //          this_post = < User_passw, { (Data_0, Category_0), ..., (Data_n, Category_m) } / {Data_i, Category | Data_i == dato} >
    //          this_pre --> this_post
    //          & returns a (deep)copy of Data
    public E remove(String passw, E dato)throws NullPointerException, InvalidPasswordException, NoElementException;


    //Returns the list of Data in Board of a specific Category
    //@requires: Cat != null, Cat Exists in Board, valid passw*
    //@throws: NullPointerException (unchecked) if Cat == null || passw == null
    //         InvalidPasswordException (checked) if passw != c.password
    //         NoElementException (checked) if Cat !Exists
    //@effects: returns Cat.getDataList();
    public List<E> getDataCategory(String passw, String Cat)throws NullPointerException, InvalidPasswordException, NoElementException;

    // Returns an iterator on all Data ordered by numLike
    //@requires: valid passw*
    //@throws: InvalidPasswordException (cheked) if passw != c.password
    //         NullPointerException (uncheked) if passw == null
    //@effects: returns A.iterator() such that A = { Data_1, ..., Data_n | n == alldata.size() &&
    //                                               Data_i.numLike <= Data_i+1.numLike() }
    public Iterator<E> getIterator(String passw) throws NullPointerException, InvalidPasswordException;


    // Add a like on a Data
    //@requires: friend != null, data != null, Exists data
    //@throws: NullPointerexception (unchecked) if friend == null || data == null
    //         NoElementException (checked) if data !Exists
    //@modifies: this
    //@effects: this_pre.data = < elem, likes, {like_friend_0, ..., like_friend_n} >
    //          this_post.data = < elem, likes++, {like_friend_0, ..., like_friend} U {friend} >
    //          this_pre --> this_post
    public void insertLike(String friend, E data)throws NullPointerException, NoElementException, AlreadyLikedException;


    // Returns an interator on all data shared with friend
    //@requires: friend != null
    //@throws: NullPointerException (uncheked) if friend == null
    //@effects: return A.iterator() such that A = { Category_i.getDataList() | Category_i.getFriendList.contains(friend) == True}
    public Iterator<E> getFriendIterator(String friend);


    //see valid passw*
    public boolean validatePassw(String passw)throws NullPointerException, InvalidPasswordException;

}
