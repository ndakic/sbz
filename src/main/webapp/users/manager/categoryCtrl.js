/**
 * Created by dakamadafaka on 7/30/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('categoryCtrl', function($scope, $log, AuthenticationService, $http, Alertify, $state){
            var vm = this;
            vm.newArtCategory = newArtCategory;
            vm.deleteArtCat = deleteArtCat;
            vm.addUserLimit = addUserLimit;
            vm.deleteLimit = deleteLimit;
            vm.updateCategory = updateCategory;
            vm.newUser = newUser;
            vm.deleteUser = deleteUser;

            $scope.artCategory = {};

            $scope.limit = {};
            $scope.userCategory = {};
            $scope.roles = ['customer', 'seller', 'manager'];

            var loadArticleCategories = function () {
                var promise = $http.get("/api/category/articles");
                promise.then(function (response) {
                    $scope.articleCategories = response.data;
                });
            };

            loadArticleCategories();

            var loadArticles = function () {
                var promise = $http.get("/api/article/all");
                promise.then(function (response) {
                    $scope.articles = response.data;
                });
            };

            loadArticles();

            var loadUserCategories = function () {
                var promise = $http.get("/api/category/users");
                promise.then(function (response) {
                    $scope.userCategories = response.data;
                });
            };

            loadUserCategories();


            function newArtCategory() {
                var promise = $http.post("/api/category/article/add", $scope.artCategory);
                promise.then(function (response) {
                    if(response.status == '200'){
                        loadArticleCategories();
                        Alertify.success("Category successfully added.");
                    };
                });
            };

            function addUserLimit() {

                var limit = {
                    lowerRange: "",
                    upperRange: "",
                    percent: ""
                };

                limit.lowerRange = $scope.limit.lowerRange;
                limit.upperRange = $scope.limit.upperRange;
                limit.percent = $scope.limit.percent;

                var category = $scope.userCategory;

                for(var c in $scope.userCategories){
                    if($scope.userCategories[c].id == category.id){
                        $scope.userCategories[c].limits.push(limit);
                    }
                }

            };

            function deleteLimit(category, limit){
                var indexCat = $scope.userCategories.indexOf(category);
                var indexLim = $scope.userCategories[indexCat].limits.indexOf(limit);
                $scope.userCategories[indexCat].limits.splice(indexLim, 1);

            };

            function updateCategory(category){
                console.log(category);

                var promise = $http.post("/api/category/user/update", category);
                promise.then(function (response) {
                    if(response.status == '200'){
                        loadArticleCategories();
                        Alertify.success("Category successfully updated!");
                    };
                });

            };

            function deleteArtCat(category){

                var status = true;

                for(var art in $scope.articles){
                    if($scope.articles[art].articleCategory.title == category.title){
                        Alertify.error("This category can't be deleted!");
                        status = false;
                    }
                }

                if(status){
                    var promise = $http.post("/api/category/article/delete/" + category.id);
                    promise.then(function (response) {
                        console.log(response);
                        if(response.status == '200'){
                            loadArticleCategories();
                            Alertify.success("Category successfully deleted.");
                        };
                    });
                };
            };


            // ===================== Users ========================

            var loadUsers = function () {
                var promise = $http.get("/api/user/all");
                promise.then(function (response) {
                    $scope.users = response.data;
                });
            };

            loadUsers();

            // var loadAuthorities = function () {
            //     var promise = $http.get("/api/user/authorities");
            //     promise.then(function (response) {
            //         $scope.authorities = response.data;
            //     });
            // };
            //
            // loadAuthorities();

            $scope.user = {
                userProfile: {
                    userCategory: {}
                }

            };
            $scope.confirm_password = '';
            $scope.selected_authority = {};
            $scope.selected_category = {};


            function newUser() {
                if($scope.user.password == $scope.confirm_password && $scope.user.password != ''){

                    $scope.user.userProfile.userCategory.title = $scope.selected_category.title;

                    console.log($scope.user);

                    var promise = $http.post("/api/user/registration", $scope.user);
                    promise.then(function (response) {
                        if(response.status == "200"){
                            Alertify.success('User created!');
                            loadUsers();
                        };

                        if(response.status == "202"){
                            Alertify.error('Password is not valid!');
                        };

                        if(response.status == "204"){
                            Alertify.error('Username already exist!');
                        };
                    });

                };
            };


            function deleteUser(user) {

                console.log(user.username);

                var promise = $http.post("/api/user/delete/" + user.username);
                promise.then(function (response) {
                    if(response.status == '200'){
                        loadUsers();
                        Alertify.success("User successfully deleted.");
                    };
                });

            }

        });
}(angular));