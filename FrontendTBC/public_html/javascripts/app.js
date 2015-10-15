/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(){
    var TBC = angular.module('TBC',[]);
    
    
    /**
     * Directiva y controllador de buses 
     */
    
    TBC.directive('tablaBuses', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-buses.html',
            controller: 'buses'
        };
    });
    
    TBC.controller("buses", function($http, $scope){
       $http.get('http://localhost:9000/mobibuses').success(function(data, status, headers, config){
          $scope.buses = [];
            for(i = 0; i < data.length; i++){
              var bus = {
              estado : data[i].estado,
              id : data[i].id,
              panico : data[i].panico,
              longitud : data[i].posiciones[data[i].posiciones.length-1].longitud,
              latitud : data[i].posiciones[data[i].posiciones.length-1].latitud,
              temperatura : data[i].temperatura
              };
              bus.isAccidentado = function(){
                return bus.estado === "Accidentado";  
              };  
              $scope.buses.push(bus);
              console.log($scope.buses);
            }
            var mapOptions = {
                zoom: 8,
                center: new google.maps.LatLng(4.60, -74.08),
                mapTypeId: google.maps.MapTypeId.TERRAIN
            }
        $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

        $scope.markers = [];
    
        var infoWindow = new google.maps.InfoWindow();
    
        var createMarker = function (info){
        
            var marker = new google.maps.Marker({
                map: $scope.map,
                position: new google.maps.LatLng(info.posiciones[info.posiciones.length-1].longitud, info.posiciones[info.posiciones.length-1].latitud),
            });
        
        marker.content = '<div class="infoWindowContent"> Bus ' + info.id + '</div>';
        
        google.maps.event.addListener(marker, 'click', function(){
            infoWindow.setContent(marker.content);
            infoWindow.open($scope.map, marker);
        });
        
        $scope.markers.push(marker);
     
        }  

        
        for (i = 0; i < data.length; i++){
            createMarker(data[i]);
        }
       }).
      error(function(data, status, headers, config) {
        // log error
      }); 
    });
    
    /**
     * Directiva y controlador de estaciones vcub
     */
    TBC.directive('tablaVcub', function(){
        return {    
            restrict:'E',
            templateUrl:'partials/tabla-vcub.html',
            controller: 'vcub'
        };
    });
    
    TBC.controller("vcub", function($http, $scope){
        $scope.llenarTodas = function(){
            
        };
        $scope.estaciones = [];
        $http.get('http://localhost:9000/vcubs').succes(function(data, status, headers, config){
            var vcub = {
                eliminar : function(){
                    
                }
            };
        }).error(function(data, status, headers, config){
            
        });
    });
    
    /**
     * Directiva y controlador de t ranvias
     */
    
    TBC.directive('tablaTranvia', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-tranvia.html',
            controller: 'getTranvia'
        };
    });
 
    TBC.controller("getTranvia", function($http, $scope) {
        $scope.tranvias = [];
    $http.get('http://localhost:9000/tranvias').
      success(function(data, status, headers, config) {
          console.log(data);
        
        for(i = 0; i < data.length; i++){
              var tran = {
              estado : data[i].estado,
              id : data[i].id,
              panico : data[i].panico,
              longitud : data[i].posiciones[data[i].posiciones.length-1].longitud,
              latitud : data[i].posiciones[data[i].posiciones.length-1].latitud,
              temperatura : data[i].temperatura
            };
        tran.isAccidentado = function(){
          return tran.estado === "Accidentado";  
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
        for(i = 0; i < data.length; i++){
            var pos = {
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
        var mapOptions = {
            
        zoom: 8,
        center: new google.maps.LatLng(4.60, -74.08),
        mapTypeId: google.maps.MapTypeId.TERRAIN
        }
        $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

        $scope.markers = [];
    
        var infoWindow = new google.maps.InfoWindow();
    
        var createMarker = function (info){
        
            var marker = new google.maps.Marker({
                map: $scope.map,
                position: new google.maps.LatLng(info.posiciones[info.posiciones.length-1].longitud, info.posiciones[info.posiciones.length-1].latitud),
            });
        
        marker.content = '<div class="infoWindowContent"> Tranvia ' + info.id + '</div>';
        
        google.maps.event.addListener(marker, 'click', function(){
            infoWindow.setContent(marker.content);
            infoWindow.open($scope.map, marker);
        });
        
        $scope.markers.push(marker);
     
        }  

        
        for (i = 0; i < data.length; i++){
            createMarker(data[i]);
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
    
    TBC.controller('registro',function($http, $scope, $location){ 
    $scope.addUser=function(){
            console.log('entro');
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


