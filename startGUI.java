package javafxProjects;


import java.sql.*;
import java.util.Properties;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene; 
import javafx.scene.layout.*; 
import javafx.stage.Stage; 
import javafx.scene.Group; 
import javafx.scene.control.*;

public class startGUI extends Application {
	
	private static TextArea output = new TextArea();
	private static TextArea output1 = new TextArea();

	static Connection conn = null;


	    public void start(Stage primaryStage) {

	    	Tables tables[] = new Tables[4];
	    	
	    	
	    	
	    	
	        TabPane tabPane = new TabPane();
	        tabPane.setPrefSize(500, 500);
	        
	        // CUSTOMERS TAB
	        
	        Tab tab1 = new Tab("CUSTOMERS", new Label("List of entries inside CUSTOMERS table"));
	        TextField name = new TextField("name");
	        TextField ccnum = new TextField("CC Number");
	        Button insert = new Button("INSERT");
	        Button refresh = new Button("Refresh");
	        Button delete = new Button("Remove");
	        
	        output.setPrefSize(150, 300);
	        output.setWrapText(true);
	        VBox text = new VBox();
	        text.getChildren().addAll(name, ccnum, insert, delete, output, refresh);
	        
	        tab1.setContent(text);
	        
			insert.setOnAction(e -> {
				insertEntry(name.getText(), ccnum.getText());
				update(1);
				name.setText("name");
				ccnum.setText("CC Number");
			});
			
			refresh.setOnAction(e ->{
			    update(1);
			    name.setText("name");
			    ccnum.setText("CC Number");
			});
			
			delete.setOnAction(e -> {
				deleteEntry(name.getText(), ccnum.getText());
				update(1);
			    name.setText("name");
			    ccnum.setText("CC Number");
			});

	        // PRODUCTS TAB
	        
	        Tab tab2 = new Tab("PRODUCTS"  , new Label("List of entries inside PRODUCTS table"));
	        TextField barcode = new TextField("Barcode#");
	        TextField descript = new TextField("Transaction Description");
	        TextField date = new TextField("Date of Purchase (yyyy-mm-dd)");
	        TextField total_owed = new TextField("Total Owed");
	        TextField price = new TextField("Price");
	        TextField make = new TextField("Make");
	        TextField model = new TextField("model");
	        TextField price_paid = new TextField("Total Amount Paid");
	        TextField quantity = new TextField("Quantity");
	        Button insert1 = new Button("INSERT");
	        Button refresh1 = new Button("Refresh");
	        Button delete1 = new Button("Remove");
	        
	        output1.setPrefSize(150, 300);
	        output1.setWrapText(true);
	        VBox text1 = new VBox();
	        text1.getChildren().addAll(barcode, descript, date, total_owed, price, make, model, 
	        		price_paid, quantity, insert1, delete1, output1, refresh1);
	        
	        tab2.setContent(text1);
	        
	        insert1.setOnAction(e -> {
				insertEntryProduct(barcode.getText(), descript.getText(), date.getText(),total_owed.getText(),
						price.getText(),make.getText(),model.getText(),price_paid.getText(),quantity.getText());
				update(2);
			});
			
			refresh1.setOnAction(e ->{
			    update(2);;
			});
			
			delete1.setOnAction(e -> {
				deleteEntryProducts(barcode.getText(), descript.getText(), date.getText(),total_owed.getText(),
						price.getText(),make.getText(),model.getText(),price_paid.getText(),quantity.getText());
				update(2);
			});
	        
	        // CONNECTION TAB
	        
	        Tab tab3 = new Tab("CONNECTION" , new Label("Enter Credentials to Access Database"));
	      
	        TextField server = new TextField("MySQL Server Address");
	        TextField user = new TextField("javaUser");
	        TextField pass = new TextField("password");
	        Button connect = new Button("Login");
	        TextField status = new TextField("Connection Status");
	        
	        VBox login = new VBox();
	        login.getChildren().addAll(server, user, pass, connect, status);
	        tab3.setContent(login);
	        
	        connect.setOnAction(e -> {
	        	getConnection(user.getText(), pass.getText(), "192.168.1.22");
	        	status.setText(connectionStatus());
	        });
	        
	        

	        tabPane.getTabs().add(tab1);
	        tabPane.getTabs().add(tab2);
	        tabPane.getTabs().add(tab3);

	        VBox vBox = new VBox(tabPane);
	        Scene scene = new Scene(vBox);

	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Electronic Inventory Project");

	        primaryStage.show();
	    }
	    
		 // Main Program
	    
	    public static void main(String[] args) {
	    		launch(args);
		    }
		 
		 
	    // Methods and Back end 
public class Tables extends startGUI {
	
	private String tableName;
	
