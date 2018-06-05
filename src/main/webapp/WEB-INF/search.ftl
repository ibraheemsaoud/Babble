<!DOCTYPE html>

<html>
<header> </header>
<title>Search</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<body class="container">
<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4 card" style="width: 36rem;">
			<div class="card-body">
				<form method="post">
					<input name="page" value="search" type="hidden" />
					<input placeholder="search for whatever you want" class="w-100 p-3 form-control" name="search" type="text" />
					<br />
					<button class="btn-lg btn-block btn btn-primary">Search</button>
				</form>
			</div>
		</div>
		<div class="col-sm-4"></div>
	</div>
	<#if babbles??>
	Search results for: ${text}
			<#list babbles as babble>
			<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-8">
					<div class="card" style="width: 45rem;">
						<div class="card-body">
							<h5 class="card-title">@${babble.creator}</h5>
							<p class="card-text">${babble.text}</p>
							<a href="babble?id=${babble.id}" class="btn btn-primary">Check the babble!</a>
						</div>
					</div>
				</div>
				<div class="col-sm-2"></div>
			</div>
			<br />
			</#list>
			<#else>
			</#if>
</body>

</html>