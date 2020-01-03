//定义控制器，注入$rootScope、$scope、$http
actionApp.controller('View1Controller', ['$rootScope', '$scope', '$http', function ($rootScope, $scope, $http) {
    //使用$scope.$on监听$viewContentLoaded事件
    $scope.$on('$viewContentLoaded', function () {
        console.log('view1页面加载完成');
    });

    //定义ng函数，可在页面使用
    $scope.search = function () {
        personName = $scope.personName;
        $http.get('/hello/search?personName=' + personName, {})
            .success(function (data) {
            $scope.person = data;
        });
    }
}]);

actionApp.controller('View2Controller', ['$rootScope', '$scope', function ($rootScope, $scope) {
    $scope.$on('$viewContentLoaded', function () {
        console.log('view2页面加载完成');
    });
}]);