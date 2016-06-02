<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/signin.css" rel="stylesheet" media="screen">
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
	    }
	    
	    .input-margin20{
	    	 margin:20px auto;
	    }
	    
	    .input-margin5{
	   		 margin:5px auto;
	    }
	</style>
</head>
<body>
<div class="container">
      <form method="post" action="LoginServlet" class="form-signin">
        <h2 class="form-signin-heading">Effettuare il Login</h2>
        <input type="text" class="form-control" placeholder="Username" name="username" required autofocus>
        <input type="password" class="form-control" placeholder="Password" name="password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
      </form>
	</div>
 </body>
</body>
</html>