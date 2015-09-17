$(document).ready(function(){

$('.reserv').click(function(){
        
        var toSend = {
    "usuarioId": "102837546",
    "estado" : "En espera",
    "fecha": "11/4/2014",
    "costo": 1445.3,
    "turno": 1,
    "ruta":{
        "ubicacionOrigen": "Cr57A98",
        "ubicacionDestino": "Unicentro",
        "tiempoTrayecto": 0.0,
        "tipo": "Ruta movibus",
        "terminado": "En curso",
        "accidente": "Normal"
        }
    };

        var path = '/usuarios/reserva';
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