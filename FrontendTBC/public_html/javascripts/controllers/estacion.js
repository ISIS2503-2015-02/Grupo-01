(function(){
    var TBC = angular.module('est',[]);
    
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
        $scope.show = false;
        $scope.mostrarTab = function(){
            $scope.show = true;
        };
        $scope.ocultarTab = function(){
            $scope.show = false;
        };
        $scope.showForm = function(){
           $scope.formulario = true;
        };
        $scope.ocultarForm = function(){
            $scope.formulario = false;
        };
        $scope.addEstation = function(){
            var peti={
                    method: 'POST',
                    url: urlP +'/estaciones',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token")
                    },
                    data: JSON.stringify($scope.estacion)
                };
            $http(peti).success(function(data, status, headers, config){
                var estacion = {
                            id : data.id,
                            capacidad : data.capacidad,
                            ubicacion : data.ubicacion,
                            llena : data.llena,
                            ocupacion : data.ocupacion,
                            bicis : data.vcubs,
                            longitud : data.longitud,
                            latitud : data.latitud,
                            mostrar : false
                        };
                        $scope.estaciones.push(estacion);
                        $scope.ocultarForm();
                        $scope.estation = {};
            }).error(function(data, status, headers, config){
                
            });
        };
        $scope.llenarTodas = function(){
            for (i = 0 ; i < $scope.estaciones.length; i++){
               var station = {
                    estacionId : $scope.estaciones[i].id
                    };
                    var peti2={
                    method: 'PUT',
                    url: urlP +'/estaciones/llenar',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token")
                    },
                    data: JSON.stringify(JSON.stringify(estacionId))
                };
                    $http(peti2).success(function(data, status, headers, config){
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
            $scope.ocultarTab();
            $scope.estaciones[index].mostrar = false;
            $scope.bicis = [];
        };
        $scope.mostrar = function(index){
           $scope.estaciones[index].mostrar = true;
           $scope.bicis = $scope.estaciones[index].bicis;
           $scope.mostrarTab();
        };
        $scope.estaciones = [];
        var peti3={
                    method: 'GET',
                    url: urlP +'/estaciones',
                    headers:{
                        'Content-Type': 'application/json',
                        'X-AUTH-TOKEN': getCookie("token")
                    }
                };
        $http(peti3).success(function(data, status, headers, config){
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



