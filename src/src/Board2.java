import java.io.IOException;
import java.util.*;

public class Board2<E extends Data> implements DataBoard<E>{
    /*
        Overview: Board is a collection of generic object <E extends Data>
                  every object is associated with only one Category
                  a list of friends is associated with each Category
                  Every Category is unique
                  Every Data is unique in a Category

        Typical Element: Board = < User_passw, { (Data_0, Category_0), ..., (Data_n, Category_m)} >
                         Category_i = < name_category, {Data_0, ..., Data_n}, A={friend_0, ..., friend_m} >
                         Data_j = < elem, likes, B={ like_friend_0, ..., like_friend_n } >
                         B is a subset of A

        AF(c): < c.user_passw, { M.get(Key).get(i), M.keySet().get(j) | 0 <= i < M.get(Key).size() &&
                                0 <= j < M.keySet().size() }

        RI(c): c.passw is valid* (see contract)
                froall i,j: 0 <= i,j < c.M.keySet.size() & i!=j ==> c.M.keySet.get(i) != c.M.keySet.get(j)      (unique category)
                forall i,j: 0 <= i,j < M.get(Key).size() & i!=j ==> c.M.get(Key).get(i) != c.M.get(Key).get(j)  (unique data)
                M.get(Key).getLikeList() is a subset of Key.getFriendList()
                c.M != null
                forall i: 0 <= i < c.M.keySet() ==> c.M.get(i) != null
                forall i: 0 <= i < c.M.keySet() ==> c.M.keySet().get(i) != null
                forall i: 0 <= i < c.M.keySet() ==> c.M.key.Set().get(i).getFriendList().contains(this.usernmae) == true


     */

    private String username;
    private String password;
    Map<Category<E>, Vector<E>> M;           //Categoria --> dati della categoria

    //@requires: user != null, valid passw*   (see DataBoard)
    //@throws: NullpointerException (uncheked) if user == null || passw == null
    //         InvalidPasswordException (checked) if passw not valid
    //@modifies: this
    //@effects: create new Board with c.Map != null
    //          user != null & passw != null
    public Board2(String user, String passw) throws NullPointerException, InvalidPasswordException{
        if(user == null) throw new NullPointerException("Cannot create 'null' user");
        this.username = user;

        if( this.validatePassw(passw) ){
            this.password = passw;
        }

        M = new HashMap<>();
    }


    @Override
    public void createCategory(String Cat, String passw) throws NullPointerException, InvalidPasswordException, NoDuplicateException {
    if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        for(Category<E> c : M.keySet()){
            if(c.getName().equals(Cat)) throw new NoDuplicateException("NoDuplicateException: Category already exists");
        }

        Category<E> c = new Category(Cat);
        c.addFriend(this.username);
        M.put(c, new Vector<E>());
    }

    @Override
    public void removeCategory(String Cat, String passw) throws NullPointerException, InvalidPasswordException, NoElementException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        //Controllo se esiste la categoria
        Category<E> supp = null;
        for(Category<E> c : M.keySet()){
            if(c.getName().equals(Cat)){
                supp = c;
            }
        }
        if(supp == null) throw new NoElementException("NoElementException: There isn't this Category");

