   (function(){
    var TBC = angular.module('us',[]);

TBC.directive('registro', function(){
       return{
           restrict : 'E',
           templateUrl: 'partials/registro.html',
           controller: 'registro'
       };
   });

   Tdirective('usuario',function(){
        return {
            restric : 'E',
            templateUrl:'partials/usuario.html',
            controller:'usuario'
        };
    });

    TBCtroller('usuario',function($http, $scope, $location){
    $scope.active = {};
    $scope.crearReserva = false;
    $scope.verReserva = false;


    $scopeifunction(){
        console.log(JSON.stringify($scope.user));
        $http.post(urlP + '/login',JSON.stringify($scope.user)).success(function(data,headers){
            $scope.active = data;
            document.cookie="id=" + data.numeroIdentificacion;
            document.cookie="token=" + data.authToken;

            console.log(getCookie("id"));
            if(data.rol==='admin'){
              var admin="admin.html"
              function redireccionar()
              {
              location.href=admin;
              }
              setTimeout ("redireccionar()", 20000);
            }
        }).error(fu headers){

        });
    };
    $scope.ltion(){
        console.log(document.cookie);
        var peti={
                    method: 'POST',
                    url: urlP +'/logout',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify({numeroIdentificacion:getCookie("id")})


                };
        console.log(peti);
        $http(peti).success(function(data,h
            console.log(data);
            console.log(":)")
        }).error(function(data, headers){
            console.log(data);
            console.log(":(")
        });
    };
    $scope.reservar = function(){

        $scope.reserva.ruta.tipo = "Ruta Mobibus";
$scope.reserva.usuarioId = getCookie("id");
        console.log($scope.active);
        console.log(JSON.stringify($scope.reserva));

        var peti={
                    method: 'PUT',
            url: urlP +'/usuarios/reserva',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify($scope.reserva)


                };
        $http(peti).success(function(data,headers){
            console.log(data);

        }).error(function(data, headers){
            console.log(d  });
    };
    $scope.accioncrear = function(){
        if($scope.crearReserva === false)
            $scope.crearReserva = true;
        else
            $scope.crearReserva = false;

    };

    $scope.verRevas = function(){
        $scope.reservasus = [];
     eti={
                method: 'GET',
                    url: urlP +'/usuarios/'+getCookie("id") + '/reservas',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },


                };
        $http(peti).
            success(function(data, status, headers, config) {
                console.log(data);
                for(i = 0; i < data.length; i++){
                    var res = {
                        id : data[i].id,
                        ubicacionorigen : data[i].ruta.ubicaiconOrigen,
                        ubicaciondestino : data[i].ruta.ubicacionDestino,
                        fecha : data[i].fecha,
                        precio : data[i].costo,
                        estado : data[i].estado,
                        turno : data[i].turno
                    };
                    $scope.reservasus.push(res);
                    $scope.cancelarRes = funcion(ind){
                    $http.delete(urlP + '/reservas/'+$scope.reservasus[ind].id).
                        success(function(data, status, headers, config) {

                });
                }
    }
            });
        }

    $scope.accionver = fcope.verReserva === false){
          e.verReserva = true;
            pe.verReservas();
        }
         else
            $scope.verReserva = false;
    };

    });

    TBC.controller('registro',function($http, $scope, $location){
        $scope.addUser=function(){
            $http.polocalhost0/usuarios', JSON.stringify($scope.usuario)).success(function(data,headers){
                $scope.usuario={};
                window.location.assign("/FrontendTBC/index.html");
            });
        };
    });

   var compareTo = function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
       },
    link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };

       atch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
};

TBC.directive("compareTo", compaeTo);

})();
