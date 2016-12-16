<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Admin Page</title>
<!--Import Google Icon Font-->
<link href="http://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<!--Import materialize.css-->
<!--   <link type="text/css" rel="stylesheet" href="/static/css/materialize.min.css"  media="screen,projection"/>-->
<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>
	<!--Import jQuery before materialize.js-->
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<!--<script type="text/javascript" src="/static/js/materialize.min.js"></script>-->
	<!-- Compiled and minified CSS -->
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/css/materialize.min.css">

	<!-- Compiled and minified JavaScript -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.8/js/materialize.min.js"></script>

	<nav>
	<div class="nav-wrapper">
		<ul id="nav-mobile" class="right hide-on-med-and-down">
			<li><a href="/work/admin_logout">Log Out</a></li>
		</ul>
	</div>
	</nav>


	<div class="container">
		<div class="card-panel teal lighten-2">
			<h4 align="center">Resize your Images</h4>
		</div>

		<div class="row">
			<div class="col s12">
				<ul class="tabs">
					<li class="tab col s3"><a class="active" href="#test1">Upload
							Image</a></li>
					<li class="tab col s3"><a href="#test2"
						onclick="return(fetchImages());">View Images</a></li>
				</ul>
			</div>
			<div id="test1" class="col s12">
				<div class="row ">
					<form action="/ImageResizer/ImageResizer" id="uploadFile"
						onsubmit="return(uploadValidate());" enctype="multipart/form-data"
						method="POST" name="uploadFile">
						<div class="file-field input-field col s4 offset-s1">
							<div class="btn">
								<span>File</span> <input type="file" name="datafile"
									id="file_name">
							</div>
							<div class="file-path-wrapper">
								<input class="file-path validate" type="text">
							</div>
						</div>

						<button class="btn waves-effect waves-light col s2 offset-s1"
							type="submit" name="action">
							Submit <i class="material-icons right">send</i>
						</button>
					</form>

					<div id="uploadLoader" style="display:none;" class="row">
						<div class="col s12 m4 center"></div>

						<div class="col s12 m4 center">

							<div class="preloader-wrapper active">
								<div class="spinner-layer spinner-red-only">
									<div class="circle-clipper left">
										<div class="circle"></div>
									</div>
									<div class="gap-patch">
										<div class="circle"></div>
									</div>
									<div class="circle-clipper right">
										<div class="circle"></div>
									</div>
								</div>
							</div>

						</div>
						<div class="col s12 m4 center"></div>
					</div>

					<img class="materialboxed" style="display: none;" id="Displayimg"
						width="650" src="">

				</div>
			</div>
			<div id="test2" class="col s12">
				<div id="loader" class="row">
					<div class="col s12 m4 center"></div>

					<div class="col s12 m4 center">

						<div class="preloader-wrapper active">
							<div class="spinner-layer spinner-red-only">
								<div class="circle-clipper left">
									<div class="circle"></div>
								</div>
								<div class="gap-patch">
									<div class="circle"></div>
								</div>
								<div class="circle-clipper right">
									<div class="circle"></div>
								</div>
							</div>
						</div>

					</div>
					<div class="col s12 m4 center"></div>
				</div>
				<div id="result"></div>
			</div>
		</div>


	</div>

	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$('.materialboxed').materialbox();
			$('ul.tabs').tabs();
		});

		function uploadValidate() {
			var file = document.getElementById('file_name');
			var fileName = file.value;
			var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			ext = ext.toLowerCase();
			if ((ext == "jpg" || ext == "jpeg" || ext == "png" || ext == "bmp")) {
			}

			else {
				alert("Please upload image files jpeg/png/bmp!")
				return false;
			}

			var form = $('form')[0]; // You need to use standart javascript object here
			var formData = new FormData(form);
			$("#uploadLoader").show();
			$.ajax({
				url : "/ImageResizer/ImageResizer",
				type : 'POST',
				data : formData,
				async : true,
				success : function(response) {
					$('#Displayimg').attr("src", response);
					$("#uploadLoader").hide();
					$('#Displayimg').show();

				},
				cache : false,
				contentType : false,
				processData : false
			});
			return false;
		}

		function fetchImages() {

			$
					.ajax({
						url : "/ImageResizer/DisplayImages",
						type : 'POST',
						data : {},
						async : true,
						success : function(response) {
							var urls = response.split(",");
							urls = urls.slice(1, urls.length);
							var table = "<table><tr>";
							if (urls != null) {
								$
										.each(
												urls,
												function(index, value) {
													if (index % 3 == 0) {
														table += "</tr><tr>";
													}
													table += '<td> <img class="materialboxed" width="30%" src='
															+ value + '></td>'
												});
								table += "</tr></table>"
								$("#loader").hide();
								$("#result").html(table);
								$('.materialboxed').materialbox();
							}
						},
						cache : false,
						contentType : false,
						processData : false
					});
			return false;
		}
	</script>
</body>
</html>