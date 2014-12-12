package com.teoco.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvDBManager {
	
	private static Connection con = null;
	
	private static Map<String, String> envMap = new HashMap<String, String>();
	static {
		envMap.put("envName", "Environment_Name");
		envMap.put("productSuite", "Product_Suite");
	}
	
	
	public static Connection getConnection(){
		
		try
		{
			String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
			String user = "";
			String password = "";
			String url = "jdbc:odbc:emg";
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return con;
		
		
	}
	
	public static Integer getCountOfEnvironments(int archived) {
		
		Connection con = EnvDBManager.getConnection();
		int count = 0;
		try
		{
			String countQuery = "SELECT count(*) FROM Environment WHERE Archived=" + archived + ";";
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(countQuery);
			
			rs.next(); // moves the cursor to the 1st row in the result set object. by default the cursor is before first row.
			
			count = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	//Insert to Environment table
	public static void insertNewEnv(Environment env){
		
		Connection con = EnvDBManager.getConnection();
		try
		{
		
		String insertQuery="INSERT INTO Environment(Environment_Name,Product_Suite,Environment_Status,Environment_Owner,Test_Flag,Archived,Use) VALUES(?,?,?,?,?,?,?)";
		
		
		PreparedStatement pstmt = con.prepareStatement(insertQuery);
		
		pstmt.setString(1,env.getEnvName());
		pstmt.setString(2,env.getProductSuite());
		pstmt.setString(3,env.getEnvStatus());
		pstmt.setString(4,env.getEnvOwner());
		pstmt.setInt(5,env.getTestFlag());
		pstmt.setInt(6,env.getEnvArchived());
		pstmt.setString(7,env.getUse());
		
		pstmt.executeUpdate();
		
		con.close();
		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			
	}
	
	public static boolean checkEnvName(String envName){
		Connection con = EnvDBManager.getConnection();
		
		try{
			
			Statement stmt = con.createStatement();
			
			String checkQuery="SELECT count(Environment_Name) FROM Environment WHERE Environment_Name = '" + envName + "';";
			
			ResultSet rs = stmt.executeQuery(checkQuery);
			
			rs.next(); // moves the cursor to the 1st row in the result set object. by default the cursor is before first row.
			
			int count = rs.getInt(1);
			
			if (count == 0 ){
				return false;
		
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
		
		
		
	}
	
	//Insert to Environemt_Details table
	
	public static void insertEnvDetails(EnvironmentDetails envDetails){
			
			Connection con = EnvDBManager.getConnection();
			try
			{
			
			String insertQuery="INSERT INTO Environment_Details(Environment_Name,Product_Application,Folder,Host,Port,URL,Login,Comments,Refresh_History) VALUES(?,?,?,?,?,?,?,?,?)";
			
			
			PreparedStatement pstmt = con.prepareStatement(insertQuery);
			
			pstmt.setString(1,envDetails.getEnvName());
			pstmt.setString(2,envDetails.getProductApp());
			pstmt.setString(3,envDetails.getFolder());
			pstmt.setString(4,envDetails.getHost());
			pstmt.setString(5,envDetails.getPort());
			pstmt.setString(6,envDetails.getUrl());
			pstmt.setString(7,envDetails.getLogin());
			pstmt.setString(8,envDetails.getComments());
			pstmt.setString(9,envDetails.getRefhist());
		
			pstmt.executeUpdate();
			
			con.close();
			
			}
			catch(Exception e){
				
				e.printStackTrace();
			}
				
		}

	//Insert to Environment_DB table
	
	public static void insertEnvDbDetails(Environment_DB envDB){
		
		Connection con = EnvDBManager.getConnection();
		try
		{
		
		String insertQuery="INSERT INTO Environment_DB(Environment_Name,Use,Host,Schema,Login,BOUT_Level,Custom,Refresh_History) VALUES(?,?,?,?,?,?,?,?)";
		
		
		PreparedStatement pstmt = con.prepareStatement(insertQuery);
		
		pstmt.setString(1,envDB.getEnvName());
		pstmt.setString(2,envDB.getUse());
		pstmt.setString(3,envDB.getHost());
		pstmt.setString(4,envDB.getSchema());
		pstmt.setString(5,envDB.getLogin());
		pstmt.setString(6,envDB.getBout_level());
		pstmt.setString(7,envDB.getCustom());
		pstmt.setString(8,envDB.getRefhist());
	
		pstmt.executeUpdate();
		
		con.close();
		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			
	}

	//Delete from Environment table
	
	public static void delEnv(String envName){
		
		Connection con = EnvDBManager.getConnection();
		try
		{
			Statement stmt = con.createStatement();
			
			String deleteQuery="DELETE FROM Environment WHERE Environment_Name = '"+envName+"';";
		
			stmt.executeUpdate(deleteQuery);
		
				
		con.close();
		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			
	}

	//Delete from Environment_Details table
	
	public static void deleteEnvDetails(EnvironmentDetails envDetails){
		
		Connection con = EnvDBManager.getConnection();
		try
		{
			Statement stmt = con.createStatement();
			
			String deleteQuery="DELETE FROM Environment_Details WHERE ID="+envDetails.getId()+";";
		
			stmt.executeUpdate(deleteQuery);
		
				
		con.close();
		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			
	}

	//Delete from Environment_DB table
	public static void deleteEnvDbDetails(Environment_DB envDB){
		
		Connection con = EnvDBManager.getConnection();
		try
		{
			Statement stmt = con.createStatement();
			
			String deleteQuery="DELETE FROM Environment_DB WHERE ID="+envDB.getId()+";";
		
			stmt.executeUpdate(deleteQuery);
		
				
		con.close();
		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			
	}

	//Update to Environment table
	public static void updateEnv(Environment env){
		
		Connection con = EnvDBManager.getConnection();
		try
		{
		
	
			Statement stmt = con.createStatement();
		      String updateQuery = "UPDATE Environment " +
		                   "SET Product_Suite = '"+env.getProductSuite()+
		    		  "', Environment_Status = '"+env.getEnvStatus()+"', Environment_Owner = '"+env.getEnvOwner()+"', Test_Flag = "+env.getTestFlag()+", Archived = "+
		                   env.getEnvArchived()+", Use = '"+env.getUse()+"' WHERE Environment_Name = '"+ env.getEnvName() + "';";
		      
		      stmt.executeUpdate(updateQuery);
		
		con.close();
		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			
	}

	//Update to Environment_Details table
	public static void updateEnvDetails(EnvironmentDetails envDetails){
		
		Connection con = EnvDBManager.getConnection();
		try
		{
		
	
			Statement stmt = con.createStatement();
		      String updateQuery = "UPDATE Environment_Details " +
		                   "SET Environment_Name = '"+envDetails.getEnvName()+"', Product_Application = '"+envDetails.getProductApp()+
		    		  "', Folder = '"+envDetails.getFolder()+"', Host = '"+envDetails.getHost()+"', Port = '"+envDetails.getPort()+"', URL = '"+
		                   envDetails.getUrl()+"', Login = '"+envDetails.getLogin()+"', Comments ='"+envDetails.getComments()+"', Refresh_History = '"+
		    		  envDetails.getRefhist()+"'" +
		                   		" WHERE id="+envDetails.getId();
		      
		      stmt.executeUpdate(updateQuery);
		
		con.close();
		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			
	}
	
	//Update to Environment_DB table
	public static void updateEnvDbDetails(Environment_DB envDB){
		
		Connection con = EnvDBManager.getConnection();
		try
		{
		
	
			Statement stmt = con.createStatement();
		      String updateQuery = "UPDATE Environment_DB " +
		                   "SET Environment_Name = '"+envDB.getEnvName()+"', Use = '"+envDB.getUse()+
		    		  "', Host = '"+envDB.getHost()+"', Schema = '"+envDB.getSchema()+"', Login = '"+envDB.getLogin()+"', BOUT_Level = '"+
		    		  envDB.getBout_level()+"', Custom = '"+envDB.getCustom()+"', Refresh_History = '"+ envDB.getRefhist()+"'" +
		    		  " WHERE id="+envDB.getId();
		      
		      stmt.executeUpdate(updateQuery);
		
		con.close();
		
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			
	}
	
	
	
	
	
	//Gets Environment table data
	//Accepts env name and returns a list of details related to that env
	
		public static List<Environment> getEnvironment(int archived, String sortId, String sortOrder, int start, int limit){ 
			
			Connection con = EnvDBManager.getConnection();
			
			List<Environment> environment = new ArrayList<Environment>(); //blank list
			
			try
			{
				
				//String selectQuery="SELECT * FROM Environment WHERE Archived=" + archived + " ORDER BY " + envMap.get(sortId) 
										//+ " " + sortOrder + " LIMIT " + start + " " + limit + ";";
				
				//String selectQuery="SELECT * FROM Environment_Details;";
				
				String selectQuery="SELECT * FROM Environment WHERE Archived=" + archived + " ORDER BY " + envMap.get(sortId) 
				+ " " + sortOrder + " " + envMap.get(sortId) + ";";
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(selectQuery);
				
				while (rs.next()){
					
					Environment env = new Environment();
					
					//getting from the result set and setting into list
					
					
					env.setEnvName(rs.getString("Environment_Name"));
					env.setProductSuite((rs.getString("Product_Suite")));
					env.setEnvStatus(rs.getString("Environment_Status"));
					env.setEnvOwner((rs.getString("Environment_Owner")));
					env.setTestFlag(rs.getInt("Test_Flag"));
					env.setEnvArchived(rs.getInt("Archived"));
					env.setUse((rs.getString("Use")));
					
					environment.add(env); //list is prepared. return this list 
					
				}
				
				con.close();
			}
				
			
			catch(Exception e){
				e.printStackTrace();
			}
			
			return environment;
			
		
			
		}
		
		public static List<Environment> getSortedEnvironment(String sortId, String sortOrder){ 
			
			Connection con = EnvDBManager.getConnection();
			
			List<Environment> sortedEnvironment = new ArrayList<Environment>(); //blank list
			
			String query = "";
			try
			{
				//sortenvMap
				if(sortId.equals("envName")){
				
				//query="SELECT * FROM Environment ORDER BY "+sortId+sortOrder+";";
					query="SELECT * FROM Environment ORDER BY " + envMap.get(sortId) + " " + sortOrder + ";";
				}
				
				/*if(sortId.equals("envName")){
					query="SELECT * FROM Environment ORDER BY " + sortenvMap.get(sortId) + sortenvMap.get(sortOrder) + ";";
				}*/
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				while (rs.next()){
					
					Environment env = new Environment();
					
					//getting from the result set and setting into list
					
					
					env.setEnvName(rs.getString("Environment_Name"));
					env.setProductSuite((rs.getString("Product_Suite")));
					env.setEnvStatus(rs.getString("Environment_Status"));
					env.setEnvOwner((rs.getString("Environment_Owner")));
					env.setTestFlag(rs.getInt("Test_Flag"));
					env.setEnvArchived(rs.getInt("Archived"));
					env.setUse((rs.getString("Use")));
					
					sortedEnvironment.add(env); //list is prepared. return this list 
					
				}
				
				con.close();
			}
				
			
			catch(Exception e){
				e.printStackTrace();
			}
			
			return sortedEnvironment;
			
		
			
		}
		
		public static List<Environment> getSearchedEnvironment(String searchString, String searchField, String searchOper){ 
			
			Connection con = EnvDBManager.getConnection();
			
			List<Environment> searchedEnvironment = new ArrayList<Environment>(); //blank list
			
			String query = "";
			try
			{
				
				/*if(searchField.equals("envName")){
				
				query="SELECT * FROM Environment WHERE Environment_Name LIKE '%"+searchString+"%';";
				}
				else if(searchField.equals("productSuite")){
					query="SELECT * FROM Environment WHERE Product_Suite LIKE '%"+searchString+"%';";
				}*/
				
				if(searchOper.equals("eq")){
					query="SELECT * FROM Environment WHERE " + envMap.get(searchField) + " = '"+searchString+"';";
				}
				else if(searchOper.equals("cn")){
					query="SELECT * FROM Environment WHERE " + envMap.get(searchField) + " LIKE '%"+searchString+"%';";
				}
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				while (rs.next()){
					
					Environment env = new Environment();
					
					//getting from the result set and setting into list
					
					
					env.setEnvName(rs.getString("Environment_Name"));
					env.setProductSuite((rs.getString("Product_Suite")));
					env.setEnvStatus(rs.getString("Environment_Status"));
					env.setEnvOwner((rs.getString("Environment_Owner")));
					env.setTestFlag(rs.getInt("Test_Flag"));
					env.setEnvArchived(rs.getInt("Archived"));
					env.setUse((rs.getString("Use")));
					
					searchedEnvironment.add(env); //list is prepared. return this list 
					
				}
				
				con.close();
				
				//String selectQuery="SELECT * FROM Environment_Details;";
				
				//Statement stmt = con.createStatement();
				
				//ResultSet rs = stmt.executeQuery(query);
				
				//while (rs.next()){
					
					//Environment env = new Environment();
					
					//getting from the result set and setting into list
					
					
					//env.setEnvName(rs.getString("Environment_Name"));
					//env.setProductSuite((rs.getString("Product_Suite")));
					//env.setEnvStatus(rs.getString("Environment_Status"));
					//env.setEnvOwner((rs.getString("Environment_Owner")));
					//env.setTestFlag(rs.getInt("Test_Flag"));
					//env.setEnvArchived(rs.getInt("Archived"));
					//env.setUse((rs.getString("Use")));
					
					//searchedEnvironment.add(env); //list is prepared. return this list 
					
				//}
				
				//con.close();
			}
				
			
			catch(Exception e){
				e.printStackTrace();
			}
			
			return searchedEnvironment;
			
		
			
		}
		
		//Get Archived Environments
		
		public static List<Environment> getArchivedEnvironment(int archived){ 
			
			Connection con = EnvDBManager.getConnection();
			
			List<Environment> environment = new ArrayList<Environment>(); //blank list
			
			try
			{
				String selectQuery="SELECT * FROM Environment WHERE Archived = "+archived+";";
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(selectQuery);
				
				while (rs.next()){
					
					Environment env = new Environment();
					
					//getting from the result set and setting into list
					
					
					env.setEnvName(rs.getString("Environment_Name"));
					env.setProductSuite((rs.getString("Product_Suite")));
					env.setEnvStatus(rs.getString("Environment_Status"));
					env.setEnvOwner((rs.getString("Environment_Owner")));
					env.setTestFlag(rs.getInt("Test_Flag"));
					env.setEnvArchived(rs.getInt("Archived"));
					env.setUse((rs.getString("Use")));
					
					environment.add(env); //list is prepared. return this list 
					
				}
				
				con.close();
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return environment;
			
		}
		
		//Get the list of Env details from Environment_Details table
		//Accepts env name and returns a list of details related to that env

		public static List<EnvironmentDetails> getEnvironmentDetails(String envName){ 
			
			Connection con = EnvDBManager.getConnection();
			
			List<EnvironmentDetails> details = new ArrayList<EnvironmentDetails>(); //blank list
			
			try
			{
				String selectQuery="SELECT * FROM Environment_Details WHERE Environment_Name = '" + envName + "';";
			
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(selectQuery);
				
				while (rs.next()){
					
					EnvironmentDetails envDetails = new EnvironmentDetails();
					
					//getting from the result set and setting into list
					
					envDetails.setId(rs.getInt("ID"));
					envDetails.setEnvName(rs.getString("Environment_Name"));
					envDetails.setProductApp((rs.getString("Product_Application")));
					envDetails.setHost(rs.getString("Host"));
					envDetails.setPort(rs.getString("Port"));
					envDetails.setFolder((rs.getString("Folder")));
					envDetails.setUrl((rs.getString("Url")));
					envDetails.setLogin(rs.getString("Login"));
					envDetails.setComments(rs.getString("Comments"));
					envDetails.setRefhist((rs.getString("Refresh_History")));
					
					details.add(envDetails); //list is prepared. return this list 
					
				}
				
				con.close();
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return details;
			
		}
		
		//Get the data from Environment_DB table
		
		public static List<Environment_DB> getEnvironmentDbDetails(String envName){ 
			
			Connection con = EnvDBManager.getConnection();
			
			List<Environment_DB> environment = new ArrayList<Environment_DB>(); //blank list
			
			try
			{
				String selectQuery="SELECT * FROM Environment_DB WHERE Environment_Name = '" + envName + "';";
				
				//String selectQuery="SELECT * FROM Environment_Details;";
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(selectQuery);
				
				while (rs.next()){
					
					Environment_DB envDB = new Environment_DB();
					
					//getting from the result set and setting into list
					
					envDB.setId(rs.getInt("ID"));
					envDB.setEnvName(rs.getString("Environment_Name"));
					envDB.setUse((rs.getString("Use")));
					envDB.setHost(rs.getString("Host"));
					envDB.setSchema((rs.getString("Schema")));
					envDB.setLogin(rs.getString("Login"));
					envDB.setBout_level(rs.getString("BOUT_Level"));
					envDB.setCustom((rs.getString("Custom")));
					envDB.setRefhist(rs.getString("Refresh_History"));
					
					environment.add(envDB); //list is prepared. return this list 
					
				}
				
				con.close();
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return environment;
			
		}
		
		
	//Get the Hostnames from Servers table.
	
	public static List<String> getServers(){ 
			
			Connection con = EnvDBManager.getConnection();
			
			List<String> serversList = new ArrayList<String>(); //blank list
			
			try
			{
				String selectQuery="SELECT Hostname FROM Servers;";
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(selectQuery);
				
				while (rs.next()){
					
					//getting from the result set and setting into list
					
					serversList.add(rs.getString("Hostname")); //list is prepared. return this list 
					
				}
				
				con.close();
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return serversList;
			
		}


	//Get the status name from status table.
	
	public static List<String> getStatus(){ 
		
		Connection con = EnvDBManager.getConnection();
		
		List<String> statusList = new ArrayList<String>(); //blank list
		
		try
		{
			String selectQuery="SELECT Status_Name FROM Status;";
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			while (rs.next()){
				
				//getting from the result set and setting into list
				
				statusList.add(rs.getString("Status_Name")); //list is prepared. return this list 
				
			}
			
			con.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return statusList;
		
	}
	
	public static List<String> getProducts(){
		
		Connection con = EnvDBManager.getConnection();
		
		//empty array to get the list of products.
		
		List<String> productsList = new ArrayList<String>();
		
		try{
			String selectQuery = "SELECT Product_Name FROM Products;";
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			while(rs.next()){
				//add the results from ResultSet object to your list
				productsList.add(rs.getString("Product_Name"));
			}
			
			con.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return productsList;
		
	}
	
	
public static List<String> getProdApp(){
		
		Connection con = EnvDBManager.getConnection();
		
		//empty array to get the list of product applications.
		
		List<String> prodAppList = new ArrayList<String>();
		
		try{
			String selectQuery = "SELECT Application_Name FROM Applications;";
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			while(rs.next()){
				//add the results from ResultSet object to your list
				prodAppList.add(rs.getString("Application_Name"));
			}
			
			con.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return prodAppList;
		
	}
	
	

}
