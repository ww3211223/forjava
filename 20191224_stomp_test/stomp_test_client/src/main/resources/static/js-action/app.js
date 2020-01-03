//定义模块actionApp，并依赖于路由模块ngRout.
var actionApp = angular.module('actionApp', ['ngRoute']);

//配置路由，通过注入$routeProvider来配置
actionApp.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when('/oper', {
            controller: 'View1Controller',
            templateUrl: 'views/view1.html'
        })
        .when('/directive', {
            controller: 'View2Controller',
            templateUrl: 'views/view2.html'
        });
}]);