        M.remove(supp);
    }

    @Override
    public void addFriend(String Cat, String passw, String friend) throws NullPointerException, InvalidPasswordException, NoElementException, NoDuplicateException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        Category<E> supp = null;
        for(Category<E> c : M.keySet()){
            if(c.getName().equals(Cat)){
                supp = c;
            }
        }
        if(supp == null) throw new NoElementException("NoElementException: There isn't this Category");
        supp.addFriend(friend);

    }

    @Override
    public void removeFriend(String Cat, String passw, String friend) throws NullPointerException, InvalidPasswordException, NoElementException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        //Controllo se esiste la categoria
        Category<E> supp = null;
        for(Category<E> c : M.keySet()){
            if(c.getName().equals(Cat)){
                supp = c;
            }
        }
        if(supp == null) throw new NoElementException("NoElementException: There isn't this Category");
        supp.removeFriend(friend);
    }

    @Override
    public boolean put(String passw, E elem, String Cat) throws NullPointerException, InvalidPasswordException, NoElementException, NoDuplicateException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");
        if(elem == null) throw new NullPointerException("NullPointerException: Can't exists null Data");

        //Controllo se esiste la categoria
        Category<E> supp = null;
        for(Category<E> c : M.keySet()){
            if(c.getName().equals(Cat)){
                supp = c;
            }
        }
        if(supp == null) throw new NoElementException("NoElementException: There isn't this Category");
        supp.addData(elem);
        M.put(supp, supp.getDataList());

        return true;
    }

    @Override
    public E get(String passw, E elem) throws NullPointerException, InvalidPasswordException, NoElementException, RuntimeException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(elem == null) throw new NullPointerException("NullPointerException: Can't exists null Data");

        //Controllo se esiste elem
        E tmp = null;
        for(Category<E> c : M.keySet()){
            for(E e : M.get(c)){
                if(e.equals(elem)) tmp = (E) elem.deepCopy();
            }
        }
        if(tmp == null) throw new NoElementException("NoElementException: Data doesn't exist");
        return tmp;
    }

    @Override
    public E remove(String passw, E elem) throws NullPointerException, InvalidPasswordException, NoElementException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(elem == null) throw new NullPointerException("NullPointerException: Can't exists null Data");

        E copy = null;
        boolean tmp = false;
        for(Category<E> c : M.keySet()){
            for(E e : M.get(c)){
                if(e.equals(elem)){
                    tmp = true;
                    c.removeData(elem);             //rimuovo
                    M.put(c, c.getDataList());      //Aggiorno la map
                    copy = (E)elem.deepCopy();
                }
            }
        }
        if(!tmp) throw new NoElementException("NoElementException 0.3 : Data doesn't exist");
        return copy;
    }

    @Override
    public List<E> getDataCategory(String passw, String Cat) throws NullPointerException, InvalidPasswordException, NoElementException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        //Controllo se esiste la categoria
        Category<E> supp = null;
        for(Category<E> c : M.keySet()){
            if(c.getName().equals(Cat)) supp = c;
        }
        if(supp == null) throw new NoElementException("NoElementException: There isn't this Category");
        return M.get(supp);
    }

    @Override
    public Iterator<E> getIterator(String passw) throws NullPointerException, InvalidPasswordException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");

        Set<E> T = new TreeSet<>(new MyComparator());
        for(Category<E> c : M.keySet()){
            T.addAll(M.get(c));
        }

        return T.iterator();
    }

    @Override
    public void insertLike(String friend, E elem) throws NullPointerException, NoElementException, AlreadyLikedException {
        if(elem == null) throw new NullPointerException("NullPointerException: Can't exists null Data");

        //Cerco la categoria associata ad elem
        boolean tmp = false;
        for(Category<E> c : M.keySet()){
            for(E e : c.getDataList()){
                if(elem.equals(e)){
                    tmp = true;
                    c.tapLike(friend, elem);
                    M.put(c, c.getDataList());  // Aggiorno la map
                }
            }
        }
        if(!tmp) throw new NoElementException("NoElementException: elem doesn't exist");
    }

    @Override
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException{
        if(friend == null) throw new NullPointerException("NullPointerException: Cannot exists 'null' friends");

        Set<E> T = new TreeSet<>(new MyComparator<>());
        for(Category<E> c : M.keySet()){
            if(c.getFriendList().contains(friend)){
                T.addAll(M.get(c));
            }
        }

        return T.iterator();
    }

    @Override
    public boolean validatePassw(String passw)throws NullPointerException, InvalidPasswordException{
        if(passw == null) throw new NullPointerException("Password must be not null");
        if(passw.length() < 6) throw new InvalidPasswordException("Password too short!");
        if(passw.length() > 20) throw new InvalidPasswordException("Password too long!");
        if(passw.contains(" ")) throw new InvalidPasswordException("Password cannot contain empty spaces");
        return true;
    }
}
