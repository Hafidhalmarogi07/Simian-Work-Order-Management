import axios from 'axios'
import oauth from 'axios-oauth-client'

const {console, host} = window
const KEY_PREFIX = btoa(host)
const TOKEN_KEY = KEY_PREFIX + '$_access_token'
const REMEMBER_TOKEN_KEY = KEY_PREFIX + '$_remember_token'

export default class Api extends Object {

    constructor(env, router, allows, redirectTo) {
        super()
        this.env = env
        this.router = router
        this.rememberToken = localStorage.getItem(REMEMBER_TOKEN_KEY) == 'true'
        this.accessToken = null
        this.allows = allows || []
        this.redirectTo = redirectTo || 'login'
        this.base = env.api.base
        this.authenticate()
    }

    setStore(store) {
        this.$store = store
    }

    setLoading(loading) {
        try {
            if (loading == true) {
                this.$store.state.loading = true
            } else {
                setTimeout(() => {
                    this.$store.state.loading = false
                }, 500)
            }
        } catch (e) {}
    }

    authenticate() {
        if (this.router.beforeHooks.length > 0)
            return

        if (this.allows.indexOf(this.redirectTo) < 0)
            this.allows.push(this.redirectTo)


        this.router.beforeEach((to, from, next) => {
            let token = this.getToken()
            let allow = false
            for (let i in this.allows) {
                allow = to.name == this.allows[i]
                if (allow)
                    break
            }

            if (allow || token) {
                next()
            } else {
                this.router.replace({name: this.redirectTo})
            }
        })

        axios.defaults.headers.common['Accept'] = 'application/json'

        axios.interceptors.request.use(config => {
            let token = this.getToken()
            if (token && token.access_token) {
                config.headers['Authorization'] = [
                    token.token_type, token.access_token].join(' ')
            }

            this.setLoading(true)
            return config
        }, error => {
            this.setLoading(false)
            return Promise.reject(error)
        })

        $.ajaxSetup({
            beforeSend: (xhr) => {
                this.setLoading(true)
                let token = this.getToken()
                if (token) {
                    xhr.setRequestHeader('Authorization',
                        [token.token_type, token.access_token].join(' '))
                }
            },
            complete: () => {
                this.setLoading(false)
            }
        })

        // eslint-disable-next-line
        $(document).ajaxError((event, response, settings, thrownError) => {
            this.setLoading(false)
            if (this.getRouteName() != 'login' && response.status == 401) {
                let token = this.getToken()
                if (token && this.rememberToken) {
                    oauth.client(axios.create(), {
                        url: this.env.api.token,
                        grant_type: 'refresh_token',
                        client_id: env.oauth.client_id,
                        client_secret: env.oauth.client_secret,
                        refresh_token: token.refresh_token
                    }).call(this).then((res) => {
                        this.setToken(res, true)
                        window.console.clear()
                        $.ajax(settings)
                    }).catch(err => this.logout(err))
                } else {
                   window.console.clear()
                   this.logout()
                }
            }
        })

        axios.interceptors.response.use((response) => {
            this.setLoading(false)
            return response;
        }, (error) => {
            this.setLoading(false)
            if (this.getRouteName() != 'login' && error.response.status == 401) {
                let token = this.getToken()
                if (token && this.rememberToken) {
                    return oauth.client(axios.create(), {
                        url: this.env.api.token,
                        grant_type: 'refresh_token',
                        client_id: env.oauth.client_id,
                        client_secret: env.oauth.client_secret,
                        refresh_token: token.refresh_token
                    }).call(this).then((res) => {
                        this.setToken(res, true)
                        window.console.clear()
                        error.config.headers['Authorization'] = res.access_token
                        return axios.request(error.config)
                    }).catch(err => this.logout(err))
                } else {
                    window.console.clear()
                    this.logout()
                }
            }
            return Promise.reject(error)
        })
    }

    getRouteName() {
        let hist = this.router.history || {}
        let curr = hist.current || {}
        return curr.name || ''
    }

    login(username, password) {
        let env = this.env;
        return oauth.client(axios.create(), {
            url: env.api.token,
            grant_type: 'password',
            client_id: env.oauth.client_id,
            client_secret: env.oauth.client_secret,
            username: username,
            password: password
        }).call(this)
    }

    logout(errorException) {
        localStorage.clear();
        this.accessToken = undefined
        this.rememberToken = false
        if (this.router) {
            this.router.replace({name: this.redirectTo})
        }
        if (errorException)
            return Promise.reject(errorException)
    }

    setToken(accessToken, remember) {
        this.accessToken = accessToken
        this.rememberToken = remember === true
        this.accessToken.expires_in *= 1000
        this.accessToken.expires_in += new Date().getTime()
        localStorage.setItem(REMEMBER_TOKEN_KEY, this.rememberToken)
        localStorage.setItem(TOKEN_KEY, JSON.stringify(this.accessToken))
    }

    getToken() {
        if (this.accessToken)
            return this.accessToken
        let accessToken = localStorage.getItem(TOKEN_KEY)
        if (accessToken) {
            try {
                this.accessToken = JSON.parse(accessToken)
            } catch (e) {
                console.log('Failed to parsing token')
            }
        }

        return this.accessToken
    }

    isExpiredToken() {
        let token = this.getToken()
        if (!token)
            return true
        return token && token.expires_in <= new Date().getTime()
    }

    isTokenRemember() {
        let remember = localStorage.getItem(REMEMBER_TOKEN_KEY)
        return this.rememberToken = remember == 'true'
    }

    get(path, params) {
        return axios.get(this.env.api.base + path, {params})
    }

    post(path, data) {
        let headers;
        if (data instanceof FormData) {
            headers = {
                'Content-type': 'multipart/form-data'
            }
        }

        return axios.post(this.env.api.base + path, data, headers)
    }

    put(path, data) {
        let headers;
        if (data instanceof FormData) {
            headers = {
                'Content-type': 'multipart/form-data'
            }
        }

        return axios.put(this.env.api.base + path, data, headers)
    }

    delete(path, data) {
        return axios.delete(this.env.api.base + path, data || {})
    }

    download(path, name) {
        return axios.get(this.env.api.base + path, {
            responseType: 'blob'
        }).then((res) => {
            var url = window.URL.createObjectURL(new Blob([res.data]))
            var link = document.createElement('a')
            link.href = url
            link.setAttribute('download', name)
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link)
            return Promise.resolve(res)
        })
    }

    forgot(path, data) {
        let headers;
        if (data instanceof FormData) {
            headers = {
                'Content-type': 'multipart/form-data'
            }
        }
        return axios.post(env.api.oauth + path, data, headers)
    }

    reset(path, data) {
        let headers;
        if (data instanceof FormData) {
            headers = {
                'Content-type': 'multipart/form-data'
            }
        }
        return axios.post(env.api.oauth + path, data, headers)
    }
}
