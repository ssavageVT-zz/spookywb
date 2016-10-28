(function() {
    'use strict';

    angular
        .module('savickApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('whiteboard', {
            parent: 'entity',
            url: '/whiteboard',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Whiteboards'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/whiteboard/whiteboards.html',
                    controller: 'WhiteboardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('whiteboard-detail', {
            parent: 'entity',
            url: '/whiteboard/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Whiteboard'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/whiteboard/whiteboard-detail.html',
                    controller: 'WhiteboardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Whiteboard', function($stateParams, Whiteboard) {
                    return Whiteboard.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'whiteboard',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('whiteboard-detail.edit', {
            parent: 'whiteboard-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/whiteboard/whiteboard-dialog.html',
                    controller: 'WhiteboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Whiteboard', function(Whiteboard) {
                            return Whiteboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('whiteboard.new', {
            parent: 'whiteboard',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/whiteboard/whiteboard-dialog.html',
                    controller: 'WhiteboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                currentStatus: null,
                                isInBathroom: null,
                                isLate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('whiteboard', null, { reload: 'whiteboard' });
                }, function() {
                    $state.go('whiteboard');
                });
            }]
        })
        .state('whiteboard.edit', {
            parent: 'whiteboard',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/whiteboard/whiteboard-dialog.html',
                    controller: 'WhiteboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Whiteboard', function(Whiteboard) {
                            return Whiteboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('whiteboard', null, { reload: 'whiteboard' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('whiteboard.delete', {
            parent: 'whiteboard',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/whiteboard/whiteboard-delete-dialog.html',
                    controller: 'WhiteboardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Whiteboard', function(Whiteboard) {
                            return Whiteboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('whiteboard', null, { reload: 'whiteboard' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
