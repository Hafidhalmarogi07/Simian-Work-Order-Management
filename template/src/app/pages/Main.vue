<template>
    <div class="wrapper">
        <router-view name="navbar"></router-view>
        <router-view name="sidebar"></router-view>
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <router-view name="header"></router-view>
            <!-- Main content -->
            <section class="content">
                <router-view></router-view>
            </section>
        </div>
        <!-- /.content-wrapper -->
        <router-view name="footer"></router-view>
    </div>
</template>

<script>
export default {
    mounted() {
        $('body,html').removeAttr('style')
    }
}
</script>
