<template>
        <div class="login-box">
            <!-- /.login-logo -->
            <!-- <img src="@assets/logo.png" class="login-banner" :alt="env.app_name"> -->
            <div class="card">
                <div class="login-logo">
                    <a href="./">
                        <b>{{ env.app_name }}</b>
                    </a>
                </div>
                <div class="card-body login-card-body">
                    <!-- <p class="login-box-msg">
                        Sign in to start your session
                    </p> -->
                    <div class="alert alert-danger alert-dismissible" v-if="error">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h5>
                            <i class="icon fa fa-ban"></i>
                            Signin failed!
                        </h5>
                        <p>{{ error }}</p>
                    </div>
                    <form @submit.prevent="submit" method="post">
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" autocomplete="off" v-model="form.username" placeholder="Username" :disabled="loading" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-envelope"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input type="password" class="form-control" autocomplete="off" v-model="form.password" placeholder="Password" :disabled="loading" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-7">
                                <div class="icheck-primary">
                                    <input type="checkbox" v-model="form.remember" id="remember" :disabled="loading">
                                    <label for="remember">
                                        Remember Me
                                    </label>
                                </div>
                            </div>
                            <!-- /.col -->
                            <div class="col-5">
                                <button type="submit" class="btn btn-secondary btn-block btn-flat" :disabled="loading">
                                    <i class="fas fa-spinner fa-spin" v-if="loading"></i>
                                    {{ loading ? 'Signing in ...' : 'Sign In' }}
                                </button>
                            </div>
                            <!-- /.col -->
                        </div>
                    </form>

                    <div class="social-auth-links text-center mb-3">
                        <p>- OR -</p>
                        <router-link :to="{name: 'forgot_password'}">I forgot my password</router-link>

                        <!-- <a href="#" class="btn btn-block btn-primary">
                            <i class="fab fa-facebook mr-2"></i> Sign in using Facebook
                        </a>
                        <a href="#" class="btn btn-block btn-danger">
                            <i class="fab fa-google-plus mr-2"></i> Sign in using Google+
                        </a> -->
                    </div>
                    <!-- /.social-auth-links -->

                    <!-- <p class="mb-1">
                        <router-link :to="{name: 'forgot_password'}">I forgot my password</router-link>
                    </p> -->
                    <!-- <p class="mb-0">
                        <router-link :to="{name: 'register'}" class="text-center">Register a new membership</router-link>
                    </p> -->
                </div>
                <!-- /.login-card-body -->
            </div>
        </div>
</template>

<script>
const {console} = window
export default {
    data: () => ({
        title_submit : "Sign In",
        form: {},
        error: undefined,
        loading: false
    }),
    mounted() {
        if (Api.getToken()) {
            this.$router.replace({name: 'dashboard'})
        }
        $('body').attr('class', 'hold-transition login-page text-sm')
        $('body,html').removeAttr('style')
        new adminlte.Layout('body', {})
    },
    methods: {
        submit() {
            this.title_submit = "Processing..."
            this.loading = true
            Api.login(this.form.username, this.form.password)
            .then(res => {
                this.loading = false
                Api.setToken(res, this.form.remember)
                this.$router.replace({name: 'dashboard'})
            }).catch(err => {
                this.loading = false
                this.title_submit = "Sign In"
                if(err.response.data.error_description == "Bad credentials"){
                    this.error = "Incorrect username or password"
                }
                console.log(this.error)
            })
        }
    }
}
</script>

<style>
.login-banner {
    width: 100px;
    margin: auto;
    display: block;
}
.login-page {
    background: #333333 !important;
}
.login-page .login-logo{
    padding-top: 20px;
    margin-bottom: 0px;
}
.login-page .login-logo img{
    background-position: center center;
    width: 26px;
    height: 26px;
    margin-top: -6px;
    margin-right: 6px;
}
</style>
