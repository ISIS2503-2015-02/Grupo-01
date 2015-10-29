(function(){
    var TBC = angular.module('cond',[]);
    
      TBC.controller("getConductores", function($http, $scope){
        $scope.formulario = false;
        $scope.conductores = [];
        $scope.eliminar = function(index){
            var peti={
                    method: 'DELETE',
                    url: urlP +'/conductores/'+$scope.conductores[index].id,
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': usActual.authToken
                    }
                };
            $http.delete(peti).success(function(data, status, headers, config){
                $scope.conductores.splice(index,1);
            }).error(function(data, status, headers, config){
                
            });
        };
        $scope.mostrarFormulario = function(){
            $scope.formulario = true;
        };
        $scope.ocultar = function(){
            $scope.formulario = false;
        };
        $scope.addDriver = function(){
            console.log($scope.conductor);
            var peti={
                    method: 'POST',
                    url: urlP +'/conductores',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': usActual.authToken
                    },
                    data: JSON.stringify($scope.conductor)
                };
            $http.post(peti).success(function(data, status, headers, config){
              var conduc = {
              numeroIdentificacion : data.numeroIdentificacion,
              edad : data.edad,
              nombre : data.nombre,
              tipoId : data.tipoId,
              telefono : data.telefono,
              licenciaDeConduccion : data.licenciaDeConduccion,
              fechaVencimientoLicencia : data.fechaVencimientoLicencia
              };
              $scope.conductores.push(conduc);
              $scope.formulario = false;
              $scope.conductor={};
                
            }).error(function(data, status, headers, config){
                
            });
            $scope.$apply();
        };
        var peti={
                    method: 'GET',
                    url: urlP +'/conductores',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': usActual.authToken
                    },
                };
        $http.get(peti).success(function(data, status, headers, config){
            for(i = 0; i < data.length; i++){
              var conductor = {
              numeroIdentificacion : data[i].numeroIdentificacion,
              edad : data[i].edad,
              nombre : data[i].nombre,
              tipoId : data[i].tipoId,
              telefono : data[i].telefono,
              licenciaDeConduccion : data[i].licenciaDeConduccion,
              fechaVencimientoLicencia : data[i].fechaVencimientoLicencia
              };
              $scope.conductores.push(conductor);
            }
       }).
      error(function(data, status, headers, config) {
        // log error
      }); 
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

