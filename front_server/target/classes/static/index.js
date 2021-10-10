(function ($localStorage) {
    'use strict';

    angular
      .module('app', ['ngRoute', 'ngStorage'])
      .config(config)
      .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/groups', {
                templateUrl: 'groups/groups.html',
                controller: 'groupsController'
                })
            .otherwise({
                redirectTo: '/'
            });
    }

    const contextPath = 'http://localhost:5555';

    function run($rootScope,$http,$localStorage){
        if($localStorage.currentUser){
            $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location) {

    const contextPath = 'http://localhost:5555';

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth/login', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = response.data.token;
                    $localStorage.currentUser = {email: $scope.user.email, token: response.data.token};
                    $scope.currentUserName = $scope.user.email;
                    $scope.user.email = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $location.path('/');
        if ($scope.user.email) {
            $scope.user.email = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            return true;
        } else {
            return false;
        }
    };
});