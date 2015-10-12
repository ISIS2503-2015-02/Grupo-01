/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){

$('.registro').click(function(){
        var nombre = $('.nombre').val();
        var cedula = parseInt($('.cedula').val());
        var telefono = $('.telefono').val();
        var age = parseInt($('.edad').val());
        var condition = $('.condicion').val();
        var tipo = $('.condicion').val();

        var toSend = {  "identificacion" : cedula,
		"edad" : edad,
		"nombre" : nombre,
		"tipoId" : tipo,
		"telefono" : telefono,
		"condicion": condition
	};

        var path = '/usuarios';
        var request;
        request = $.ajax({
            type: 'POST',
            url: path,
            data: JSON.stringify (toSend),
            success: function(data) {
            if (JSON.stringify(data)=="{}")
            {
                $('.errorLogin').show();
            }
            else
            {
                //window.location.href="/search/"+data.id;
                window.location.href="/";
            }
             },
            contentType: "application/json",
            dataType: 'json'
        });

    });

});