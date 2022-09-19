import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class main {
    public static void main(String[] args) {


        //CREO LA BOARD
        Board me = null;
        try {
            me = new Board("Lino", "123456");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //CREO DELLE CATEGORIE
        try{

            me.createCategory("Matematici", "123456");
            me.createCategory("Fisici", "123456");
            me.createCategory("Informatici", "123456");
            me.createCategory("Common", "123456");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //RIMUOVO UNA CATEGORIA
        try{
            me.removeCategory("Common", "123456");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //AGGIUNGO GLI AMICI ALLA CATEGORIA
        try{
            me.addFriend("Matematici", "123456", "Calogero");
            me.addFriend("Matematici", "123456", "Salvatore");
            me.addFriend("Informatici", "123456", "Calogero");
            me.addFriend("Informatici", "123456", "Salvatore");
            me.addFriend("Informatici", "123456", "Francesco");
            me.addFriend("Informatici", "123456", "Antonio");
            me.addFriend("Matematici", "123456", "Lorenzo");
            me.addFriend("Informatici", "123456", "Calogero");   //Amico già inserito (NoDuplicateException)
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //ELIMINO GLI AMICI DALLA CATEGORIA
        try{
            me.removeFriend("Informatici", "123456", "Antonio");
            me.removeFriend("Informatici", "123456", "Antonio");  //Amico già rimosso (NoElementException)
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //CREO DEI DATI
        Data pi = new Data(3.14);
        Data Fibo = new Data("0, 1, 1, 2, 3, 5...");
        Data Tart = new Data ("Tartaglia");
        Data Ocaml = new Data("inferenza");



        try{
            me.put("123456", pi, "Matematici");
            me.put("123456", Fibo, "Matematici");
            me.put("123456", Tart, "Matematici");
            me.put("123456", Ocaml, "Informatici");
            me.put("1234", Fibo, "Informatici");  //Password errata
        }catch(Exception e){
            System.out.println(e.getMessage());
        }




        //PROVA DELLA DEEP COPY
        Data tmp = new Data("I don't have to be printed");
        try {
            tmp = me.get("123456", Fibo);
            me.insertLike("Lorenzo", Fibo);
            //Fibo.display();               //Verifico che la copia rimanga immutata
            //tmp.display();                //modificando il dato originale
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Data tmp2 = me.remove("123456", Fibo);
            //tmp2.display();
            Fibo = null;
            //Fibo.display();
            //tmp.display();      //Verifico che la deepcopy funzioni
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //INSERISCO LIKE
        try{
            me.insertLike("Lino", Ocaml);
            me.insertLike("Francesco", Ocaml);
            me.insertLike("Calogero", Ocaml);
            me.insertLike("Salvatore", Tart);
            me.insertLike("Francesco", pi);   //'Francesco' non appartiene alla lista di amici
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


        //PROVA ITERATORE DEI DATI ORDINATI
        /*try{
            Iterator walk = me.getIterator("123456");
            for (Iterator it = walk; it.hasNext();) {
                Data o = (Data) it.next();
                o.display();

            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }*/

        //PROVA DELL'ITERATORE SUI DATI CONDIVISI
        /*try{
            Iterator share = me.getFriendIterator("Totò");
            for(Iterator it = share; it.hasNext();){
                Data o = (Data) it.next();
                o.display();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }*/

        //TEST DEGLI ERRORI AGGIUNTIVO
        /*try{
            Board inval = new Board(null, "12345");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            me.createCategory("Wrong", "49254");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            me.createCategory(null, "123456");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            me.removeCategory("Wrong", "123456");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            me.removeFriend("Informatici", "123456", "Dijkstra");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            me.put("123456", pi, "Matematici");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            me.insertLike("Calogero", Ocaml);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }*/

        //TEST DELLA BOARD2
        Board2 B = null;
        try{
            B = new Board2("Lino", "secure");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            B.createCategory("Tarantino", "secure");
            B.createCategory("Scorsese", "secure");
            B.createCategory("Kubrick", "secure");
            B.createCategory("Remove", "secure");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            B.removeCategory("Remove", "secure");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            B.addFriend("Tarantino", "secure", "Calogero");
            B.addFriend("Tarantino", "secure", "Salvatore");
            B.addFriend("Scorsese", "secure", "Calogero");
            B.addFriend("Scorsese", "secure", "Paolo");
            B.addFriend("Kubrick", "secure", "Calogero");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //CREO DEI DATA
        Film SO = new Film("Space Odissey", 168);
        Film CO = new Film("Clockwork Orange", 137);
        Film Ir = new Film("The Irish Man", 210);
        Film Good = new Film("Good fellas", 148);
        Film Tx = new Film("Taxi driver", 160);
        Film Holly = new Film("Once upon a time in Hollywood", 156);
        Film R = new Film("Remove", 42);

        try{
            B.put("secure", SO, "Kubrick");
            B.put("secure", CO, "Kubrick");
            B.put("secure", Ir, "Scorsese");
            B.put("secure", Good, "Scorsese");
            B.put("secure", Tx, "Scorsese");
            B.put("secure", Holly, "Tarantino");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        //PROVA DELLA DEEPCOPY CON FILM
        try{
            Film tmp2 = (Film)B.get("secure", Ir);
            B.insertLike("Paolo", Ir);
            //Ir.display();
            //tmp2.display();           //VERIFICO CHE LA COPIA RESTI IMMUTATA
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            B.insertLike("Calogero", Ir);
            B.insertLike("Calogero", SO);
            B.insertLike("Lino", SO);
            B.insertLike("Lino", Holly);
            
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        //PROVA ITERATORE DEI DATI ORDINATI
        try{
            System.out.println("\n\nIteartore sui dati ordinati (in modo non decrescente)");
            Iterator walk = B.getIterator("secure");
            for (Iterator it = walk; it.hasNext();) {
                Data o = (Data) it.next();
                o.display();

            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //PROVA DELL'ITERATORE SUI DATI CONDIVISI
        try{
            System.out.println("\n\nIteartore sui dati condivisi\n\n");
            Iterator share = B.getFriendIterator("Lino");
            for(Iterator it = share; it.hasNext();){
                Data o = (Data) it.next();
                o.display();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }


}



