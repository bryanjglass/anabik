import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue';
import router from './router';
import App from './App.vue';

Vue.use(BootstrapVue);

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
