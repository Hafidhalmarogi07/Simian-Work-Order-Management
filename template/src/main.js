import 'es6-promise/auto'
import Vue from 'vue'
import Vuex from 'vuex'
import App from './App.vue'
import VueRouter from 'vue-router'
import VueCurrencyInput from 'vue-currency-input'
import VueCurrencyFilter from 'vue-currency-filter'
import createCurrencyFormat from 'vue-currency-input/src/utils/createCurrencyFormat'
import jQuery from 'jquery'
import moment from 'moment'
import routerConfig from '@config/router'
import env from '@config/environment'
import Api from '@app/service/Api'
import '@fortawesome/fontawesome-free/css/all.css'
import 'ionicons/dist/css/ionicons.css'
import 'icheck-bootstrap'
import 'admin-lte/dist/css/adminlte.min.css'
import 'jquery-ui'
import 'bootstrap/dist/js/bootstrap.bundle.js'
import 'chart.js'
import 'daterangepicker/daterangepicker.css'
import 'daterangepicker/daterangepicker.js'
import 'tempusdominus-bootstrap-4/build/css/tempusdominus-bootstrap-4.css'
import 'tempusdominus-bootstrap-4/build/js/tempusdominus-bootstrap-4.js'
import 'summernote/dist/summernote-bs4.css'
import 'summernote/dist/summernote.js'
import 'summernote/dist/summernote-bs4.js'
import 'overlayscrollbars/css/OverlayScrollbars.css'
import 'overlayscrollbars/js/jquery.overlayScrollbars.js'
import 'admin-lte/dist/js/adminlte.js'
import '@assets/css/style.css'
import '@assets/css/loader.css'
import 'bootstrap-select/dist/css/bootstrap-select.css'
import 'bootstrap-select/dist/js/bootstrap-select.js'
import 'admin-lte/plugins/pace-progress/pace.min.js'

const currencyOptions = {
    globalOptions: {
        currency: 'IDR',
        locale: 'id'
    }
}

const formatOptions = createCurrencyFormat(currencyOptions.globalOptions)

Vue.use(Vuex)
Vue.use(VueRouter)
Vue.use(VueCurrencyInput, currencyOptions)

const filterCurrencyOptions = {
  symbol : formatOptions.prefix,
  thousandsSeparator: formatOptions.groupingSymbol,
  fractionCount: formatOptions.maximumFractionDigits,
  fractionSeparator: formatOptions.decimalSymbol,
  symbolPosition: 'front',
  symbolSpacing: true
}
Vue.use(VueCurrencyFilter, filterCurrencyOptions)

const store = new Vuex.Store({
    state: {
        user: {},
        user_image: null,
        data: {},
        title: null,
        subtitle: null,
        loading: false
    },
})

const router = new VueRouter(routerConfig)
moment.locale('id')

Vue.config.productionTip = false
Vue.prototype.$ = window.$ = window.jQuery = jQuery
Vue.prototype.moment = window.moment = moment
Vue.prototype.env = window.env = env
window.adminlte = adminlte
Vue.prototype.Api = window.Api = new Api(env, router,
    ['login', 'forgot_password', 'recover_password'])
Vue.prototype.currencyOptions = currencyOptions.globalOptions
window.Api.setStore(store)

window.Vue = Vue

new Vue({
  router: router,
  store: store,
  render: h => h(App)
}).$mount('#app')
