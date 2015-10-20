 
   (function(){
    var TBC = angular.module('us',[]);
   
   TBC.directive('registro', function(){
       return{
           restrict : 'E',
           templateUrl: 'partials/registro.html',
           controller: 'usuario'
       }; 
    });
   
   TBC.directive('usuario',function(){
        return {
            restric : 'E',
            templateUrl:'partials/usuario.html',
            controller:'usuario'
        };
    });
    
    TBC.controller('usuario',function($http, $scope, $location){
    $scope.active = {};
    $scope.crearReserva = false;
    $scope.verReserva = false;
    
    
    $scope.login = function(){
        console.log(JSON.stringify($scope.user));
        $http.post('http://localhost:9000/usuarios/login',JSON.stringify($scope.user)).success(function(data,headers){
            $scope.active = data;
            usActual = data;

            console.log($scope.active);
            
        }).error(function(data, headers){
            
        });
    };
    $scope.logout = function(){
        
    };
    $scope.reservar = function(){
        
        $scope.reserva.ruta.tipo = "Ruta Mobibus";
        $scope.reserva.usuarioId = usActual.numeroIdentificacion;
        console.log($scope.active);
        console.log(JSON.stringify($scope.reserva));
        $http.put('http://localhost:9000/usuarios/reserva',JSON.stringify($scope.reserva)).success(function(data,headers){
            console.log(data);
            
        }).error(function(data, headers){
            console.log(data);
        });
    };
    $scope.accioncrear = function(){
        if($scope.crearReserva === false)
            $scope.crearReserva = true;   
        else
            $scope.crearReserva = false;
        
    };
    
    $scope.verReservas = function(){
        $scope.reservasus = [];
        $http.get('http://localhost:9000/usuarios/'+usActual.numeroIdentificacion + '/reservas').
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
                    $scope.cancelarRes = function(ind){
                    $http.delete('http://localhost:9000/reservas/'+$scope.reservasus[ind].id).
                        success(function(data, status, headers, config) {
                        
                });
                }       
    }
            });
        }
    
    $scope.accionver = function(){
        if($scope.verReserva === false){
            $scope.verReserva = true;
            $scope.verReservas();
        }
         else
            $scope.verReserva = false;
    };
    
    
    $scope.addUser=function(){
            $http.post('http://localhost:9000/usuarios', JSON.stringify($scope.usuario)).success(function(data,headers){
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
 
            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
};
 
TBC.directive("compareTo", compareTo);
    
})();


