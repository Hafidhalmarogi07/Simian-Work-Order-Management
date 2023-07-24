<template>
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0 text-dark capitalize">
                        {{ title }}
                        <small class="text-gray" style="margin-left: 8px; font-size: 12px" v-if="subtitle">{{ subtitle }}</small>
                    </h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><router-link :to="{ name: 'dashboard' }">Home</router-link></li>
                        <li class="breadcrumb-item active">{{ title }}</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
</template>

<script>
export default {
    computed: {
        title() {
            let title = this.$store.state.title || 'Dashboard'
            window.document.title = [this.env.app_name, title].join(' | ')

            return title
        },
        subtitle() {
            if (this.$store.state.subtitle) {
                return this.$store.state.subtitle
            }

            return ''
        }
    }
}
</script>
