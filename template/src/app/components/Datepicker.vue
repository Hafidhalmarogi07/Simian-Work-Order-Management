<template>
    <div class="input-group">
        <div class="input-group-prepend">
            <span class="input-group-text">
                <i class="far fa-calendar-alt"></i>
            </span>
        </div>
        <input type="text" class="form-control">
    </div>
</template>

<script>

const {console} = window

export default {
    name: 'date-picker',
    props: ['config', 'value'],
    data() {
        return {
            input: null,
            conf: {},
            defaultConfig: {
                startDate: moment().startOf('hour'),
                endDate: moment().endOf('hour'),
                timePicker: false,
                singleDatePicker: true,
                showDropdowns: true,
                autoApply: true,
                locale: {
                    format: 'YYYY-MM-DD'
                    // format: 'YYYY-MM-DD HH:mm:ss'
                }
            }
        }
    },
    mounted() {
        this.input = $('input', this.$el)
        this.conf = $.extend({}, this.config, this.defaultConfig)
        if (this.value) {
            if (typeof this.value == 'object') {
                this.conf.startDate = moment(this.value[0])
                this.conf.endDate = moment(this.value[1])
            } else {
                this.conf.startDate = moment(this.value)
                this.conf.endDate = moment(this.value)
            }
        }

        this.input.daterangepicker(this.conf)
        this.input.on('apply.daterangepicker', (ev, picker) => {
            var startDate = picker.startDate.format(this.conf.locale.format);
            var endDate = picker.endDate.format(this.conf.locale.format)
            if (this.conf.singleDatePicker) {
                this.$emit('input', startDate)
            } else {
                this.$emit('input', [startDate, endDate])
            }
        })
    },
    watch: {
        value(val) {
            if (typeof val == 'object' && val.length == 2) {
                val = val.join(' - ')
            }
            this.input.val(val)
        }
    }
}
</script>