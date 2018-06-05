<!DOCTYPE html>

<html>
<header> </header>
<title>Chat</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<body class="container">
	<#if messages??>
	Messages with ${otheruser.name} (<a href="profile?username=${otheruser.username}">@${otheruser.username}</a>)
			<div class="row">
				<div class="col-sm-2"></div>
				<div class="col-sm-8">
					<div class="card" style="width: 45rem;">
						<div class="card-body">
							<#list messages as message>
								<h5 class="card-title"><#if message.sender == user.username>${user.name}<#else>${otheruser.name}</#if>@${message.sender}</h5>
								<p class="card-text">${message.text}</p>
								<br />
							</#list>
							<form method="post">
								<input name="page" value="send" type="hidden" /> <input
									class="w-80 p-3 form-control" name="text" placeholder="messeseseseage"
									type="text" /> <br />
								<button class="btn-lg btn-block btn btn-primary">Send Message</button>
							</form>
						</div>
					</div>
				</div>
				<div class="col-sm-2"></div>
			</div>
			<br />
			<#else>
			</#if>
</body>

</html>