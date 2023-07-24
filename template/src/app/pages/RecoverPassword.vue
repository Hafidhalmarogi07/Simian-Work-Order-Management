<template>
    <div class="login-box">
        <div class="login-logo">
            <router-link :to="{name: 'login'}"><b>Admin</b>LTE</router-link>
        </div>
        <!-- /.login-logo -->
        <div class="card">
            <div class="card-body login-card-body">
                <p class="login-box-msg">You are only one step a way from your new password, recover your password now.</p>

                <form @submit.prevent="submit" method="post">
                    <div class="input-group mb-3">
                        <input type="password" class="form-control" placeholder="Password">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                    </div>
                    <div class="input-group mb-3">
                        <input type="password" class="form-control" placeholder="Confirm Password">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary btn-block">Change password</button>
                        </div>
                        <!-- /.col -->
                    </div>
                </form>

                <p class="mt-3 mb-1">
                    <router-link :to="{name: 'login'}">Login</router-link>
                </p>
            </div>
            <!-- /.login-card-body -->
        </div>
    </div>
    <!-- /.login-box -->
</template>

<script>
export default {
    mounted() {
        $('body').attr('class', 'hold-transition login-page')
        $('body,html').removeAttr('style')
        $('body').Layout('fixLayoutHeight')
    },
    methods: {
        submit() {
            this.$router.push({name: 'login'});
        }
    }
}
</script>
