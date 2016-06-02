<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Ricerca</title>
	<script type="text/javascript" src="js/jquery-2.0.3.js"></script>
	<!-- <script src="http://code.jquery.com/jquery-latest.js"></script> -->
	<link href="css/bootstrap.css" rel="stylesheet" media="screen">
	<link href="css/signin.css" rel="stylesheet" media="screen">
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<%@ page import="model.DBList" %>
	<%@ page import="controller.Ricerca" %>
	<script type="text/javascript">
	$(document).ready(function() {
		$('[rel="popover"]').popover();
		$('[role="dialog"]').dialog();
	});
	</script>
	<style type="text/css">	
		.bodycontainer { max-height: 370px; width: 100%; margin: 0; overflow-y: auto; }
		.table-scrollable { margin: 0; padding: 0; }
		.center{
	   	  text-align: center;
	   	  margin:8px auto;
	   	  width:40%;
	    }
	    
	    h1{
	   		 margin:20px auto;
	   		 text-align: center;
	   		  color:#C9A457;
	    }
	    
	    h5{
	    	 text-align: center;
	   		 margin:10px auto;
	    }
	    
	    .input-margin{
	   		 margin:5px auto;
	   		 width: 140px;
	    }
	    .button-margin{
	    	margin:5px auto;
	   		width: 140px;
	    }
	</style>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body> 
	<div class="collapse navbar-collapse navbar-fixed-top">
          <ul class="nav navbar-nav">
            <li><a href="Welcome.jsp">Home</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Operazioni <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="Aggiunta">Aggiunta</a></li>
                <li><a href="Prelievo">Prelievo</a></li>
                <li><a href="Rabbocco">Rabbocco</a></li>
                <li><a href="Misurazione">Misurazione</a></li>
                <li><a href="Degustazione">Degustazione</a></li>
              </ul>
            </li>
          </ul>
         <form action="LogoutServlet" method="post" class="navbar-form navbar-right">
			<button class="btn btn-default btn-sm btn-primary btn-block button-margin" type="submit">Logout</button>
		</form> 
    </div>
	<div class="col-md-5 col-md-offset-3">
		<h1>RICERCA</h1>
	</div>
 	<div class="col-md-5 col-md-offset-3">
		<div class="table-responsive">
			<table class="table table-striped table-hover table-condensed">
			    <thead>
			        <tr><th width="3%"></th>
			        	<th width="16%">Barile</th>
			            <th width="19%">Batteria</th>
			            <th width="13%">Data</th>
			            <th width="25%">Operazione</th>
			            <th width="20%">Informazioni</th>
			            <th width="20%"></th>
			        </tr>
			    </thead>
			</table>
			<div class="bodycontainer scrollable">
				<table class="table table-hover table-striped table-condensed table-scrollable table-bordered">
				    <tbody>
				    	<c:forEach var="i" begin="0" end="${rows}">
									<tr>
										<c:forEach var="j" begin="0" end="${columns}">
											<td width="20%"><c:out value="${storico[j].getData(i)}"/></td>
										</c:forEach>
							            <td width="20%">
							            	<button class="btn btn-default btn-sm button-margin" data-toggle="modal" data-target="#${i}">Info</button>
							            </td>
									</tr>
						</c:forEach>
				</tbody>
			 </table>
		 </div>
	</div>
</div>


				   		 <%
				   		 	DBList data[]=(DBList[])session.getAttribute("other_data");
				   		 		   		 		   			DBList nome[]=(DBList[])session.getAttribute("col_names");
				   		 		   		 		   		 	int cont=0;
				   		 		   		 		   		 	int rows=Integer.parseInt(session.getAttribute("rows").toString());
				   		 		   		 		   		 	while(cont<rows+1){
				   		 %>				
				   		  <div class="modal fade" id="<%=cont%>" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<h4 class="modal-title">Più Informazioni su quest'operazione</h4>
										</div>							
										<div class="modal-body">
											<% 	int i=0;
												for(; i<data[cont].getSize(); i++){
													if(data[cont].getData(i).endsWith("jpg")){ 
													String url=data[cont].getData(i).toString();
													url="degustazione/"+url.substring(url.lastIndexOf('/')+1, url.length());
											%>			
												<a href="#" id="example" class="popover-left" rel="popover" data-html="true" data-content="<img src='<%=url%>'/>">Scheda Degustazione</a>
												<% }else{%>
													<%=nome[cont].getData(i)%>: <%=data[cont].getData(i)%>
												<% } //fine else%>
											<br>
											<%} //fine for%>
									 </div>
									 <div class="modal-footer">
			       						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>			       
			    					 </div>
								 </div>
							</div>
						</div>
					<%cont++;} //fine while%>
		
</body>
</html>