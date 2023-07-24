<template>
    <div class="modal fade" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" :class="modalClass">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">
                        <slot name="title">Large Modal</slot>
                    </h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <slot name="body">Modal body</slot>
                </div>
                <div class="modal-footer">
                    <slot name="footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Tutup</button>
                        <button type="submit" class="btn btn-primary" @click.prevent="save">Simpan</button>
                    </slot>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
const {console} = window
export default {
    name: 'modal',
    props: ['show', 'modalClass'],
    data: () => ({

    }),
    mounted() {
        $(this.$el)
        .on('hide.bs.modal', e => this.$emit('hide', e))
        .on('hidden.bs.modal', e => this.$emit('hidden', e))
        .on('show.bs.modal', e => this.$emit('show', e))
        .on('shown.bs.modal', (e) => {
            this.$emit('shown', e)
            $('input.form-control', $(e.target).closest('.modal')).eq(0).focus()
        })
    },
    methods: {
        save(e) {
            this.$emit('save', e)
        }
    },
    watch: {
        show(show) {
            $(this.$el).modal(show ? 'show' : 'hide')
        }
    },
    destroyed() {
        $(this.$el).off()
    }
}
</script>
