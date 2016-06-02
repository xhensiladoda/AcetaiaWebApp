<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Benvenuti</title>
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
	    
	    .input-margin{
	   		 margin:10px auto;
	   		 width: 140px;
	    }
	    .button-margin{
	    	margin:5px auto;
	   		width: 140px;
	    }
	    
	    .nav-margin{
	    	margin:80px auto;
	    }
	    
	    .div-margin{
	    	margin:15px;
	    }
	    	    
	    span{
	    	background:#A69FA1;	
	    }
	</style>
</head>
<body>
	<div class="col-md-1">
		<div class="collapse navbar-collapse navbar-fixed-top ">
	         <form action="LogoutServlet" method="post" class="navbar-form navbar-right">
				<button class="btn btn-default btn-sm btn-primary btn-block button-margin" type="submit">Logout</button>
			</form>
	    </div>
	</div>
	<div class="col-md-6">
		<h1>ACETAIA</h1>
		<p>Per accedere alle diverse funzionalit&#224; selezionare l'operazione desiderata 
		tra quelle proposte sulla destra.<br>
		Per effettuare una ricerca tra le operazioni effettuate in passato selezionare 
		uno o pi&#249; parametri tra quelli presentati facendo click su <em>Ricerca</em> in basso a destra.<br><br>
		</p>
		<div class="col-md-8 col-md-offset-2">
		<div id="mycarousel" class="carousel slide" data-ride="carousel">
	        <div class="carousel-inner">
	          <div class="item active">
	            <img src="images/1_0.jpg" alt="First slide">
	          </div>
	          <div class="item">
	            <img src="images/2_0.jpg" alt="Second slide">
	          </div>
	          <div class="item">
	            <img src="images/3_0.jpg" alt="Third slide">
	          </div>
	          <div class="item">
	            <img src="images/4_0.jpg" alt="Forth slide">
	          </div>
	        </div>
	        <a class="left carousel-control" href="#mycarousel" data-slide="prev">
	          <span class="glyphicon glyphicon-chevron-left"></span>
	        </a>
	        <a class="right carousel-control" href="#mycarousel" data-slide="next">
	          <span class="glyphicon glyphicon-chevron-right"></span>
	        </a>
	  </div>
	 </div> 	     
    </div>
    <div class="col-md-3 col-md-offset-1 nav-margin">
      <div class="list-group">
  		<a href="Aggiunta" class="list-group-item">
  		  <h4 class="list-group-item-heading">Aggiunta</h4>
  		  <p class="list-group-item-text">Inserimento di una determinata quantità in un barile.</p>
  		</a>
  		<a href="Prelievo" class="list-group-item">
  		  <h4 class="list-group-item-heading">Prelievo</h4>
  		  <p class="list-group-item-text">Prelievo di una determinata quantità da un barile.</p>
  		</a>
  		<a href="Rabbocco" class="list-group-item">
  		  <h4 class="list-group-item-heading">Rabbocco</h4>
  		  <p class="list-group-item-text">Inserimento di una determinata quantità in un barile, dopo averlo prelevato da un altro barile.</p>
  		</a>
  		<a href="Misurazione" class="list-group-item">
  		  <h4 class="list-group-item-heading">Misurazione</h4>
  		  <p class="list-group-item-text">Misurazione del livello, della densità e dell'acidità di un barile.</p>
  		</a>
  		<a href="Degustazione" class="list-group-item">
  		  <h4 class="list-group-item-heading">Degustazione</h4>
  		  <p class="list-group-item-text">Scansione di una scheda con vari parametri di degustazione per un barile.</p>
  		</a>	
	</div>
	<div class="center"><button class="btn btn-warning btn-sm button-margin" data-toggle="modal" data-target="#myModal">Ricerca</button></div>
    </div>
	<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h4 class="modal-title">Effettua una ricerca compilando uno dei campi sottostanti...</h4>
			      </div>
			      <form method="post" action="Ricerca">
			      <div class="modal-body">
					<div class="form-group input-margin"><span class="label">Barile</span>
						<input class="form-control" type="text" placeholder="numero barile" name="barile"></input>
					</div>
				    <div class="form-group input-margin"><span class="label">Batteria</span>
				    	<input class="form-control" type="text" placeholder="numero batteria" name="batteria"></input>
				    </div>
				     <div class="form-group input-margin"><span class="label">Inizio Periodo</span>
				     	<input class="form-control" type="text" placeholder="aaaa/mm/gg" name="periodo_i"></input>
				     </div>
				    <div class="form-group input-margin"><span class="label">Fine Periodo</span>
				    	<input class="form-control" type="text" placeholder="aaaa/mm/gg" name="periodo_f"></input>
				    </div>
				     <div class="form-group input-margin"><span class="label">Operazione</span>
				    	<select name="intervento" class="form-control">
				    		<option value="vuoto">-- Qualsiasi --</option>
							<option value="aggiunta">Aggiunta</option>
							<option value="prelievo">Prelievo</option>
							<option value="rabbocco">Rabbocco</option>
							<option value="misurazione">Misurazione</option>
							<option value="degustazione">Degustazione</option>
						</select>
					</div>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        <button type="submit" class="btn btn-primary">Invio</button>
			      </div>
			     </form>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->	 
</body>
</html>