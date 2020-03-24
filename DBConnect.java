
package library;

import java.sql.*;
import java.util.ArrayList;

public class DBConnect {
    
    private Connection con;
    private Statement   st; 
    private ResultSet   rs;
    public  String Sql_Query;
    
    private final String database_name = "library";
    
//==============================================================================     
    public Connection DBConnect() {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306"
                    + "/"+database_name+"?zuseUnicode=true&"
                    + "useJDBCCompliantTimezoneShift=true&"
                    + "useLegacyDatetimeCode=false&"
                    + "serverTimezone=UTC","root","");

            System.out.println("Connection to Database Success");
            return con;
        }catch(SQLException ex){
            System.out.println("Error: "+ex);
            
        }
        return null;
    }   
//============================================================================== 
   
    
/* Insert Record */
    public int Insert_Admin(String name ,String pass ,String email) 
                                                            throws SQLException{
       String TABLE_NAME ="admin"; 
       String column1 = "admin_name"; 
       String column2 = "admin_pass";
       String column3 = "admin_email";
        
       String Sql_Query = "INSERT INTO "+TABLE_NAME+
                                              "("+column1+","
                                             + ""+column2+","
                                             + ""+column3+") VALUES " 
                                             + "("
                                             + "'"+name+"',"
                                             + "'"+pass+"',"
                                             + "'"+email+"'"
                                             + ");";
        
       PreparedStatement pstmt = con.prepareStatement(Sql_Query);
   
        return pstmt.executeUpdate();
    }
//==============================================================================    
    public int Insert_Student(String name,String pass,String email,String phone) 
                                                            throws SQLException{
       String TABLE_NAME ="student"; 
       String column1 = "student_name"; 
       String column2 = "student_pass";
       String column3 = "student_email";
       String column4 = "student_phone";
        
       String Sql_Query = "INSERT INTO "+TABLE_NAME+
                                              "("+column1+","
                                             + ""+column2+","
                                             + ""+column3+","
                                             + ""+column4+") VALUES " 
                                             + "("
                                             + "'"+name+"',"
                                             + "'"+pass+"',"
                                             + "'"+email+"',"
                                             + "'"+phone+"'"
                                             + ");";
        
       PreparedStatement pstmt = con.prepareStatement(Sql_Query);
   
        return pstmt.executeUpdate();
    }
//==============================================================================    
    public int Insert_Book(String name ,String author ,
                            int quantity,String sellable,double price)   throws SQLException{
       String TABLE_NAME ="books"; 
       String column1 = "book_name";
       String column2 = "book_author";
       String column3 = "book_quantity";
       String column4 = "book_sellable";
       String column5 = "book_price";
        
       String Sql_Query = "INSERT INTO "+TABLE_NAME+
                                              "("+column1+","
                                             + ""+column2+","
                                             + ""+column3+","
                                             + ""+column4+","
                                             + ""+column5+") VALUES " 
                                             + "("
                                             + "'"+name+"',"
                                             + "'"+author+"',"
                                             + ""+quantity+","
                                             + "'"+sellable+"',"
                                             + ""+price+""
                                             + ");";
        
       PreparedStatement pstmt = con.prepareStatement(Sql_Query);
   
        return pstmt.executeUpdate();
    }
//==============================================================================   
    public int borrow_Book(int code ,int id ,String date) 
                                                            throws SQLException{
       String TABLE_NAME ="book_line"; 
       String column1 = "book_code";
       String column2 = "student_id";
       String column3 = "date";
       String column0 = "borrow_id"; 
       
       String concat = ""+code+"-"+id+"-"+"-"+date+"";
       
       String Sql_Query = "INSERT INTO "+TABLE_NAME+
                                              "("+column1+","
                                             + ""+column2+","
                                             + ""+column3+","
                                             + ""+column0+") VALUES " 
                                             + "("
                                             + ""+code+","
                                             + ""+id+","
                                             + "'"+date+"',"
                                             + "'"+concat+"'"
                                             + ");";
        
       
        update_quantity(code,-1);
       
       PreparedStatement pstmt = con.prepareStatement(Sql_Query);
       
       
       
        return pstmt.executeUpdate();
    }
