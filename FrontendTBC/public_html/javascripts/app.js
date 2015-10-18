/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(){
    var TBC = angular.module('TBC',[]);
    
    /**
     * Directiva y controlador de rutas
     */
    TBC.directive('tablaRutasacc', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-rutasacc.html',
            controller: 'getRutasacc'
        };
    });
    
    TBC.controller("getRutasacc", function($http, $scope){
       $http.get('http://localhost:9000/rutas/accidentes').success(function(data, status, headers, config){
                  
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
                            console.log($scope.busesCerc);
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
                            console.log(JSON.stringify({mobibusId: $scope.busesCerc[ind].id , rutaId: $scope.rutasacc[index].id}));
                            $http.put('http://localhost:9000/rutas/asignacionMobibus', JSON.stringify({mobibusId: $scope.busesCerc[ind] , rutaId: $scope.rutasacc[index].id})).success(function(data,headers){
                            $scope.rutasacc[index]=data;
                            console.log(data);
                            console.log(hola);
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
                  return ruta.terminado==='Anormal';
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
    
    TBC.controller("getConductores", function($http, $scope){
        $scope.formulario = false;
        $scope.conductores = [];
        $scope.eliminar = function(index){
            $http.delete('http://localhost:9000/conductores/'+$scope.conductores[index].id).success(function(data, status, headers, config){
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
            $http.post('http://localhost:9000/conductores',JSON.stringify($scope.conductor)).success(function(data, status, headers, config){
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
              $scope.$apply();
              $scope.formulario = false;
              $scope.conductor={};
                
            }).error(function(data, status, headers, config){
                
            });
        };
        $http.get('http://localhost:9000/conductores').success(function(data, status, headers, config){
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
              longitud : data[i].posiciones[data[i].posiciones.length-1].longitud,
              latitud : data[i].posiciones[data[i].posiciones.length-1].latitud,
              placa : data[i].placa,
              capacidad: data[i].capacidad
              };
              bus.isAccidentado = function(){
                return bus.estado === "Accidentado";  
              };  
              $scope.buses.push(bus);
              console.log($scope.buses);
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
      }); 
    });
    
    /**
     * Directiva y controlador de estaciones vcub
     */
    
    TBC.directive('stations', function(){
        return {    
            restrict:'E',
            templateUrl:'partials/stations.html',
            controller: 'vcub'
        };
    });
    
    TBC.controller("vcub", function($http, $scope){
        $scope.bicis = [];
        $scope.formulario = false;
        
        $scope.llenarTodas = function(){
            for (i = 0 ; i < $scope.estaciones.length; i++){
               var station = {
                    estacionId : $scope.estaciones[i].id
                    };
                    $http.put('http://localhost:9000/estaciones/llenar', JSON.stringify(station)).success(function(data, status, headers, config){
                    console.log(data);
                    for(i = 0; i < data.length; i++){
                        var estacion = {
                            id : data[i].id,
                            capacidad : data[i].capacidad,
                            ubicacion : data[i].ubicacion,
                            llena : data[i].llena,
                            ocupacion : data[i].ocupacion,
                            bicis : data[i].vcubs,
                            longitud : data[i].longitud,
                            latitud : data[i].latitud,
                            mostrar : false
                        };
                        $scope.estaciones = [];
                        $scope.estaciones.push(estacion);
                        $scope.$apply();
                    }
                }).error(function(data, status, headers, config){
              
                });
            }  
        };
        $scope.ocultar = function(index){
            $scope.estaciones[index].mostrar = false;;
            $scope.bicis = [];
        };
        $scope.mostrar = function(index){
           $scope.estaciones[index].mostrar = true;
           $scope.bicis = $scope.estaciones[index].bicis;
        };
        $scope.estaciones = [];
        $http.get('http://localhost:9000/estaciones').success(function(data, status, headers, config){
            for(i = 0; i < data.length; i++){
                var estacion = {
                id : data[i].id,
                capacidad : data[i].capacidad,
                ubicacion : data[i].ubicacion,
                llena : data[i].llena,
                ocupacion : data[i].ocupacion,
                bicis : data[i].vcubs,
                latitud : data[i].latitud,
                longitud : data[i].longitud
                };
            $scope.estaciones.push(estacion);
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
                position: new google.maps.LatLng(info.longitud, info.latitud),
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


