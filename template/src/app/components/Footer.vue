<template>
    <footer class="main-footer">
        <strong>Copyright &copy; 2019-{{ year }} <a href="">{{ env.app_name }}</a>.</strong>
        All rights reserved.
        <div class="float-right d-none d-sm-inline-block">
            <b>Version</b> {{ version }}
        </div>
    </footer>
</template>

<script>
export default {
    data() {
        return {
            year: new Date().getFullYear(),
            version: env.version || '0.0.1'
        }
    }
}
</script>