//============================================================================== 
    public int order_Book(int code ,int id ,double price,String date) 
                                                            throws SQLException{
       String TABLE_NAME ="order_line"; 
       String column1 = "book_code";
       String column2 = "student_id";
       String column3 = "price";
       String column4 = "date";
       String column0 = "order_id";
       String concat  = ""+code+"-"+id+"-"+price+"-"+date+""; 
       
       String Sql_Query = "INSERT INTO "+TABLE_NAME+
                                              "("+column1+","
                                             + ""+column2+","
                                             + ""+column3+","
                                             + ""+column4+","
                                             + ""+column0+") VALUES " 
                                             + "("
                                             + ""+code+","
                                             + ""+id+","
                                             + ""+price+","
                                             + "'"+date+"',"
                                             + "'"+concat+"'"
                                             + ");";
        update_quantity(code, -1);
       
       PreparedStatement pstmt = con.prepareStatement(Sql_Query);
   
        return pstmt.executeUpdate();
    }
//==============================================================================    
    public int return_book(int code)throws SQLException{
        return  update_quantity(code, 1);
    } 
    
    
/* Delete Record  by ID */
    public int Delete_admin(int id) throws SQLException{
        String TABLE_NAME = "admin";
        String ID = "admin_id";
        
     String Sql_Query="DELETE FROM "+TABLE_NAME+" WHERE "+ID+"="+id+"";   
     PreparedStatement pstmt = 
             con.prepareStatement(Sql_Query);
     return pstmt.executeUpdate();
    }
//============================================================================== 
    public int Delete_book(int code) throws SQLException{
        String TABLE_NAME = "books";
        String CODE = "book_code";
        
     String Sql_Query="DELETE FROM "+TABLE_NAME+" WHERE "+CODE+"="+code+"";   
     PreparedStatement pstmt = 
             con.prepareStatement(Sql_Query);
     return pstmt.executeUpdate();
    }
//============================================================================== 
    public int Delete_student(int id) throws SQLException{
        String TABLE_NAME = "student";
        String ID = "student_id";
        
     String Sql_Query="DELETE FROM "+TABLE_NAME+" WHERE "+ID+"="+id+"";   
     PreparedStatement pstmt = 
             con.prepareStatement(Sql_Query);
     return pstmt.executeUpdate();
    }
//============================================================================== 
        
    
/* Update by ID */
//==============================================================================    
    public int Update_admin(int id,String name,String pass ,String email)
                                                            throws SQLException{
        
     String TABLE_NAME ="admin";  
     String column1 = "admin_name";
     String column2 = "admin_pass";
     String column3 = "admin_email";
     String column0 = "admin_id";
    
     String Sql_Query="UPDATE "+TABLE_NAME+""+ 
                       " SET "+ 
                       ""+column1+" = '"+name+"'," +
                       ""+column2+" = '"+pass+"'," +
                       ""+column3+" = '"+email+"' " +
                       "WHERE "+column0+" ="+id+"  ";
         
      PreparedStatement pstmt = con.prepareStatement(Sql_Query);

     return pstmt.executeUpdate();  
   }
//==============================================================================    
    public int Update_student(int id,String name,String pass,
                                     String email,String phone )
                                                            throws SQLException{
        
     String TABLE_NAME ="student";  
     String column1 = "student_name";
     String column2 = "student_pass";
     String column3 = "student_email";
     String column4 = "student_phone";
     String column0 = "student_id";
     
     String Sql_Query="UPDATE "+TABLE_NAME+""+ 
                       " SET "+ 
                       ""+column1+" = '"+name+"'," +
                       ""+column2+" = '"+pass+"'," +
                       ""+column3+" = '"+email+"'," +
                       ""+column4+" = '"+phone+"' " +
                       "WHERE "+column0+" ="+id+"  ";
         
      PreparedStatement pstmt = con.prepareStatement(Sql_Query);

     return pstmt.executeUpdate();  
   } 
