(function(){
    var TBC = angular.module('mobi',[]);
    
        /**
     * Directiva y controllador de buses 
     */
    
    TBC.directive('tablaBuses', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-buses.html',
            controller : 'buses'
        };
    });
    
    TBC.directive('datatableSetup', ['$timeout',
        function ($timeout) {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
 
                    $timeout(function () {
                        element.dataTable();
                    });
                }
            };
        }
    ]);
    
    TBC.controller("buses", function($http, $scope){
        console.log("meh");
        $scope.buses = [];
        $scope.form = false;
        $scope.ocupados = false;
        $scope.disponibles = false;
        $scope.reviews = -1;
        $scope.revisiones = [];
        $scope.showForm = function(){
            $scope.form = true;
        };
        $scope.hideForm = function(){
            $scope.form = false;
        };
        $scope.showOcupados = function(){
            $scope.ocupados = true;
            $scope.disponibles = false;
            var peti={
                    method: 'GET',
                    url: urlP +'/mobibusesOcupados',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token")
                    },
                };
            $http(peti).
            success(function(data, status, headers, config) {
                $scope.buses = [];
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
                    $scope.buses.push(tran);
                }
                $scope.totalR = $scope.buses;
                $scope.map = {
                        center: {
                            latitude: 40.454018, 
                            longitude: -3.509205
                        }, 
                        zoom: 14,
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
                };
                $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);
                $scope.markers = [];
                var infoWindow = new google.maps.InfoWindow();
                var createMarker = function (info){
                    var marker = new google.maps.Marker({
                        map: $scope.map,
                        position: new google.maps.LatLng(info.posiciones[info.posiciones.length-1].latitud,info.posiciones[info.posiciones.length-1].longitud)
                    });
                    marker.content = '<div class="infoWindowContent"> Bus ' + info.id + '</div>';
                    google.maps.event.addListener(marker, 'click', function(){
                        infoWindow.setContent(marker.content);
                        infoWindow.open($scope.map, marker);
                    });
                    $scope.markers.push(marker);
                };  
                for (i = 0; i < data.length; i++){
                    createMarker(data[i]);
                }
            }).
            error(function(data, status, headers, config) {
                // log error
            });
        };
        $scope.showDisponibles = function(){
            $scope.disponibles = true;
            $scope.ocupados = false;
            var peti={
                    method: 'GET',
                    url: urlP +'/mmobibusesDisponibles',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token")
                    }
                };
            $http(peti).
            success(function(data, status, headers, config) {
                $scope.buses = [];
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
                    $scope.buses.push(tran);
                }
                $scope.totalR = $scope.buses;
                $scope.map = {
                        center: {
                            latitude: 40.454018, 
                            longitude: -3.509205
                        }, 
                        zoom: 14,
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
                };
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
                };  
                for (i = 0; i < data.length; i++){
                    createMarker(data[i]);
                }
            }).
            error(function(data, status, headers, config) {
                // log error
            });
        };
        $scope.showAll = function(){
            $scope.ocupados = false;
            $scope.disponibles = false;
            console.log(getCookie("token"));
            var peti={
                    method: 'GET',
                    url: urlP +'/mobibuses',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token")
                    }
                };
            $http(peti).
            success(function(data, status, headers, config) {
                $scope.buses = [];
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
                    $scope.buses.push(tran);
                }
                $scope.totalR = $scope.buses;
                $scope.map = {
                        center: {
                            latitude: 40.454018, 
                            longitude: -3.509205
                        }, 
                        zoom: 14,
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
                };
                $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);
                $scope.markers = [];
                var infoWindow = new google.maps.InfoWindow();
                var createMarker = function (info){
                    var marker = new google.maps.Marker({
                        map: $scope.map,
                        position: new google.maps.LatLng(info.posiciones[info.posiciones.length-1].longitud, info.posiciones[info.posiciones.length-1].latitud),
                    });
                    marker.content = '<div class="infoWindowContent"> Mobibus ' + info.id + '</div>';
                    google.maps.event.addListener(marker, 'click', function(){
                        infoWindow.setContent(marker.content);
                        infoWindow.open($scope.map, marker);
                    });
                    $scope.markers.push(marker);
                };  
                for(i = 0; i < data.length; i++){
                    createMarker(data[i]);
                }
            }).
            error(function(data, status, headers, config) {
                // log error
            });
        };
        $scope.showReviews = function(index){
            $scope.reviews = $scope.buses[index].id;
            var peti={
                    method: 'GET',
                    url: urlP +'/mobibuses/'+$scope.buses[index].id+'/revisiones',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token")
                    }
                };
            $http.get(peti).success(function(data, status, headers, config){
                for (i = 0; i < data.length; i++){
                    var rev = {
                       id : data[i].id,
                       fechaAnterior :data[i].fechaAnterior,
                       fecha : data[i].fecha,
                       kilometraje : data[i].kilometraje
                    };
                    $scope.revisiones.push(rev);
                }
            }).error(function(){
                
            });
        };
        $scope.hideReviews = function(){
            $scope.reviews = 0;
            $scope.revisiones = [];
        };
        console.log(getCookie("token"));
        var petiV={
                    method: 'GET',
                    url: urlP +'/mobibuses',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token")
                    }
                };
       $http(petiV).success(function(data, status, headers, config){
       console.log("done");
            for(i = 0; i < data.length; i++){
              var bus = {
              estado : data[i].estado,
              id : data[i].id,
              longitud : data[i].posiciones[data[i].posiciones.length-1].longitud,
              latitud : data[i].posiciones[data[i].posiciones.length-1].latitud,
              placa : data[i].placa,
              capacidad: data[i].capacidad
              };
              bus.isAccidentado = function(){
                return bus.estado === "Accidentado";  
              };  
              $scope.buses.push(bus);
            }
            $scope.totalR = $scope.buses;
            var mapOptions = {
                zoom: 14,
                center: new google.maps.LatLng(4.60, -74.08),
                mapTypeId: google.maps.MapTypeId.TERRAIN
            }
        $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

        $scope.markers = [];
    
        var infoWindow = new google.maps.InfoWindow();
    
        var createMarker = function (info){
        
            var marker = new google.maps.Marker({
                map: $scope.map,
                position: new google.maps.LatLng(info.posiciones[info.posiciones.length-1].latitud, info.posiciones[info.posiciones.length-1].longitud),
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
      }).then(function () {
                    $scope.totalR = $scope.buses;
                });; 
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

