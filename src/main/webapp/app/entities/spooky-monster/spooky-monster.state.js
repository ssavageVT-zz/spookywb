(function() {
    'use strict';

    angular
        .module('spookywhiteboardApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('spooky-monster', {
            parent: 'entity',
            url: '/spooky-monster',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SpookyMonsters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/spooky-monster/spooky-monsters.html',
                    controller: 'SpookyMonsterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('spooky-monster-detail', {
            parent: 'entity',
            url: '/spooky-monster/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SpookyMonster'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/spooky-monster/spooky-monster-detail.html',
                    controller: 'SpookyMonsterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SpookyMonster', function($stateParams, SpookyMonster) {
                    return SpookyMonster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'spooky-monster',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('spooky-monster-detail.edit', {
            parent: 'spooky-monster-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/spooky-monster/spooky-monster-dialog.html',
                    controller: 'SpookyMonsterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SpookyMonster', function(SpookyMonster) {
                            return SpookyMonster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('spooky-monster.new', {
            parent: 'spooky-monster',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/spooky-monster/spooky-monster-dialog.html',
                    controller: 'SpookyMonsterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                monsterName: null,
                                monsterType: null,
                                currentStatus: null,
                                isInBathroom: null,
                                isLate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('spooky-monster', null, { reload: 'spooky-monster' });
                }, function() {
                    $state.go('spooky-monster');
                });
            }]
        })
        .state('spooky-monster.edit', {
            parent: 'spooky-monster',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/spooky-monster/spooky-monster-dialog.html',
                    controller: 'SpookyMonsterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SpookyMonster', function(SpookyMonster) {
                            return SpookyMonster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('spooky-monster', null, { reload: 'spooky-monster' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('spooky-monster.delete', {
            parent: 'spooky-monster',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/spooky-monster/spooky-monster-delete-dialog.html',
                    controller: 'SpookyMonsterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SpookyMonster', function(SpookyMonster) {
                            return SpookyMonster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('spooky-monster', null, { reload: 'spooky-monster' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