//==============================================================================    
    public int Update_book(int code,String name, String author,
                           int qunatity,String sellable , double price )   throws SQLException{
        
     String TABLE_NAME ="books";  
     String column1 = "book_name";
     String column2 = "book_author";
     String column3 = "book_quantity";
     String column4 = "book_sellable";
     String column5 = "book_price";
     String column0 = "book_code";
     
     String Sql_Query="UPDATE "+TABLE_NAME+""+ 
                       " SET "+ 
                       ""+column1+" = '"+name+"'," +
                       ""+column2+" = '"+author+"'," +
                       ""+column3+" = "+qunatity+"," +
                       ""+column4+" = '"+sellable+"'," +
                       ""+column5+" =  "+price+" " +
                       "WHERE "+column0+" ="+code+"  ";
         
      PreparedStatement pstmt = con.prepareStatement(Sql_Query);

     return pstmt.executeUpdate();  
   }      
//==============================================================================    
    public int update_quantity(int book_code,int change) throws SQLException{
     String TABLE_NAME ="books";
     String column = "book_quantity";
     String column0 = "book_code";
     
     String Sql_Query="UPDATE "+TABLE_NAME+""+ 
                       " SET "+
                        "book_quantity = book_quantity + "+change+
                        " WHERE "+column0+"="+book_code+"";
     
     PreparedStatement pstmt = con.prepareStatement(Sql_Query);

     return pstmt.executeUpdate(); 
  
    }
//==============================================================================  
    public int deccrement_quantity(int book_code) throws SQLException{
     String TABLE_NAME ="books";
     String column = "book_quantity";
     String column0 = "book_code";
     
     String Sql_Query="UPDATE "+TABLE_NAME+""+ 
                       " SET "+
                        "book_quantity = book_quantity - 1"+
                        " WHERE "+column0+"="+book_code+"";
     
     PreparedStatement pstmt = con.prepareStatement(Sql_Query);

     return pstmt.executeUpdate(); 
  
    }
/*Select Table*/
//============================================================================== 
 public ArrayList<ArrayList<String>>  Select_admin() throws SQLException{   
          
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    //ArrayList<String> row = new ArrayList<String>();
       String TABLE_NAME  = "admin";
       String column1 = "admin_id";
       String column2 = "admin_name"; 
       String column3 = "admin_pass";
       String column4 = "admin_email";
       String Sql_Query = "Select * From "+TABLE_NAME+"";

       Statement st = con.createStatement();
       ResultSet rs = st.executeQuery(Sql_Query);
       int i = 1;
       while (rs.next()) {
             ArrayList<String> row = new ArrayList<String>();
             row.add(Integer.toString(i));
             row.add(Integer.toString(rs.getInt(column1)));
             row.add(rs.getString(column2));
             row.add(rs.getString(column3));
             row.add(rs.getString(column4));
             table.add(row);
             i++;
         }
       
       System.out.println(TABLE_NAME);
       System.out.println("table size = "+table.size());
                 for(int j = 0 ; j < table.size() ; j++){
               for(int k = 0 ; k < table.get(j).size() ; k++){
                    System.out.print(table.get(j).get(k)+"  ");
           }
           System.out.println("\n------------------------------------");
       }
       
                 
                 
     return table;
 }
//============================================================================== 
 public ArrayList<ArrayList<String>>  Select_student() throws SQLException{   
          
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    //ArrayList<String> row = new ArrayList<String>();
       String TABLE_NAME  = "student";
       String column1 = "student_id";
       String column2 = "student_name"; 
       String column3 = "student_pass";
       String column4 = "student_email";
       String column5 = "student_phone";
       
       String Sql_Query = "Select * From "+TABLE_NAME+"";
       Statement st = con.createStatement();
       ResultSet rs = st.executeQuery(Sql_Query);
       
       int i = 1;
       while (rs.next()) {
             ArrayList<String> row = new ArrayList<String>();
             row.add(Integer.toString(i));
             row.add(Integer.toString(rs.getInt(column1)));
             row.add( rs.getString(column2));
             row.add(rs.getString(column3));
             row.add(rs.getString(column4));
             row.add(rs.getString(column5));
             table.add(row);
             i++;
         }
       System.out.println(TABLE_NAME);
       System.out.println("table size = "+table.size());
                 for(int j = 0 ; j < table.size() ; j++){
               for(int k = 0 ; k < table.get(j).size() ; k++){
                    System.out.print(table.get(j).get(k)+"  ");
           }
           System.out.println("\n----------------------------------");
       }
       
     return table;
 }
