<!DOCTYPE html>

<html>
<header> </header>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<title>${user.name}'s Profile</title>
<body class="container">
	<div class="row">
		<div class="col-sm-1"></div>
		<div class="col-sm-10">
			<div class="row">
				<div class="col-sm-2">
					<form method="post">
						<input name="page" value="follow" type="hidden" /> <input
							name="username" value="${user.username}" type="hidden" />
						<button class="btn btn-outline-success">Follow/Unfollow</button>
					</form>
				</div>
				<div class="col-sm-2">
					<form method="post">
						<input name="page" value="block" type="hidden" /> <input
							name="username" value="${user.username}" type="hidden" />
						<button class="btn btn-outline-danger">Block/Unblock</button>
						
					<input placeholder="reason" name="reason" class="form-control" />
					</form>
				</div>
				<div class="col-sm-6"></div>
				<div class="col-sm-2">
					<form action="search">
						<button class="btn btn-outline-secondary">Search babble</button>
					</form>
				</div>
			</div>
			<div class="row">
				<h5 class="display-4">${user.name} @${user.username}</h5>
			</div>
			<div class="row">
				<blockquote class="blockquote">
					<p class="mb-0">Status: ${user.status}</p>
				</blockquote>
			</div>
			<div class="row">
				<div class="col-sm-10"></div>
				<form class="col-sm-2" action="create">
					<button class="btn btn-outline-primary">New Babble</button>
				</form>
			</div>
			<br />
			<#if babbles??>
			<#list babbles as babble>
			<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-8">
					<div class="card" style="width: 36rem;">
					<#if babble.type == "like">
					liked by ${user.name}
					</#if>
					<#if babble.type == "rebabble">
					rebabbled by ${user.name}
					</#if>
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
			<#if blocks??>
			YOU HAVE BEEN BLOCKED, REASON: ${blocks}<br />
			</#if>
			</#if>
	</div>
	<div class="col-sm-1"></div>
	</div>
</body>

</html>