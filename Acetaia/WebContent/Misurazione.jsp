<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html "WebContent/index.html"PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Misurazione</title>
	<script type="text/javascript" src="js/jquery-2.0.3.js"></script>
	<!-- <script src="http://code.jquery.com/jquery-latest.js"></script> -->
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/signin.css" rel="stylesheet" media="screen">
	<script type="text/javascript" src="js/bootstrap.js"></script>
	<style type="text/css">	
		.bodycontainer { max-height: 222px; width: 100%; margin: 0; overflow-y: auto; }
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
		<h1>MISURAZIONE</h1>
	</div>
	<form method="post" action="Misurazione">
	 	<div class="col-md-5 col-md-offset-3">
			<h5><i>Selezionare un barile</i></h5>
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
				    <thead>
				        <tr>
				            <th width="15%">Barile</th>
				            <th width="14%">Capacità</th>
				            <th width="15%">MaxLivello</th>
				            <th width="14%">Livello</th>
				            <th width="14%">Legno</th>
				            <th width="30%">Batteria</th>
				        </tr>
				    </thead>
				</table>
				<div class="bodycontainer scrollable">
					<table class="table table-hover table-striped table-condensed table-scrollable table-bordered">
					    <tbody>
					    	 <c:forEach var="i" begin="0" end="${r}">
							<tr>
								<c:forEach var="j" begin="0" end="${c}">
									<td width="15%"> <c:out value="${o[j].getData(i)}"/></td>
								</c:forEach>
									<td width="11%"> <input class="radio-input" type="radio" name="sel" value ="${o[0].getData(i)}"/></td> 
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
 		<div class="col-md-5 col-md-offset-3">
			<h5><i>Indica il parametro da misurare</i></h5>     
		     <div class="center">
		     	<select name ="parametro" class="form-control">
					<option value="empty">--</option>
					<option value="livello">livello nel barile</option>
					<option value="acidita">titolazione acidità</option>
					<option value="densita">misura densità</option>
				</select>
			</div>
		    <h5><i>Inserire nuovo valore del parametro</i></h5> 
	        <div class="center"><input class="input-margin form-control" type="text" name="valore" placeholder="quantità..."/></div>
       		<div class="center"><button class=" button-margin btn btn-sm btn-primary btn-block" type="submit">Invio</button></div> 
		</div>     
	</form>
</body>
</html>