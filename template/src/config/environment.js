const api = {}
const API_VERSION = '/' + (process.env.VUE_APP_API_VERSION || 'v1')
const endpoints = {
    token: '/oauth/token',
    upload: '/upload',
    base: '',
    oauth: '/oauth'
}

api.url = process.env.VUE_APP_API_URL || ''
api.token = api.url + endpoints.token
api.oauth = api.url + endpoints.oauth

for (var i in endpoints) {
    if (typeof api[i] == 'undefined')
        api[i] = api.url + API_VERSION + endpoints[i]
}

const oauth = {
    client_id: process.env.VUE_APP_OAUTH_CLIENT_ID,
    client_secret: process.env.VUE_APP_OAUTH_CLIENT_SECRET,
}

module.exports = {
    app_name: (process.env.VUE_APP_NAME || 'Admin LTE'),
    router_mode: (process.env.VUE_APP_ROUTER_MODE || 'hash'),
    base_url: (process.env.BASE_URL || ''),
    api: api,
    oauth: oauth
}
