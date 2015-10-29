(function(){
    var TBC = angular.module('tranv',[]);

    /**
     * Directiva y controlador de t ranvias
     */
    
    TBC.directive('tablaTranvia', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-tranvia.html',
            controller: 'tranviaapp'
        };
    });
 
    TBC.controller("tranviaapp", function($http, $scope) {
        $scope.tranvias = [];
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
        $scope.addTranvia = function(){
            var peti={
                    method: 'POST',
                    url: urlP +'/tranvias',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify($scope.tranvia)


                };
            $http(peti).success(function(data,status,headers,config){
                var tran = {
                        estado : data.estado,
                        id : data.id,
                        panico : data.panico,
                        longitud : data.posiciones[data[i].posiciones.length-1].longitud,
                        latitud : data.posiciones[data[i].posiciones.length-1].latitud,
                        temperatura : data.temperatura
                    };
                    tran.isAccidentado = function(){
                        return tran.estado === "Accidentado";  
                    };  
                    $scope.tranvias.push(tran);
            }).error();
        };
        $scope.eliminar = function(index){
            var peti={
                    method: 'DELETE',
                    url: urlP +'/tranvias/'+$scope.tranvias[index].id,
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify($scope.tranvia)


                };
            $http(peti).success(function(data, status, headers, config){
                $scope.conductores.splice(index,1);
            }).error(function(data, status, headers, config){
                
            });
        };
        $scope.showOcupados = function(){
            $scope.ocupados = true;
            $scope.disponibles = false;
            var peti={
                    method: 'GET',
                    url: urlP +'/tranviasOcupados',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify($scope.tranvia)


                };
            $http(peti).
            success(function(data, status, headers, config) {
                $scope.tranvias = [];
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
        $scope.showDisponibles = function(){
            var peti={
                    method: 'GET',
                    url: urlP +'/tranviasDisponibles',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify($scope.tranvia)


                };
            $scope.disponibles = true;
            $scope.ocupados = false;
            $http(peti).
            success(function(data, status, headers, config) {
                $scope.tranvias = [];
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
            var peti={
                    method: 'GET',
                    url: urlP +'/tranvias',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify($scope.tranvia)


                };
            $scope.ocupados = false;
            $scope.disponibles = false;
            $http().
            success(function(data, status, headers, config) {
                $scope.tranvias = [];
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
                for(i = 0; i < data.length; i++){
                    createMarker(data[i]);
                }
            }).
            error(function(data, status, headers, config) {
                // log error
            });
        };
        $scope.showReviews = function(index){
            var peti={
                    method: 'GET',
                    url: urlP +'/tranvias/'+$scope.tranvias[index].id+'/revisiones',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify($scope.tranvia)


                };
            $scope.reviews = $scope.tranvias[index].id;
            $http(peti).success(function(data, status, headers, config){
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
        var petiV={
                    method: 'GET',
                    url: urlP +'/tranvias',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token"),
                    },
                    data: JSON.stringify($scope.tranvia)


                };
        $http(petiV).
            success(function(data, status, headers, config) {
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
                position: new google.maps.LatLng(info.posiciones[info.posiciones.length-1].latitud, info.posiciones[info.posiciones.length-1].longitud ),
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

