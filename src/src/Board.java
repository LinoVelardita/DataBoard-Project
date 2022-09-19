import java.io.IOException;
import java.util.*;


public class Board<E extends Data> implements DataBoard<E>{

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

        AF(c): < c.user_passw, { (c.alldata.get(i), c.Content.get(j)) |  0 <= i < c.alldata.size() & 0 <= j < c.Content.size() }

        RI(c): c.passw is valid* (see DataBoard)
               c.Content.get(i) != c.Content.get(j) forall 0 <= i,j <= c.Content.size() & i != j        (unique category)
               c.alldata.get(i) != c.alldata.get(j) forall 0 <= i,j <= c.alldata.size() & i != j        (unique data)
               c.Content.get(i).getDataList().get(j).getLikeList() is a subset of c.Content.get(i).getFriendList()
               c.Content != null
               c.alldata != null
               c.Content.get(i) != null   forall i: 0 <= i < c.Content.size()
               c.alldata.get(i) != null   forall i: 0 <= i < c.alldata.size()
               c.Content.get(i).getFriendList().contains(this.username) == true   forall: 0 <= i < c.Content.size()
     */


    private String username;
    private String password;
    private Vector<Category<E>> Content;     //collection of Category in Board
    private Set<E> alldata;             //collection of all Data element in Board (sorted by number of likes)

    //@requires: user != null, valid passw*   (see DataBoard)
    //@throws: NullpointerException (uncheked) if user == null || passw == null
    //         InvalidPasswordException (checked) if passw not valid
    //@modifies: this
    //@effects: create new Board with c.alldata != null, c.Content != null
    //          user != null & passw != null
    public Board(String user, String passw) throws NullPointerException, InvalidPasswordException{

        if(user == null) throw new NullPointerException("Cannot create 'null' user");
        this.username = user;

        if( this.validatePassw(passw) ){
            this.password = passw;
        }

        Content = new Vector<>();
        alldata = new TreeSet<>(new MyComparator<>());

    }

