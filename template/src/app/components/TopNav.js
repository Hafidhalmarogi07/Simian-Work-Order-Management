export default {
    template: '<div/>',
    mounted() {
        $('body').attr('class', 'hold-transition layout-top-nav text-sm')
        $('[data-widget="pushmenu"]').closest('.nav-item').hide();
    }
}
