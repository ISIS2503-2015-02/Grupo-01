/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(){
    var TBC= angular.module('tranviapp',[]);
    
    TBC.directive('tablatranvia', function(){
        return{
            restrict:'E',
            templateUrl:'partials/tabla-tranvia.html',
            controller: 'getTranvia'
        };
    });
 
    TBC.controller("getTranvia", function($http, $scope) {
    $http.get('http://localhost:9000/tranvias').
      success(function(data, status, headers, config) {
        console.log(data);
        $scope.tranvias = data;
      }).
      error(function(data, status, headers, config) {
        // log error
      });
    });
})();


