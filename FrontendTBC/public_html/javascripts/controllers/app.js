/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
userAct ={}; 

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}

function sign(key) {
    var decrypted = decrypt();
    var hash = CryptoJS.HmacSHA1(decrypted,key);
    return hash.toString(CryptoJS.enc.Base64);
}

function encrypt(){
    var encrypted = CryptoJS.AES.encrypt("Message", ":>GwSUOe`9Ks[?t=cD9Lcn^LcvwU3VlVU?QS1oW:_hBRh3FR2ivOD6<JnR]EW;Dp");
    return encrypted;
}

function decrypt(){
    var encryptoken = getCookie("authToken");
    var key = getCookie("secretKey");
    var decrypted = CryptoJS.AES.decrypt(encryptoken,key);
    return decrypted.toString(CryptoJS.enc.Utf8);
}

(function(){
    var TBC = angular.module('TBC',[]);
    var usActual;
    
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


