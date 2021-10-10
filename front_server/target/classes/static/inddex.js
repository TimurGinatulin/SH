(function ($localStorage) {
    'use strict';

    angular
      .module('app', ['ngRoute', 'ngStorage'])
      config(config)
      .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/',{
                templateUrl: 'home/home.html',
                controller: 'homeController'})
                .otherwise({
                redirectTo: '/'
                });
    }

    const contextPath = 'http://192.168.0.102:5555';

    function run($rootScope,$http,$localStorage){
        if($localStorage.currentUser){
            $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
        }
    }
})();
angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://192.168.0.102:5555';

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
        alert(true);
            return true;
        } else {
            return false;
        }
    };
});