//============================================================================== 
 public ArrayList<ArrayList<String>>  Select_book() throws SQLException{   
          
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    //ArrayList<String> row = new ArrayList<String>();
       String TABLE_NAME  = "books";
       String column1 = "book_code";
       String column2 = "book_name"; 
       String column3 = "book_author";
       String column4 = "book_quantity";
       String column5 = "book_sellable";
       String Sql_Query = "Select * From "+TABLE_NAME+"";
       Statement st = con.createStatement();
       ResultSet rs = st.executeQuery(Sql_Query);
       int i = 1;
       while (rs.next()) {
             ArrayList<String> row = new ArrayList<String>();
             row.add(Integer.toString(i));
             row.add(Integer.toString(rs.getInt(column1)));
             row.add( rs.getString(column2));
             row.add(rs.getString(column3));
             row.add(Integer.toString(rs.getInt(column4)));
             row.add(rs.getString(column5));
             table.add(row);
             i++;
         }
       System.out.println(TABLE_NAME);
       System.out.println("table size = "+table.size());
                 for(int j = 0 ; j < table.size() ; j++){
               for(int k = 0 ; k < table.get(j).size() ; k++){
                    System.out.print(table.get(j).get(k)+"  ");
           }
           System.out.println("\n------------------------------------");
       }
       
     return table;
 }
//==============================================================================
 public ArrayList<ArrayList<String>>  view_orders() throws SQLException{   
          
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    //ArrayList<String> row = new ArrayList<String>();
       String TABLE_NAME  = "order_line";
       String column1 = "book_code";
       String column2 = "student_id"; 
       String column3 = "price";
       String column4 = "date";
       String column0 = "order_id";
       String Sql_Query = "Select * From "+TABLE_NAME+"";
       Statement st = con.createStatement();
       ResultSet rs = st.executeQuery(Sql_Query);
       int i = 1;
       while (rs.next()) {
             ArrayList<String> row = new ArrayList<String>();
             row.add(Integer.toString(i));
             row.add(Integer.toString(rs.getInt(column1)));
             row.add(Integer.toString(rs.getInt(column2)));
             row.add(Double.toString(rs.getInt(column3)));
             row.add(rs.getString(column4));
             row.add(rs.getString(column0));
             table.add(row);
             i++;
         }
       System.out.println(TABLE_NAME);
       System.out.println("table size = "+table.size());
                 for(int j = 0 ; j < table.size() ; j++){
               for(int k = 0 ; k < table.get(j).size() ; k++){
                    System.out.print(table.get(j).get(k)+"  ");
           }
           System.out.println("\n-------------------------");
       }
       
     return table;
 }  
//==============================================================================
 public ArrayList<ArrayList<String>>  view_borrows() throws SQLException{   
          
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    //ArrayList<String> row = new ArrayList<String>();
       String TABLE_NAME  = "book_line";
       String column1 = "book_code";
       String column2 = "student_id";
       String column4 = "date";
       String column0 = "borrow_id";
       String Sql_Query = "Select * From "+TABLE_NAME+"";
       Statement st = con.createStatement();
       ResultSet rs = st.executeQuery(Sql_Query);
       int i = 1;
       while (rs.next()) {
             ArrayList<String> row = new ArrayList<String>();
             row.add(Integer.toString(i));
             row.add(Integer.toString(rs.getInt(column1)));
             row.add(Integer.toString(rs.getInt(column2)));
             row.add(rs.getString(column4));
             row.add(rs.getString(column0));
             table.add(row);
             i++;
         }
       System.out.println(TABLE_NAME);
       System.out.println("table size = "+table.size());
                 for(int j = 0 ; j < table.size() ; j++){
               for(int k = 0 ; k < table.get(j).size() ; k++){
                    System.out.print(table.get(j).get(k)+"  ");
           }
           System.out.println("\n-------------------------");
       }
       
     return table;
 }  
//==============================================================================
 public ArrayList<Integer> book_codes() throws SQLException{
     
    ArrayList<Integer> book_codes = new ArrayList<Integer>(); 
    
    String TABLE_NAME  = "books";
    String column1 = "book_code";
    String Sql_Query = "Select * From "+TABLE_NAME+"";
    
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(Sql_Query);
   
       while (rs.next()) {
             book_codes.add(rs.getInt(column1));
         }
       
       System.out.println("book codes");
       System.out.println("table size = "+book_codes.size());
       for(int k = 0 ; k < book_codes.size() ; k++){
                    System.out.print(book_codes.get(k)+"  ");
           }
       
    return book_codes;
 }
 
 