	public Tables() {
		
	}
	
	
	public String getTableName(int n) {
		Statement stmt = null;
		ResultSet resultset = null;
		String tables[] = null;
		int i=0;

		try {
			stmt = conn.createStatement();
		    stmt.executeQuery("SHOW TABLES;");
		    
		    if (stmt.execute("SHOW TABLES;")) {
		        resultset = stmt.getResultSet();
		        System.out.println("Tables in project database:");
		    }

		    while (resultset.next()) {
		    	
		         tables[i] = resultset.getString("Tables_in_project");
		         i++;
		    }
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		
		return tables[n];
	}
	
}

public class data extends Tables {
	
	private String c1, c2, c3, c4 , c5;
	
	public data() {
		
	}
	public data(String nc1, String nc2, String nc3, String nc4, String nc5) {
		this.setc1(nc1);
		this.setc2(nc2);
		this.setc3(nc3);
		this.setc4(nc4);
		this.setc5(nc5);
	}
	
	//Setters
	
	public void setc1(String nc1) {
		this.c1 = nc1;
	}
	public void setc2(String nc2) {
		this.c2 = nc2;
	}
	public void setc3(String nc3) {
		this.c3 = nc3;
	}
	public void setc4(String nc4) {
		this.c4 = nc4;
	}
	public void setc5(String nc5) {
		this.c5 = nc5;
	}
	
	
	//Getters
	
	public String getc1() {
		return this.c1;
	}
	public String getc2() {
		return this.c2;
	}
	public String getc3() {
		return this.c3;
	}
	public String getc4() {
		return this.c4;
	}
	public String getc5() {
		return this.c5;
	}
}
	    
public String connectionStatus() {
	if (conn == null) return "Not Connected";
	else return "Connected to MySQL Database";
}


public void update(int tab) {
	
	if (tab == 1) {
		output.setText(executeMySQLQuery("CUSTOMER"));
	}
	else if (tab == 2) {
		output1.setText(executeMySQLQueryProduct());
	}
	
}
public static void getConnection(String username, String password, String server) {
Properties connectionProps = new Properties();
connectionProps.put("user", username);
connectionProps.put("password",  password);
System.out.println("Connecting to MySQL server...");

 try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     conn = DriverManager.getConnection(
             "jdbc:" + "mysql" + "://" +
                     server
                     + "" +
                     ":" + "3306" + "/"+"project"+
                     "",
             connectionProps);
 } catch (SQLException ex) {
     // handle any errors
     ex.printStackTrace();
 } catch (Exception ex) {
     // handle any errors
     ex.printStackTrace();
 }
 if (conn != null) {
	 //System.out.println("Succesfully connected to MySQL server!");
	 output.setText(executeMySQLQuery("CUSTOMER"));
	 output1.setText(executeMySQLQueryProduct());
	 
	 
 }
}

public static void insertEntryProduct(String barcode, String descript, String date, String owed, 
		String price, String make, String model, String amountPaid, String quantity) {
Statement stmt = null;
String command = "INSERT INTO PRODUCT (Barcode, Description, Date_of_Purchase, Total_owed, "
		+ "Unit_Price, Make, MODEL, Total_Price_Paid, Quantity) VALUES("+barcode+", \""+descript+"\", "
				+ "\""+date+"\", "+owed+", "+price+", \""+make+"\", \""+model+"\", "+amountPaid+", "+quantity+");";
System.out.println(command);
try {
	stmt = conn.createStatement();
    stmt.executeUpdate(command);
}
catch (SQLException ex){
    // handle any errors
    ex.printStackTrace();
}
catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }
}
public void deleteEntryProducts(String barcode, String descript, String date, String owed, 
		String price, String make, String model, String amountPaid, String quantity) {
	Statement stmt = null;
	String value, column;
	if (barcode != null) {
		value = barcode;
		column = "Barcode";
		String command = "DELETE FROM PRODUCT WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	else if (descript != null) {
		value = descript;
		column = "Description";
		String command = "DELETE FROM PRODUCTS WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	else if (date != null) {
		value = date;
		column = "Date_of_Purchase";
		String command = "DELETE FROM PRODUCTS WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	else if (owed != null) {
		value = owed;
		column = "Total_owed";
		String command = "DELETE FROM PRODUCTS WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	else if (price != null) {
		value = price;
		column = "Unit_Price";
		String command = "DELETE FROM PRODUCTS WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	else if (make != null) {
		value = make;
		column = "Make";
		String command = "DELETE FROM PRODUCTS WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	else if (model != null) {
		value = model;
		column = "MODEL";
		String command = "DELETE FROM PRODUCTS WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	else if (amountPaid != null) {
		value = amountPaid;
		column = "Total_Price_Paid";
		String command = "DELETE FROM PRODUCTS WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	else if (quantity != null) {
		value = quantity;
		column = "Quantity";
		String command = "DELETE FROM PRODUCTS WHERE "+column+" = \""+value+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}
	
	
}
public void deleteEntry(String name, String num) {
	Statement stmt = null;
	
	if (name != null) {
		
		String command = "DELETE FROM CUSTOMER WHERE Name = \""+name+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   } 
	}
	if(num != null) {
		String command = "DELETE FROM CUSTOMER WHERE CreditCard_Num = \""+num+"\";";
		try {
			stmt = conn.createStatement();
		    stmt.executeUpdate(command);
		}
		catch (SQLException ex){
		    // handle any errors
		    ex.printStackTrace();
		}
		catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   } 
	}
}

