/**
 * Created by dakamadafaka on 8/8/17.
 */


(function (angular) {
    angular.module('SBZApp')
        .controller('eventCtrl', function($scope, $log, AuthenticationService, $http, Alertify, $state){
            var vm = this;
            vm.addCategory = addCategory;
            vm.addEvent = addEvent;
            vm.makeEvent = makeEvent;
            vm.deleteCat = deleteCat;

            $scope.category = {};

            $scope.event = {
                title: "",
                categories: [],
                discount : "",
                starts : "",
                ends: ""
            };

            var loadArticleCategories = function () {
                var promise = $http.get("/api/category/articles");
                promise.then(function (response) {
                    $scope.articleCategories = response.data.slice(1,response.data.length);
                });
            };

            loadArticleCategories();

            var loadEvents = function () {
                var promise = $http.get("/api/event/all");
                promise.then(function (response) {
                    $scope.events = response.data;
                });
            };

            loadEvents();

            function addEvent(event) {

                for (var i = 0; i < $scope.events.length; i++) {
                    if ($scope.events[i].title == event.title) {
                        Alertify.log("Category already added!");
                        return;
                    }
                }


                var promise = $http.post("/api/event/add", event);
                promise.then(function (response) {
                    if(response.status == '200'){
                        loadEvents();
                        Alertify.success("Event successfully added.");
                    };
                });

                //$scope.events.push(event);

                $scope.event = {
                    title: "",
                    categories: [],
                    discount : "",
                    starts : new Date(),
                    ends: new Date()
                };

            };

            function addCategory(category) {

                for (var i = 0; i < $scope.event.categories.length; i++) {
                    if ($scope.event.categories[i].title == category.title) {
                        alertify.log("Category already added!");
                        return;
                    }
                }

                $scope.event.categories.push(category)

            };

            function makeEvent(event) {
                console.log(event);
            }

            function deleteCat(cat) {
                console.log(cat);

                var indexCat = $scope.event.categories.indexOf(cat);

                $scope.event.categories.splice(indexCat, 1);

            }

        });
}(angular));