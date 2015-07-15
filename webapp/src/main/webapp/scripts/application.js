$(document).ready(function() {
	/// here...
	$('.edit').text("editar");
	$('.loginPanel h2').text('Acceso');
	$('#username2-w-lbl').text('Usuario');
	$('#password3-w-lbl').text('Contraseña');
	$('#signInForm1 .btn-primary').text('entrar');
	$('#signInForm1 .btn-default').text('borrar');
	$('#signInForm1 input:checkbox').text('Recordarme');

	$('.ViewLinkItemTitle:contains("Table")').text('Tabla');
	$('.ViewLinkItemTitle:contains("Hide")').text('ocultar');
	$('input:contains("cancel")').text('cancelar');

});