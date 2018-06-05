<!DOCTYPE html>

<html>
<header> </header>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<title>a babble!!</title>
<body class="container">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6 card" style="width: 36rem;">
			<div class="card-body">
				<h5 class="card-title">
					${user.name} <b> <a href="profile?username=${user.username}">@${user.username}</a>
					</b>
				</h5>
				<input class="w-100 p-3 form-control-plaintext" name="blob"
					value="${babble.text}" type="text" readonly /> <br />
				<div class="float-right">
					${babble.upvotes}Likes/${babble.downvotes}Dislikes/
					${babble.rebabbles}Rebabbles</div>
				<br>
				<div class="float-right">${babble.created}</div>
				<br> <br>
				<div class="row">
					<form class="col-sm-2" method="post">
						<input name="page" value="like" type="hidden" /> <input name="id"
							value="${babble.id}" type="hidden" />
						<#if like==1>
						<button class="float-left btn btn-success">Like</button>
						<#else>
						<button class="float-left btn btn-outline-success">Like</button></#if>
					</form>

					<form class="col-sm-2" method="post">
						<input name="page" value="dislike" type="hidden" /> <input
							name="id" value="${babble.id}" type="hidden" />
						<#if like==-1>
						<button class="float-left btn btn-danger">Dislike</button>
						<#else>
						<button class="float-left btn btn-outline-danger">Dislike</button></#if>
					</form>

					<form class="col-sm-2" method="post">
						<input name="page" value="rebabble" type="hidden" /> <input
							name="id" value="${babble.id}" type="hidden" />
						<#if rebabble==1>
						<button class="float-left btn btn-secondary">Rebabble</button>
						<#else>
						<button class="float-left btn btn-outline-secondary">Rebabble</button></#if>

					</form>
					<div class="col-sm-4"></div>
					<form class="col-sm-2" method="post">
						<input name="page" value="delete" type="hidden" /> <input
							name="id" value="${babble.id}" type="hidden" />
						<button class="float-right btn btn-danger">Delete</button>
					</form>
				</div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</body>
</html>