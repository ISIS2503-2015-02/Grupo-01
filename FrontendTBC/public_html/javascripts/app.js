/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(){
    var TBC = angular.module('TBC',[]);
    
    TBC.directive('tablaTranvia', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-tranvia.html',
            controller: 'getTranvia'
        };
    });
 
    TBC.controller("getTranvia", function($http, $scope) {
    $http.get('http://localhost:9000/tranvias').
      success(function(data, status, headers, config) {
       
        
        $scope.tranvias = [];
        for(i = 0; i < data.length -1; i++){
              var tran = {
              estado : data[i].estado,
              id : data[i].id,
              panico : data[i].panico,
              longitud : data[i].posiciones[data[i].posiciones.length-1].longitud,
              latitud : data[i].posiciones[data[i].posiciones.length-1].latitud,
              accidente : data[i].presionChoque,
              temperatura : data[i].temperatura
            };
            $scope.tranvias.push(tran);
        }
        $scope.map = {
		center: {
			latitude: 40.454018, 
			longitude: -3.509205
		}, 
		zoom: 12,
		options : {
			scrollwheel: false
		},
		control: {}
	};
	$scope.markers = [];
        for(i = 0; i < data.length -1; i++){
            pos = {
              id : data[i].id,
              position : {
              longitude : data[i].posiciones[data[i].posiciones.length-1].longitud,
              latitude : data[i].posiciones[data[i].posiciones.length-1].latitud
              },
              options: {
			draggable: false
              }
            };
        $scope.markers.push(pos);
        }
      }).
      error(function(data, status, headers, config) {
        // log error
      });
    });
    
    TBC.directive('toolbar', function(){
        return{
            restrict:'E',
            templateUrl: 'partials/toolbar.html',
            controller:function(){
                this.tab=0;
                this.selectTab=function(setTab){
                    this.tab=setTab;
                };
                this.isSelected=function(tabParam){
                    return this.tab===tabParam;
                };
            },
            controllerAs:'toolbar'
        };
    });
    
    TBC.directive('registrar', function(){
       return{
           restrict : 'E',
           templateUrl: 'partials/resitrar.html',
           controller: function(){
               this.show = false;
               this.isClicked = function(){
                 this.show = true;  
               };
               this.unshow = function(){
                 this.show = false;  
               };
           },
           controllerAs: 'registrar'
       }; 
    });
    
    TBC.directive('registro', function(){
       return{
           restrict : 'E',
           templateUrl: 'partials/registro.html',
           controller: 'registro'
       }; 
    });
    
    TBC.controller('registro',['$scope', function($http, $scope){ 
    $scope.addUser=function(){
            console.log('entro');
            $http.post('http://localhost:9000/usuarios', JSON.stringify($scope.usuario)).success(function(data,headers){
                $scope.usuario={};
                
            });
        };	       
}]);
    
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


