<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="java.net.*" %>
<%@page import="java.util.*"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.ResultSet" %>
<%@page import="com.teoco.db.EnvDBManager" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="EnvMgmt.css" rel="stylesheet" type="text/css">
<title>Add New Environment</title>
</head>
<body>

<div id="logo">
	<img src="logo.jpg" alt="Smiley face" width="200" height="42">
	<h1><center>Add New Environment</center></h1>
</div>

	<form name="AddNewEnv" id="AddNewEnv" action="EnvManagerServlet" METHOD="POST">
		<div id="env">
			<label>Product Suite</label>
			<% 
				try{
					
					Connection con = EnvDBManager.getConnection();
					
					Statement stmt = con.createStatement();
					
					String sql="SELECT * FROM Products";
					
					ResultSet rs = stmt.executeQuery(sql);
					
			%>
					
					<select name="products">
					<option selected> Select Product Suite </option>
					
			<% while(rs.next()){ %>
					<option> <%= rs.getString(1)%> </option>
			<% } %>
					</select>
			<%}
			catch(Exception e){
				e.printStackTrace();
			}
		
			%>
	<br/>
	<label>Environment</label>
	<input name="env" type="text"> <br/>
	<label>Status</label>
	<input name="status" type="text"> <br/>
	<label>Owner</label>
	<input name="owner" type="text"> <br/>
	<label>Test Flag</label>
	<input name="testflag" type="text"> <br/>
	<label>Archived</label>
	<input name="archived" type="text"> <br/>
	<label>Use</label>
	<input name="envUse" type="text"> <br/>
	<br/>
	
	<div id="footer">
		<input id="submit" type="submit" value="Submit" name="submit" /> 
	</div>
		</div>
	</form>
</body>
</html>