//Creates a table within a database with 2 entries

public static void createTable(int id, String tableName, String name) {
	Statement stmt = null;
	
	try {
		stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+tableName+" ("+id+" "+name+" VARCHAR(30));");
    }
    catch (SQLException ex){
        // handle any errors
        ex.printStackTrace();
    }
	catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
}

public static String executeMySQLQueryProduct() {
Statement stmt = null;
ResultSet resultset = null;
String output="";

try {
	stmt = conn.createStatement();
    resultset = stmt.executeQuery("SELECT * FROM PRODUCT;");
    ResultSetMetaData rsmd = resultset.getMetaData();
    int columnsNumber = rsmd.getColumnCount();
    
    output = "\n"+rsmd.getColumnName(1)+"             "+rsmd.getColumnName(2)+"                                     "+rsmd.getColumnName(3)+
    		"             "+rsmd.getColumnName(4)+"             "+rsmd.getColumnName(5)+"               "+rsmd.getColumnName(6)+  
    		"                    "+rsmd.getColumnName(7)+"                 "+rsmd.getColumnName(8)+"       "+rsmd.getColumnName(9)+
    		"                                \n\n";
    		
    while (resultset.next()) {
        for (int i = 1; i <= columnsNumber; i++) {
            String columnValue = resultset.getString(i);
            output = output + columnValue + "                       ";
        }
        output = output+("\n");
    }
}
catch (SQLException ex){
    // handle any errors
    ex.printStackTrace();
}

return output;
}

public static String executeMySQLQuery(String tableName) {
Statement stmt = null;
ResultSet resultset = null;
String output="";

try {
	stmt = conn.createStatement();
    resultset = stmt.executeQuery("SELECT * FROM "+tableName+";");
    ResultSetMetaData rsmd = resultset.getMetaData();
    int columnsNumber = rsmd.getColumnCount();
    
    output = "\n"+rsmd.getColumnName(1)+"                 "+rsmd.getColumnName(2)+"\n\n";
    		
    while (resultset.next()) {
        for (int i = 1; i <= columnsNumber; i++) {
            String columnValue = resultset.getString(i);
            output = output + columnValue + "                    ";
        }
        output = output+("\n");
    }
}
catch (SQLException ex){
    // handle any errors
    ex.printStackTrace();
}

return output;
}

public static void showTables()	{
Statement stmt = null;
ResultSet resultset = null;


try {
	stmt = conn.createStatement();
    stmt.executeQuery("SHOW TABLES;");
    
    if (stmt.execute("SHOW TABLES;")) {
        resultset = stmt.getResultSet();
        System.out.println("Tables in project database:");
    }

    while (resultset.next()) {
        System.out.println(resultset.getString("Tables_in_project"));
    }
}
catch (SQLException ex){
    // handle any errors
    ex.printStackTrace();
}
catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }
}


public static void insertEntry(String name, String ccnum) {
Statement stmt = null;
String command = "INSERT INTO CUSTOMER (Name, CreditCard_Num) VALUES(\""+name+"\", "+ccnum+");";
System.out.println(command);
try {
	stmt = conn.createStatement();
    stmt.executeUpdate(command);
}
catch (SQLException ex){
    // handle any errors
    ex.printStackTrace();
}
catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }
}

//This method performs the USE database command and had a input of String type of the database name

public static void useDatabase(String name) {
Statement stmt = null;
try {
	stmt = conn.createStatement();
    stmt.executeUpdate("USE "+name);

  
}
catch (SQLException ex){
    // handle any errors
    ex.printStackTrace();
}
}
//This method performs a full query of all types of the selected database and also has an input variable of type STring to specify the name of database

public static void executemySQLQuery(String tableName) {
Statement stmt = null;
ResultSet resultset = null;

try {
	stmt = conn.createStatement();
    resultset = stmt.executeQuery("SELECT * FROM "+tableName+";");
    ResultSetMetaData rsmd = resultset.getMetaData();
    int columnsNumber = rsmd.getColumnCount();
    System.out.println("All entries for "+tableName+":");
    		
    while (resultset.next()) {
        for (int i = 1; i <= columnsNumber; i++) {
            if (i > 1) System.out.print(",  ");
            String columnValue = resultset.getString(i);
            output.setText(columnValue + " " + rsmd.getColumnName(i));
        }
        System.out.println("");
    }
}
catch (SQLException ex){
    // handle any errors
    ex.printStackTrace();
}
}}