/**
 * Created by dakamadafaka on 7/7/17.
 */

(function (angular) {

    var app = angular.module('SBZApp',[
        'ui.router',
        'ngResource',
        'ngStorage',
        'ui.bootstrap',
        'Alertify',
        'authentication',
        'shopService',
        'registration'
    ]);

    app.config(['$qProvider', function ($qProvider) {
        $qProvider.errorOnUnhandledRejections(false);
    }]);

    app
        .config(config)
        .run(run);
    function config($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/login');
        $stateProvider
            .state('app', {
                abstract: true,
                views: {
                    'navbar@': {
                        templateUrl: 'layouts/navbar/navbar.html',
                        controller: 'NavbarController',
                        controllerAs: 'vm'
                    }

                }
            });
    }
    function run($rootScope, $http, $location, $localStorage, AuthenticationService, $state) {
        // postavljanje tokena nakon refresh
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
        }

        // ukoliko pokušamo da odemo na stranicu za koju nemamo prava, redirektujemo se na login
        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {

            var publicStates = ['login','registration', '404', /*'entry',*/''];
            var restrictedState = publicStates.indexOf(toState.name) === -1;
            if(restrictedState && !AuthenticationService.getCurrentUser()){
                $state.go('login');
            }
            //provera ako je ulogovan
            if($localStorage.currentUser && toState.name === "login"){
                $state.go('articles');
            };

        });

        // url
        $rootScope.$on('$stateChangeStart', function(e, next, current) {

            //console.log("===========" + next.url + " ==============");

            if(next.url != '/login' && next.url != '/registration' &&  next.url != "/articles"){

                if(next.data.authorities.length == 0){
                    window.location.assign("/articles");
                };


                var status = checkAuth(next.data.authorities);

                console.log("status", status);

                if(status === false){
                    //$location.path("/articles"); OVAKO i sa $state.go() NE RADI!
                    window.location.assign("/articles");
                };
            };

            //console.log("=========== END ==============");

        });

        $rootScope.logout = function () {
            AuthenticationService.logout();
        }

        $rootScope.getCurrentUserRole = function () {
            if (!AuthenticationService.getCurrentUser()){
                return undefined;
            }
            else{
                return AuthenticationService.getCurrentUser().role;
            }
        }
        $rootScope.isLoggedIn = function () {
            if (AuthenticationService.getCurrentUser()){
                return true;
            }
            else{
                return false;
            }
        }
        $rootScope.getCurrentState = function () {
            return $state.current.name;
        }

        $rootScope.getCurrentUser = function () {
            if (!AuthenticationService.getCurrentUser()){
                return undefined;
            }
            else{
                return AuthenticationService.getCurrentUser().username;
            }
        };


        function checkAuth(authorities) {

            var status = false;

            for(var aut in authorities){
                if(authorities[aut] == $localStorage.currentUser.role){
                    status = true;
                };
            };

            return status;
        }

    }

}(angular));