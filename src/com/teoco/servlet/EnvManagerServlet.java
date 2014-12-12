package com.teoco.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.teoco.db.*;

/**
 * Servlet implementation class EnvManagerServlet
 */
@WebServlet("/EnvManagerServlet")
public class EnvManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnvManagerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		
		
		//Get the Environment Table data
		
		if(action.equals("getEnv"))
		{
		
			//String envName = request.getParameter("envName");
			String searchString = request.getParameter("searchString");
			String searchField = request.getParameter("searchField");
			String searchOper = request.getParameter("searchOper");
			
			String sortId = request.getParameter("sidx");
			String sortOrder = request.getParameter("sord");
			
			int page = Integer.parseInt(request.getParameter("page"));
			System.out.println("Page: " + page);
			int limit = Integer.parseInt(request.getParameter("rows"));
			
			int archived = Integer.parseInt(request.getParameter("envArchived"));
			
			System.out.println("Sort ID is :" + sortId);
			System.out.println("Sort order is: " + sortOrder);
			
			
			//Sorting logic
			
/*			if(sortId != null){
				System.out.println("Requested sorting!!");  //always will comes in else loop
				
				response.setContentType("application/json");
				
				PrintWriter out = response.getWriter();
				
				//Get a list of table Environment_Details
				List<Environment> sortEnv2 = EnvDBManager.getSortedEnvironment(sortId,sortOrder); //env list comes in env2 list
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				//now convert list to JSON 
				String json = gson.toJson(sortEnv2);
				
				//write the json string in response object using printwriter.
				out.print(json);
			}*/
			
			int totalPages = 0;
			int envCount = EnvDBManager.getCountOfEnvironments(archived);
			if(envCount > 0) {
				totalPages = (int)Math.ceil(envCount/limit);
			}
			if(page > totalPages) {
				page = totalPages;
			}
			//page = page > totalPages? totalPages : page;
			
			if(limit < 0) {
				limit = 0;
			}
			
			int start = limit * page - limit;
			
			if(start < 0) {
				start = 0;
			}
			
			//Searching logic
			if(searchString == null && searchField == null){
				//System.out.println("No search yet");
				
				
				
				
				//System.out.println("Search string is:"+envName);
				

				response.setContentType("application/json");
				
				PrintWriter out = response.getWriter();
				
				//Get a list of table Environment_Details
				List<Environment> env2 = EnvDBManager.getEnvironment(archived, sortId, sortOrder, start, limit); //env list comes in env2 list
				
				ServerResponse serverResponseForEnv = new ServerResponse();
				serverResponseForEnv.setPage(page);
				serverResponseForEnv.setRecords(envCount);
				serverResponseForEnv.setTotal(totalPages);
				serverResponseForEnv.setRows(env2);
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				//now convert list to JSON 
				String json = gson.toJson(serverResponseForEnv);
				
				//write the json string in response object using printwriter.
				out.print(json);
			}
			else if(searchString != null)
			{
				//System.out.println("Search string present:"+envName);
				
				response.setContentType("application/json");
				
				PrintWriter out = response.getWriter();
				
				//Get a list of table Environment_Details
				List<Environment> searchEnv2 = EnvDBManager.getSearchedEnvironment(searchString,searchField,searchOper); //env list comes in env2 list
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				//now convert list to JSON 
				String json = gson.toJson(searchEnv2);
				
				//write the json string in response object using printwriter.
				out.print(json);
			}
			
			//int archived = Integer.parseInt(request.getParameter("envArchived"));
			
			
			
			//System.out.println("Search string is:"+envName);
			

			//response.setContentType("application/json");
			
			//PrintWriter out = response.getWriter();
			
			//Get a list of table Environment_Details
			//List<Environment> env2 = EnvDBManager.getEnvironment(archived); //env list comes in env2 list
			
			//Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			//now convert list to JSON 
			//String json = gson.toJson(env2);
			
			//write the json string in response object using printwriter.
			//out.print(json);
		
		}
		
		//Get Archived Environments
		
		if(action.equals("getArchivedEnv"))
		{
		
			//String envName = request.getParameter("envName");
			
			int archived = Integer.parseInt(request.getParameter("envArchived"));

			response.setContentType("application/json");
			
			PrintWriter out = response.getWriter();
			
			//get a list of table Environment_Details
			List<Environment> archivedEnv2 = EnvDBManager.getArchivedEnvironment(archived); //env list comes in env2 list
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			//now convert list to JSON 
			String json = gson.toJson(archivedEnv2);
			
			//write the json string in response object using printwriter.
			out.print(json);
		}
		
		
		//Get the Environment Details Table data
		
		if(action.equals("getEnvDetails"))
		{
		
			String envName = request.getParameter("envName");

			response.setContentType("application/json");
			
			PrintWriter out = response.getWriter();
			
			//get a list of table Environment_Details
			List<EnvironmentDetails> details2 = EnvDBManager.getEnvironmentDetails(envName); //details list comes in details2 list
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			//now convert list to JSON 
			String json = gson.toJson(details2);
			
			//write the json string in response object using printwriter.
			out.print(json);
		}
		
		//Get the Environment_DB Table data
		
		if(action.equals("getEnvDbDetails"))
		{
		
			String envName = request.getParameter("envName");

			response.setContentType("application/json");
			
			PrintWriter out = response.getWriter();
			
			//get a list of table Environment_Details
			List<Environment_DB> dbdetails2 = EnvDBManager.getEnvironmentDbDetails(envName); //details list comes in details2 list
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			//now convert list to JSON 
			String json = gson.toJson(dbdetails2);
			
			//write the json string in response object using printwriter.
			out.print(json);
		}
		
		//Get the Hostnames from Servers table in Environment_Details table
		
		if(action.equals("getHostName"))
		{
		
			//String envName = request.getParameter("envName");

			response.setContentType("application/json");
			
			PrintWriter out = response.getWriter();
			
			//Get a list of table Environment_Details
			List<String> servers2 = EnvDBManager.getServers(); //details list comes in details2 list
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			//now convert list to JSON 
			String json = gson.toJson(servers2);
			
			//write the json string in response object using printwriter.
			out.print(json);
		}
		
		//to get the Status from Status table.
		
		if(action.equals("getStatus"))
		{
		
			//String envName = request.getParameter("envName");

			response.setContentType("application/json");
			
			PrintWriter out = response.getWriter();
			
			//get a list of table Status
			List<String> status2 = EnvDBManager.getStatus(); //details list comes in details2 list
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			//now convert list to JSON 
			String json = gson.toJson(status2);
			
			//write the json string in response object using printwriter.
			out.print(json);
		}
		
		//Get the Product Names from Products Table
		
		if(action.equals("getProducts"))
		{
		
			//String envName = request.getParameter("envName");

			response.setContentType("application/json");
			
			PrintWriter out = response.getWriter();
			
			//get a list of table Products
			List<String> products2 = EnvDBManager.getProducts(); 
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			//now convert list to JSON 
			String json = gson.toJson(products2);
			
			//write the json string in response object using printwriter.
			out.print(json);
		}
		
		//Get the Product Applications from Application Table
		
		if(action.equals("getProdApp"))
		{
		
			//String envName = request.getParameter("envName");

			response.setContentType("application/json");
			
			PrintWriter out = response.getWriter();
			
			//get a list of table Products
			List<String> prodApp2 = EnvDBManager.getProdApp(); 
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			//now convert list to JSON 
			String json = gson.toJson(prodApp2);
			
			//write the json string in response object using printwriter.
			out.print(json);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if (action!=null && action.equals("deleteEnv")){
			
			String envName = request.getParameter("id");
			
			EnvDBManager.delEnv(envName);
			
		}
		else if(action!=null && action.equals("delEnvDetails")){
			
			EnvironmentDetails envDetails = new EnvironmentDetails();
			
			envDetails.setId(Integer.parseInt(request.getParameter("id")));
			
			EnvDBManager.deleteEnvDetails(envDetails);	
			
		}
		else if(action!=null && action.equals("delEnvDbDetails")){
			
			Environment_DB envDB = new Environment_DB();
			
			envDB.setId(Integer.parseInt(request.getParameter("id")));
			
			EnvDBManager.deleteEnvDbDetails(envDB);	
			
		}
		
		else if (action!=null && action.equals("checkEnv")){
			
			String envName = request.getParameter("envName"); // picks from data property of ajax
			
			JSONObject obj = new JSONObject();
			
			
			Boolean result = EnvDBManager.checkEnvName(envName);
			
			obj.put("checkEnvResult", result);
			
			PrintWriter out = response.getWriter();
			
			out.print(obj.toJSONString());
			
			
	}
		else if (action!=null && action.equals("addEnv")){
		
				Environment env = new Environment();
		
				env.setEnvName(request.getParameter("envName"));
				env.setProductSuite(request.getParameter("productSuite"));
				env.setEnvStatus(request.getParameter("envStatus"));
				env.setEnvOwner(request.getParameter("envOwner"));
				env.setTestFlag(Integer.parseInt(request.getParameter("testFlag")));
				env.setEnvArchived(Integer.parseInt(request.getParameter("envArchived")));
				env.setUse(request.getParameter("use"));
				
				
				String envName = request.getParameter("envName");
				
				if(EnvDBManager.checkEnvName(envName)){
					EnvDBManager.updateEnv(env);
					
				}
				else{
					EnvDBManager.insertNewEnv(env);
				}
				
		}
		else if (action!=null && action.equals("addEnvDetails")){
			
			EnvironmentDetails envDetails = new EnvironmentDetails();
			
			
			envDetails.setEnvName(request.getParameter("envName"));
			envDetails.setProductApp(request.getParameter("productApp"));
			envDetails.setFolder(request.getParameter("folder"));
			envDetails.setHost(request.getParameter("host"));
			envDetails.setPort(request.getParameter("port"));
			envDetails.setUrl(request.getParameter("url"));
			envDetails.setLogin(request.getParameter("login"));
			envDetails.setComments(request.getParameter("comments"));
			envDetails.setRefhist(request.getParameter("refhist"));
			
			
			String id = request.getParameter("id");
			
			
			
			if(id == null || id.equals("") || id.equals("_empty")){
				EnvDBManager.insertEnvDetails(envDetails);
			}
			else{
				envDetails.setId(Integer.parseInt(id));
				EnvDBManager.updateEnvDetails(envDetails);
			}
			
				
		
		}
		else if (action!=null && action.equals("addEnvDbDetails")){
			
			Environment_DB envDB = new Environment_DB();
			
			
			envDB.setEnvName(request.getParameter("envName"));
			envDB.setUse(request.getParameter("use"));
			envDB.setHost(request.getParameter("host"));
			envDB.setSchema(request.getParameter("schema"));
			envDB.setLogin(request.getParameter("login"));
			envDB.setBout_level(request.getParameter("bout_level"));
			envDB.setCustom(request.getParameter("custom"));
			envDB.setRefhist(request.getParameter("refhist"));
			
			
			
			String id = request.getParameter("id");
			
			if(id == null || id.equals("") || id.equals("_empty")){
				EnvDBManager.insertEnvDbDetails(envDB);
			}
			else{
				envDB.setId(Integer.parseInt(id));
				EnvDBManager.updateEnvDbDetails(envDB);
			}	
		
		}
	}
}


