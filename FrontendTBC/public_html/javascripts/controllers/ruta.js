(function(){
    var TBC = angular.module('rut',[]);
    
    TBC.directive('tablaRutasacc', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-rutasacc.html',
            controller: 'getRutasacc'
        };
    });
    
    TBC.controller("getRutasacc", function($http, $scope){
       $http.get('http://localhost:9000:9000/rutas/accidentes').success(function(data, status, headers, config){
                  
          $scope.rutasacc = [];
            for(i = 0; i < data.length; i++){
              var rutaac = {
                  id : data[i].id,
                  ubicaiconOrigen : data[i].ubicaiconOrigen,
                  ubicacionDestino : data[i].ubicacionDestino,
                  tipo : data[i].tipo,
                  tiempoTrayecto : data[i].tiempoTrayecto,
                  terminado : data[i].terminado,
                  tipoAccidente : data[i].tipoAccidente,
                  bus : data[i].bus,
                  tranvia : data[i].tranvia,
                  conductor : data[i].conductor,
                  mostrarmob : false
              };
              
              rutaac.vehiculoTranvia = function(){
                  return rutaac.tranvia === null;
              };
              
              rutaac.vehiculoBus = function(){
                  return rutaac.bus === null;
              };
              
              $scope.rutasacc.push(rutaac);
            }
            
            $scope.busesCerc = [];
           
            $scope.ocultar = function(index){
                $scope.rutasacc[index].mostrarmob = false;
                $scope.busesCerc = [];
                
            };
            $scope.mostrar = function(index){
                if ($scope.rutasacc[index].mostrarmob === true){
                    $scope.rutasacc[index].mostrarmob = false;
                    $scope.busesCerc = [];
                    angular.forEach($scope.markersbus, function(marker) {
                        marker.setMap(null);
                    });
                    $scope.markersbus = [];
                    
                }
                else{    
                    $scope.rutasacc[index].mostrarmob = true;
                    $http.get('http://localhost:9000/rutas/busesCercanos/'+ $scope.rutasacc[index].id).success(function(data, status, headers, config){
                        $scope.busesCerc = [];
                        for(i = 0; i < data.length; i++){
                            var busc = {
                            estado : data[i].estado,
                            id : data[i].id,
                            longitud : data[i].posiciones[data[i].posiciones.length-1].longitud,
                            latitud : data[i].posiciones[data[i].posiciones.length-1].latitud,
                            placa : data[i].placa,
                            capacidad: data[i].capacidad
                            }; 
                            $scope.busesCerc.push(busc);
                            }
                        $scope.markersbus = [];
                        var mapOptions = {
                            zoom: 14,
                            center: new google.maps.LatLng(4.60, -74.08),
                            mapTypeId: google.maps.MapTypeId.TERRAIN
                        }

                        var infoWindow = new google.maps.InfoWindow();

                        var createMarker = function (info){

                            var markerbus = new google.maps.Marker({
                                map: $scope.map,
                                position: new google.maps.LatLng(info.posiciones[info.posiciones.length-1].latitud, info.posiciones[info.posiciones.length-1].longitud),
                            });

                        markerbus.content = '<div class="infoWindowContent"> Bus ' + info.id + '</div>';

                        google.maps.event.addListener(markerbus, 'click', function(){
                            infoWindow.setContent(markerbus.content);
                            infoWindow.open($scope.map, markerbus);
                        });

                        $scope.markersbus.push(markerbus);

                        }  
                        
                        for (i = 0; i < data.length; i++){
                            createMarker(data[i]);
                        }
                        
                        $scope.asignarMobibus = function(ind){
                            $http.put('http://localhost:9000/rutas/asignacionMobibus', JSON.stringify({mobibusId: $scope.busesCerc[ind].id , rutaId: $scope.rutasacc[index].id})).success(function(data,headers){
                            $scope.rutasacc[index]=data;

                        }).error(function(data, headers){
                            console.log(data);
                        });
                                
                        }
                    })};
                
                

                };

           })
    });
    
    /**
     * Directiva y controlador de rutas
     */
    TBC.directive('tablaRutas', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-rutas.html',
            controller: 'getRutas'
        };
    });
    
    TBC.controller("getRutas", function($http, $scope){
       $http.get('http://localhost:9000/rutas').success(function(data, status, headers, config){
          $scope.rutas = [];
            for(i = 0; i < data.length; i++){
              var ruta = {
              id : data[i].id,
              ubicaiconOrigen : data[i].ubicaiconOrigen,
              ubicacionDestino : data[i].ubicacionDestino,
              tipo : data[i].tipo,
              tiempoTrayecto : data[i].tiempoTrayecto,
              terminado : data[i].terminado,
              tipoAccidente : data[i].tipoAccidente,
              bus : data[i].bus,
              tranvia : data[i].tranvia,
              conductor : data[i].conductor
              };
              
              ruta.accidentada = function(){
                  return ruta.terminado==="Anormal";
              }
              
              ruta.vehiculoTranvia = function(){
                  return ruta.tranvia === null;
              }
              
              ruta.vehiculoBus = function(){
                  return ruta.bus === null;
              }
              
              $scope.rutas.push(ruta);
              console.log($scope.rutas);
            }
            
            var mapOptions = {
                            zoom: 14,
                            center: new google.maps.LatLng(4.60, -74.08),
                            mapTypeId: google.maps.MapTypeId.TERRAIN
                        }
                        $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

                        $scope.markers = [];

                        var infoWindow = new google.maps.InfoWindow();
                        
                        var createMarker = function (info){
                            if(!(info.tranvia === null)){
                                var marker = new google.maps.Marker({
                                    map: $scope.map,
                                    position: new google.maps.LatLng(info.tranvia.posiciones[info.tranvia.posiciones.length-1].latitud, info.tranvia.posiciones[info.tranvia.posiciones.length-1].longitud),
                                });
                            }
                            else if (!(info.bus === null)){
                                var marker = new google.maps.Marker({
                                    map: $scope.map,
                                    position: new google.maps.LatLng(info.bus.posiciones[info.bus.posiciones.length-1].latitud, info.bus.posiciones[info.bus.posiciones.length-1].longitud),
                                });
                            }

                        marker.content = '<div class="infoWindowContent"> Ruta ' + info.id + '</div>';

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
     * Directiva y controlador de conductores
     */
    TBC.directive('tablaConductores', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-conductores.html',
            controller: 'getConductores'
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


