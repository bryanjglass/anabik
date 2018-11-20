import Vue from 'vue';
import Router from 'vue-router';

import DefaultContainer from '@/containers/DefaultContainer.vue';
import Dashboard from '@/views/Dashboard.vue';
Vue.use(Router);

export default new Router({
    mode: 'hash', // https://router.vuejs.org/api/#mode
    linkActiveClass: 'open active',
    scrollBehavior: () => ({y: 0}),
    routes: [{
        path: '/',
        redirect: '/dashboard',
        name: 'Home',
        component: DefaultContainer,
        children: [{
            path: '/dashboard',
            component: Dashboard
        }]
    }, ]
});