    @Override
    public void createCategory(String Cat, String passw) throws NullPointerException, InvalidPasswordException, NoDuplicateException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        for(Category c : Content){
            if(Cat.equals(c.getName())) throw new NoDuplicateException("NoDuplicateException: Category already exists");
        }
        Category c = new Category(Cat);
        c.addFriend(username);
        Content.add(c);
    }

    @Override
    public void removeCategory(String Cat, String passw) throws NullPointerException, InvalidPasswordException, NoElementException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        //controllo se esiste category
        int supp = -1;
        for(Category c : Content){
            if(Cat.equals(c.getName())){
                supp = Content.indexOf(c);
                break;
            }
        }
        if(supp == -1) throw new NoElementException("NoElementException: There isn't this Category");

        //elimino tutti i dati che erano nella categoria, e poi elimino la categoria
        Vector<E> data_to_remove = Content.get(supp).getDataList();
        for(E e : data_to_remove){
            alldata.remove(e);
        }
        Content.remove(supp);

    }

    @Override
    public void addFriend(String Cat, String passw, String friend) throws NullPointerException, InvalidPasswordException, NoElementException, NoDuplicateException{
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        //controllo se esiste category
        int supp = -1;
        for(Category c : Content){
            if(Cat.equals(c.getName())){
                supp = Content.indexOf(c);
                break;
            }
        }
        if(supp == -1) throw new NoElementException("NoElementException: There isn't this Category");
        Content.get(supp).addFriend(friend);
    }

    @Override
    public void removeFriend(String Cat, String passw, String friend) throws NullPointerException, InvalidPasswordException, NoElementException{
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        int supp = -1;
        for(Category c : Content){
            if(Cat.equals(c.getName())){
                supp = Content.indexOf(c);
                break;
            }
        }
        if(supp == -1) throw new NoElementException("NoElementException: There isn't this Category");
        Content.get(supp).removeFriend(friend);


    }

    @Override
    public boolean put(String passw, E elem, String Cat) throws NullPointerException, NoElementException, InvalidPasswordException, NoDuplicateException{
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");
        if(elem == null) throw new NullPointerException("NullPointerException: Can't exists null Data");

        //controllo se esiste la categoria
        int supp = -1;
        for(Category c : Content){
            if(Cat.equals(c.getName())){
                supp = Content.indexOf(c);
                break;
            }
        }
        if(supp == -1) throw new NoElementException("NoElementException: There isn't this Category");


        this.Content.get(supp).addData(elem);
        this.alldata.add(elem);

        return true;
    }

    @Override
    public E get(String passw, E elem) throws NullPointerException, InvalidPasswordException, NoElementException, RuntimeException{
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(elem == null) throw new NullPointerException("NullPointerException: Can't exists null Data");

        //controllo che esiste Data
        boolean tmp = false;
        for(Category<E> c : Content){
            for(E e : c.getDataList()){
                if(e.equals(elem)) tmp = true;
            }
        }
        if(!tmp) throw new NoElementException("NoElementException: Data doesn't exist");


        return (E)elem.deepCopy();
    }

    @Override
    public E remove(String passw, E elem) throws NullPointerException, InvalidPasswordException, NoElementException{
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(elem == null) throw new NullPointerException("NullPointerException: Can't exists null Data");


        E copy = null;
        boolean tmp = false;

        for(Category<E> c : Content){
            for(E e : c.getDataList()){
                if(elem.equals(e)){
                    tmp = true;
                    c.removeData(e);                //Elimino il dato dalla categoria corrispondente
                    alldata.remove(e);              //Elimino il dato dal TreeSet
                    copy = (E)elem.deepCopy();
                }
            }
        }

        if(!tmp) throw new NoElementException("NoElementException 0.3 : Data doesn't exist");
        return copy;

    }

    @Override
    public List<E> getDataCategory(String passw, String Cat)throws NullPointerException, InvalidPasswordException, NoElementException {
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");
        if(Cat == null) throw new NullPointerException("NullPointerException: Can't exists null Category");

        int supp = -1;
        for(Category c : Content){
            if(Cat.equals(c.getName())){
                supp = Content.indexOf(c);
                break;
            }
        }
        if(supp == -1) throw new NoElementException("NoElementException: There isn't this Category");


        return Content.get(supp).getDataList();

    }

    @Override
    public Iterator<E> getIterator(String passw) throws NullPointerException, InvalidPasswordException{
        if(passw == null) throw new NullPointerException("NullPointerException: Wrong Password");
        if(!passw.equals(password)) throw new InvalidPasswordException("InvalidPasswordException: Wrong Password");

        return this.alldata.iterator();
    }

    @Override
    public void insertLike(String friend, E elem) throws NullPointerException, NoElementException, AlreadyLikedException{
        if(elem == null) throw new NullPointerException("NullPointerException: Can't exists null Data");

        //trovo la categoria associata all'elemento
        boolean tmp = false;
        for(Category<E> c : Content){
            for(E e : c.getDataList()){
                if(e.equals(elem)){
                    tmp = true;
                    c.tapLike(friend, e);
                }
            }
        }
        if(!tmp) throw new NoElementException("NoElementException: elem doesn't exist");


        /*for(E e : alldata){           //INUTILE: i Data in Categroy sono una shallow copy dei dati in TreeSet
            if(e.equals(elem)){
                e.addLike(friend);
            }
        }*/

    }

    @Override
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException{
        if(friend == null) throw new NullPointerException("NullPointerException: Cannot exists 'null' friends");

        ArrayList<E> tmp = new ArrayList<>();
        for(Category<E> c : Content){
            if(c.getFriendList().contains(friend)){
                tmp.addAll(c.getDataList());
            }
        }

        return tmp.iterator();
    }

    //Validation Password
    @Override
    public boolean validatePassw(String passw)throws NullPointerException, InvalidPasswordException{
        if(passw == null) throw new NullPointerException("Password must be not null");
        if(passw.length() < 6) throw new InvalidPasswordException("Password too short!");
        if(passw.length() > 20) throw new InvalidPasswordException("Password too long!");
        if(passw.contains(" ")) throw new InvalidPasswordException("Password cannot contain empty spaces");
        return true;
    }


}
