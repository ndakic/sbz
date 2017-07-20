/**
 * Created by dakamadafaka on 3/1/17.
 */
(function (angular) {
    angular.module('SBZApp')
        .controller('loginCtrl', function($scope, $log, AuthenticationService, $http, Alertify){
            $scope.user={};
            $scope.login=function () {
                AuthenticationService.login($scope.user.username, $scope.user.password, loginCbck);
            };
            function loginCbck(success) {
                if (success) {
                    $log.info('success!');
                    Alertify.success('User successfully logged!');
                }
                else{
                    $log.info('failure!');
                }
            };

        });
}(angular));