<!DOCTYPE html>

<html>
<header> </header>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<title>Create a new Babble</title>
<body class="container">
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4 card" style="width: 36rem;">
			<div class="card-body">
				<form method="post">
					<h5 class="card-title">create a new babble!</h5>
					<input name="page" value="create" type="hidden" />
					<textarea class="w-100 p-3 form-control" name="value"
						placehoder="Pour your mind" type="text"></textarea>
					<br />
					<button class="float-right btn btn-primary">create babble</button>
				</form>
			</div>
		</div>
		<div class="col-sm-4"></div>
	</div>
</body>

</html>