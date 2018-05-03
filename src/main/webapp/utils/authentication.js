/**
 * Created by dakamadafaka on 7/7/17.
 */

(function () {
    angular
        .module('authentication',['ngStorage', 'ui.router', 'angular-jwt'])
        .factory('AuthenticationService', Service);

    function Service($http, $localStorage, $log, $state, jwtHelper) {
        var service = {};

        service.login = login;
        service.logout = logout;
        service.getCurrentUser = getCurrentUser;

        return service;

        function login(username, password, callback) {
            $http.post('/api/user/login', { "username": username, "password": password})
                .then(function successCallback(response) {
                    console.log(response);
                    if (response.data) {
                        // korisnicko ime, token i rola (ako postoji) cuvaju se u lokalnom skladištu

                        var currentUser = { username: username, token: response.data };
                        var tokenPayload = jwtHelper.decodeToken(response.data);

                        console.log("response data:", response.data);
                        console.log("token:", tokenPayload);

                        if(tokenPayload.role){
                            currentUser.role = tokenPayload.role;
                        }

                        // prijavljenog korisnika cuva u lokalnom skladistu
                        $localStorage.currentUser = currentUser;
                        console.log($localStorage.currentUser);
                        // jwt token dodajemo u to auth header za sve $http zahteve
                        $http.defaults.headers.common.Authorization = response.data;
                        // callback za uspesan login
                        callback(true);

                        if(currentUser.role == "customer"){
                            $state.go('articles');
                        }else if(currentUser.role =="seller"){
                            $state.go('bills');
                        }else if(currentUser.role == "manager"){
                            $state.go('article_category');
                        }

                    } else {
                        // callback za neuspesan login
                        callback(false);
                    }
                }, function errorCallback(response) {
                    console.log(response);
                    console.log("Error bree")
                });
        }

        function logout() {
            // uklonimo korisnika iz lokalnog skladišta
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
            $state.go('login');
        }

        function getCurrentUser() {
            return $localStorage.currentUser;
        }
    }
})();