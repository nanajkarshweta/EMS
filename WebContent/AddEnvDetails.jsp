<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="java.net.*" %>
<%@ page import="java.sql.*" %>
<%@page import="com.teoco.db.EnvDBManager" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	 <style type="text/css">
        #footer {
            width: 100%;
            text-align:center;
            font-size: 0.75em;
        }
  </style>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/themes/smoothness/jquery-ui.css" />
	
	<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
	
	<link rel="stylesheet" type="text/css" href="css/EnvMgmt.css" />
	

	
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="scripts/grid.locale-en.js" type="text/javascript"></script>
	<script src="scripts/jquery.jqGrid.min.js" type="text/javascript"></script>
	
	<script src="scripts/url.js"></script>
	<script type="text/javascript">
	  
	  var count=2;
	  
	  $(document).ready(function() {
	
			  var urlEnvName='<%=request.getParameter("env")%>';
			  
			  var searchString = "";
			  
			  var hostnames = {};
			  
			  var products = {};
			  
			  var status = {};
			  
			  var prodApp = {};
			  
			  
/****************************************Environment Grid *****************************************************************/


		//Get all products
			  $.ajax({
				  url: "EnvManagerServlet?action=getProducts", 
				  async: false, 
				  success: function(data, result) 
				  {
					 $.each(data, function( index, value ) 
					 {
						products[value] = value; // this is same as object.property = value
					});
					  
					if (!result)
					  alert('Failure to retrieve the Product Names.');
				  }
			  });
			  
			  //Get Status_Name from Status table
			  
			  $.ajax({
				  url: "EnvManagerServlet?action=getStatus", 
				  async: false, 
				  success: function(data, result) {
					 $.each(data, function( index, value ) {
	  					status[value] = value; // this is same as object.property = value
					});
					  
					  if (!result)
						  alert('Failure to retrieve the Status.');
				  }
			  });
			  
			  


			  jQuery("#env").jqGrid({
				   	url:"EnvManagerServlet?action=getEnv&envArchived=0",
					datatype: "json",
				   	colNames:['Environment Name', 'Product Suite', 'Environment Status','Environment Owner','Test Flag','Archived','Use'],
				   	colModel:[
						   		{name:'envName',index:'envName',align:"center", width:150, sortable:true,editable:true,key:true,search:true,stype:'text',searchrules:{required:true},editrules:{required:true}},
						   		{name:'productSuite', index:'productSuite', align:"center", width:120, editable:true, edittype:"select", search:true, stype:'text', editoptions:{value:products}},
						   		{name:'envStatus',index:'envStatus', width:100, align:"center",editable:true,edittype:"select",search:false,editoptions:{value:status}},
						   		{name:'envOwner',index:'envOwner', width:100, align:"center",search:false,editable:true},		
						   		{name:'testFlag',index:'testFlag', width:50,align:"center",editable:true,edittype:"checkbox",search:false,editoptions:{value:"1:0"}},		
						   		{name:'envArchived',index:'envArchived', width:50, align:"center",editable:true,edittype:"checkbox",search:false,editoptions:{value:"1:0"}},
								{name:'use',index:'use', width:130, align:"center",editable:true,edittype:"select",search:false,editoptions:{value:"Development:Development;Regression:Regression;Testing:Testing;Performance:Performance;Custom:Custom"}},		   		
						   	],
				   	rowNum:10,
				   	rowList:[10,20,30],
				   	pager: '#envNavBar',
				    viewrecords: true,
				    sortorder: "asc",
				    caption:"Environment",
				    width:1240,
				    height:400,
				    editurl:"EnvManagerServlet?action=addEnv",
				    onSelectRow: function(envName) {
				    	jQuery("#envDetails").jqGrid('setGridParam',{url:"EnvManagerServlet?action=getEnvDetails&envName="+envName}).trigger('reloadGrid');
				    	jQuery("#envDbDetails").jqGrid('setGridParam',{url:"EnvManagerServlet?action=getEnvDbDetails&envName="+envName}).trigger('reloadGrid');
				    	selRowId = $('#env').jqGrid ('getGridParam', 'selrow'),
				        celValue = $('#env').jqGrid ('getCell', selRowId, 'envName');
				    },
				    //loadComplete: function() {
				       //$("div.ui-jqgrid-titlebar").css("background", '#3399FF');
				    	
				   //},
				    loadComplete : function() {
					    $("tr.jqgrow:odd").addClass('myAltRowClassEven');
					    $("tr.jqgrow:even").addClass('myAltRowClassOdd');
					},
				});
			 jQuery("#env").jqGrid('navGrid','#envNavBar',{edit:true,add:true,del:true,search:true,refresh:true},
			{
				editCaption: "Edit Environment",
				bSubmit: "Save",
				closeAfterEdit:true,
				reloadAfterSubmit:true,
				recreateForm:true,
				beforeShowForm : function (formid)
				{
					$("#envName", formid).attr("readonly","readonly");
				}
			},//Edit button options
			{
				height:280,
				addCaption: "Add New Environment",
				bSubmit: "Save",
				closeAfterAdd:true,
				reloadAfterSubmit:true,
				recreateForm:true,
				
				beforeSubmit:function(postdata,formid){
					var checkEnv = true;
					$.ajax({
						  url: "EnvManagerServlet?action=checkEnv", 
						  dataType: "json",
						  type: "POST",
						  async: false,
						  data: { envName : postdata.envName }, //creating json data and sending json data.. sent as json string. similar to req.setAttribute
						  success: function(data, result) { // success function data var is from response which comes from servlet
							  //alert(data);
							 //alert(result);
							  
							  if (data.checkEnvResult) // value comes true, envName exists.so we set checkEnv variable to false to return the failure message.
								  checkEnv = false;
						  }
					  });
					if(checkEnv == false)	
						return [checkEnv,"Environment Name already exist"]; 
					else
						return [checkEnv,""];
				},
			}, //Add button options
			{}, //delete options
			{
				caption: "Search",
				Find: "Find",
				closeAfterSearch: true,
				recreateForm:true,
				Reset:"Reset",
				searchOnEnter: true,
				sopt:['eq','cn']
			} //search options
			
		);
		
		//Show Archived Environments
		
		$( "input" ).change(function() 
			{
				if ( $( "#showArchived" ).is( ":checked" ) )
				{
					jQuery("#env").jqGrid('setGridParam',{url:"EnvManagerServlet?action=getArchivedEnv&envArchived=1"}).trigger('reloadGrid');
				}
				else
				{
					jQuery("#env").jqGrid('setGridParam',{url:"EnvManagerServlet?action=getArchivedEnv&envArchived=0"}).trigger('reloadGrid');
				}
								  
			}
		);
				
		//Delete the selected row from the Grid
		
		$("#del_env").click(function()
		{
 	 		var selRow = jQuery("#env").jqGrid('getGridParam','selrow');  //get selected rows id which is internal jqgrid rowid
     
      		if( selRow != null) 
				jQuery("#env").jqGrid('delGridRow',selRow,{caption:"Delete Environment",bSubmit:"Delete",url:"EnvManagerServlet?action=deleteEnv",reloadAfterSubmit:true});
		});
		
			
/****************************************Environment Details Grid *****************************************************************/
			  
			  
			//get all hostnames
			
			  $.ajax({
				  url: "EnvManagerServlet?action=getHostName", 
				  async: false, 
				  success: function(data, result) 
				  {  
					 $.each(data, function( index, value )
					{
	  					hostnames[value] = value; // this is same as object.property = value
					});
					if (!result)
						  alert('Failure to retrieve the Hostname.');
				  }
			  });
			  
			  
			//Get all product application
			
			  $.ajax({
				  url: "EnvManagerServlet?action=getProdApp", 
				  async: false, 
				  success: function(data, result)
				  {
					 $.each(data, function( index, value )
					 {
	  					prodApp[value] = value; // this is same as object.property = value
					}); 
					if (!result)
						  alert('Failure to retrieve the Product Applications.');
				  }
			  });
			
			
			
			
			  jQuery("#envDetails").jqGrid({
				   	url:"EnvManagerServlet?action=getEnvDetails",
					datatype: "json",
				   	colNames:['ID','Environment Name', 'Product Application', 'Folder','Host','Port','URL','Login','Refresh History','Comments'],
				   	colModel:[
				   		{name:'id',index:'id', align:"center", sortable:true,width:40,key:true,editoptions:{readonly:true},hidden:true},
				   		{name:'envName',index:'envName',align:"center", width:195, sortable:true,editable:true,editoptions:{readonly:true}},
				   		{name:'productApp',index:'productApp',align:"center", width:70, editable:true, edittype:"select",editoptions:{value:prodApp}},
				   		{name:'folder',index:'folder', width:280, align:"center",editable:true},
				   		{name:'host',index:'host', width:199, align:"center",editable:true,edittype:"select",editoptions:{value:hostnames}},		
				   		{name:'port',index:'port', width:95,align:"center",editable:true},		
				   		{name:'url',index:'url', width:350, align:"center", sortable:false,editable:true},
				   		{name:'login',index:'login', width:160, align:"center", editable:true},
				   		{name:'refhist',index:'refhist', align:"center", width:80,editable:true},
				   		{name:'comments',index:'comments', align:"center", width:30,editable:true},
				   		
				   	],
				   	rowNum:10,
				   	rowList:[10,20,30],
				   	pager: '#pager2',
				   	sortname: 'id',
				    viewrecords: true,
				    sortorder: "asc",
				    caption:"Environment Details",
				    width:1240,
				    height:300,
				    formoptions:{rowpos:6, elmprefix:'hello'},
				    	
				    editurl:"EnvManagerServlet?action=addEnvDetails",
				    	   
				});
			  jQuery("#envDetails").jqGrid('navGrid','#pager2',{edit:true,add:true,del:true},
					  {
						  editCaption: "Edit Environment Details",
						  bSubmit: "Save",
						  closeAfterEdit:true,
						  reloadAfterSubmit:true,
						  recreateForm:true,
				  
					  },//Edit button options
					  {
						  addCaption: "Add Environment Details",
						  bSubmit: "Save",
						  closeAfterAdd:true,
						  reloadAfterSubmit:true,
						  recreateForm:true,
						  beforeShowForm: function(form) {
    							form.find('input#envName').val(celValue);
    					  }
			  		  }
					);
			  			  
			  //Delete rows.
			  $("#del_envDetails").click(function()
				{  	
					var id = jQuery("#envDetails").jqGrid('getGridParam','selrow');
					if( id != null) 
						jQuery("#envDetails").jqGrid('delGridRow',id,{caption:"Delete Environment",bSubmit:"Delete",url:"EnvManagerServlet?envName="+urlEnvName+"&action=delEnvDetails&id="+id,reloadAfterSubmit:true});
					else 
						alert("Please Select Row to delete!");
				});
			  
			  
			
/****************************************Environment DB Details Grid *****************************************************************/
			
			
			  jQuery("#envDbDetails").jqGrid({
				   	url:"EnvManagerServlet?action=getEnvDbDetails",
					datatype: "json",
				   	colNames:['ID','Environment Name', 'Use', 'Host','Schema','Login','BOUT_Level','Custom','Refresh History'],
				   	colModel:[
				   		{name:'id',index:'id', align:"center", sortable:true,width:55,key:true,hidden:true},
				   		{name:'envName',index:'envName',align:"center", width:150, sortable:true,editable:true,editoptions:{readonly:true}},
				   		{name:'use',index:'use',align:"center", width:80,editable:true,editrules:{required:true}},
				   		{name:'host',index:'host', width:190, align:"center",editable:true,edittype:"select",editoptions:{value:hostnames},editrules:{required:true}},
				   		{name:'schema',index:'schema', width:70, align:"center",editable:true,editrules:{required:true}},
				   		{name:'login',index:'login', width:140, align:"center", editable:true,editrules:{required:true}},
				   		{name:'bout_level',index:'bout_level', width:140, align:"center", editable:true},
				   		{name:'custom',index:'custom', width:50,align:"center",editable:true},		
				   		{name:'refhist',index:'refhist', align:"center", width:230,editable:true},
				   	],
				   	rowNum:10,
				   	rowList:[10,20,30],
				   	pager: '#pager3',
				   	sortname: 'id',
				    viewrecords: true,
				    sortorder: "asc",
				    caption:"Environment Database Details",
				    width:1240,
				    height:300,
				    editurl:"EnvManagerServlet?action=addEnvDbDetails"
				});
			  jQuery("#envDbDetails").jqGrid('navGrid','#pager3',{add:true,del:true,edit:true,search:false},
					  {
						  editCaption: "Edit Environment DB Details",
						  bSubmit: "Save",
						  closeAfterEdit:true,
						  reloadAfterSubmit:true,
						  recreateForm:true,
				  
					  },//Edit button options
					  {
						  addCaption: "Add Environment DB Details",
						  bSubmit: "Save",
						  closeAfterAdd:true,
						  reloadAfterSubmit:true,
						  recreateForm:true,
						  beforeShowForm: function(form) {
    							form.find('input#envName').val(celValue);
    					  }//Add button options
			  		  }
			  		  
					);
			  
			  //Delete rows.
			  $("#del_envDbDetails").click(function()
				{
					var id = jQuery("#envDbDetails").jqGrid('getGridParam','selrow');
					if( id != null) 
						jQuery("#envDbDetails").jqGrid('delGridRow',id,{caption:"Delete Environment",bSubmit:"Delete",url:"EnvManagerServlet?envName="+urlEnvName+"&action=delEnvDbDetails&id="+id,reloadAfterSubmit:true});
					else 
						alert("Please Select Row to delete!");
				});
			
				  
	  });
	   
	</script>
	<style type="text/css">
		.myAltRowClassEven { background: #E0E0E0; border-color: #79B7E7; font: italic; font-family: sans-serif;}
		.myAltRowClassOdd { background: #CC3300; font: bold; font-family: Segoe Print, Tahoma, Comic Sans MS, Georgia}
	</style>

	<title>Environment Management System</title>
	 </head>
		 <body>
		 	
			<img src="logo.jpg" alt="Teoco Corporation Logo"></img>
			<h1><center>Environment Management System</center></h1>
			
			
<!-------------------------------------------- Table for Environments-------------------------->
			<br/>
			
			
			<input type="checkbox" name="showArchived" id="showArchived" value="show"/>
				<label for="showArchived">Show Archived Environments</label>
			 
			<div id="envDiv" align="left">
				
				<table id="env">
					
				
				</table>
				<div id="envNavBar"></div>
				
				<p>***To remove an entry from the above table, please edit a row and check the Archived checkbox</p>
				</div> 
			
			
			<br/>
			
<!----------------------------------------------- Table for Environments_Details ------------------------------------>
			<div id="envDetailsDiv" align="left">
				<table id="envDetails" ></table>
				<div id="pager2"></div>
			 </div>
			 
			<br/>
			
<!------------------------------------------------------ Table for Environment_DBDetails---------------------------------------->
			<div id="envDBDetailsDiv" align="left">
				<table id="envDbDetails" ></table>
				<div id="pager3"></div>
				 <a href="Add_Server_Screen.jsp">Add Server Details</a>
				<hr/>
			
			</div>
		<div id="footer" align="center" >
				Copyright © Teoco Corporation 2014
		</div>
		
	</body>
</html>