/*Log in */ 
public int login_admin(String email , String pass) throws SQLException{
    
  int found = 0 ;
  String TABLE_NAME  = "admin";
  String column1 = "admin_email";
  String column2 = "admin_pass";
  String coulmn0 = "admin_name";
   
  String Sql_Query = "Select * From "+TABLE_NAME+"";
      Statement st = con.createStatement();
       //ResultSet rs = st.executeQuery(Sql_Query);
  ResultSet rs = st.executeQuery(Sql_Query);

    while (rs.next()){
         //int id = Integer.parseInt(rs.getStrimg(column1));
     if( rs.getString(column1).equals(email) && rs.getString(column2).equals(pass))
        {
            found = 1;
            break;

        }
    }
    if(found == 1){
      System.out.println("welcome  "+rs.getString(coulmn0)+""); 
      return found;
    }
    else{
        System.out.println("not found"); 
        return found;
    }
  }
//==============================================================================
public int login_student(String email , String pass) throws SQLException{
  
  int found = 0 ;
  String TABLE_NAME  = "student";
  String column1 = "student_email";
  String column2 = "student_pass";
  String coulmn0 = "student_name";
   
  String Sql_Query = "Select * From "+TABLE_NAME+"";
     Statement st = con.createStatement();  
     ResultSet rs = st.executeQuery(Sql_Query);

    while (rs.next()){
     if( rs.getString(column1).equals(email) && rs.getString(column2).equals(pass))
        {
            found = 1;
            break;

        }
    }
    if(found == 1){
      System.out.println("welcome  "+rs.getString(coulmn0)+""); 
      return found;
    }
    else{
        System.out.println("not found"); 
        return found;
    }
  } 
//==============================================================================

public int login_student_phone(String phone , String pass) throws SQLException{
  
  int found = 0 ;
  String TABLE_NAME  = "student";
  String column1 = "student_phone";
  String column2 = "student_pass";
  String coulmn0 = "student_name";
   
  String Sql_Query = "Select * From "+TABLE_NAME+"";
     Statement st = con.createStatement();  
     ResultSet rs = st.executeQuery(Sql_Query);

    while (rs.next()){
     if( rs.getString(column1).equals(phone) && rs.getString(column2).equals(pass))
        {
            found = 1;
            break;

        }
    }
    if(found == 1){
      System.out.println("welcome  "+rs.getString(coulmn0)+""); 
      return found;
    }
    else{
        System.out.println("not found"); 
        return found;
    }
  } 


/*Search book by name*/
public ArrayList<ArrayList<String>> Search_book(String book_name) throws SQLException {
    
   ArrayList<ArrayList<String>> book_table = new ArrayList<ArrayList<String>>();
    
  String TABLE_NAME  = "books";
  
  String column1 = "book_name";
  String column2 = "book_author";
  String column3 = "book_quantity";
  String column4 = "book_sellable";
  String column5 = "book_price";
  String column0 = "book_code";
   
  String Sql_Query = "Select * From "+TABLE_NAME+"";
  Statement st = con.createStatement();
  ResultSet rs = st.executeQuery(Sql_Query);

    while (rs.next()){
         ArrayList<String> book_row = new ArrayList<String>();
     if( rs.getString(column1).equals(book_name))
        {
             book_row.add(Integer.toString(rs.getInt(column0)));
             book_row.add(rs.getString(column1));
             book_row.add(rs.getString(column2));
             book_row.add(Integer.toString(rs.getInt(column3)));
             book_row.add(rs.getString(column4));
             book_row.add(Double.toString(rs.getInt(column5)));
             book_table.add(book_row);
        }
    }
    
    System.out.println("books with name "+book_name+"");
       System.out.println("table size = "+book_table.size());
                 for(int j = 0 ; j < book_table.size() ; j++){
               for(int k = 0 ; k < book_table.get(j).size() ; k++){
                    System.out.print(book_table.get(j).get(k)+"  ");
           }
           System.out.println("\n-------------------------");
       }
    return book_table;
}
//==============================================================================
}
/*

return book 

*/




