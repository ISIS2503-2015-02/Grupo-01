$(document).ready(function(){

$('.login').click(function(){
        var user = $('.user').val();
        var pass = $('.pass').val();

            if (user === "admin" && pass === "123")
            {
                window.location.href="/admin.htmml";
            }
            else
            {
                $('.errorLogin').show();
                //window.location.href="/search/"+data.id;
                }
    });

});