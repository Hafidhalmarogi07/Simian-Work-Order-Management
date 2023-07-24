<template>
    <div>
        <table class="table"></table>
        <slot></slot>
    </div>
</template>

<script>
import 'datatables.net-bs4/css/dataTables.bootstrap4.css'
import 'datatables.net-fixedheader-bs4/css/fixedHeader.bootstrap4.css'
import 'datatables.net-colreorder-bs4/css/colReorder.bootstrap4.css'

import DataTable from 'datatables.net'
import 'jszip'
import 'pdfmake'
import 'datatables.net-bs4'
import 'datatables.net-buttons-bs4'
import 'datatables.net-buttons/js/buttons.colVis.js'
import 'datatables.net-buttons/js/buttons.flash.js'
import 'datatables.net-buttons/js/buttons.html5.js'
import 'datatables.net-buttons/js/buttons.print.js'
import 'datatables.net-colreorder-bs4'
import 'datatables.net-fixedcolumns-bs4'
import 'datatables.net-fixedheader'
import 'datatables.net-fixedheader-bs4'
import 'datatables.net-responsive-bs4'
import 'datatables.net-rowgroup-bs4'
import 'datatables.net-rowreorder-bs4'
import 'datatables.net-scroller-bs4'
import 'datatables.net-select-bs4'
const {console} = window
$.fn.dataTable.ext.errMode = 'none'

const props = ['url', 'buttons', 'title', 'icon', 'border', 'hover', 'striped', 'sm', 'rowNumbers', 'autocommit', 'filters']
const regx = /^(ao|i|b|fn|aa|ai|a|o|s)([A-Z][A-z0-9_]+)$/
for (let i in $.fn.dataTable.defaults) {
    props.push(i)
    if (i.match(regx)) {
        let str = i.replace(regx, '$2')
        let keyName = str[0].toLowerCase()+str.substring(1)
        props.push(keyName)
    }
}
const watchers = {}
watchers.config = function(config) {
    for (var v in config) {
        this.setup(v, config[v], false)
    }

    this.init(this.cnf)
    // if (config.url) {
    //     this.setup('url', config.url, false)
    // }
    // this.init($.extend(this.cnf, config))
}
for (var i in props) {
    watchers[props[i]] = (function(p){
        return function(value) {
            this.setup(p, value, true)
        }
    })(props[i])
}
props.push('config')

export default {
    name: 'data-tables',
    props: props,
    data() {
        return {
            table: null,
            dataTables: null,
            cnf: {},
            baseConfig: {
                pagingType: 'full_numbers',
                colReorder: true,
                fixedHeader: true,
                responsive: true,
                destroy: true,
                stateSave: false,
                stateDuration: -1,
                serverSide: false,
                processing: true,
                keys: true,
                displayLength: 10,
                autoWidth: false,
                language: {
                    paginate: {
                        first: '&laquo;',
                        previous: '&lsaquo;',
                        next: '&rsaquo;',
                        last: '&raquo;'
                    },
                    // search: "Cari",
                    // info: "Menampilkan _START_ s/d _END_ dari _TOTAL_ data",
                    // infoEmpty: "Menampilkan 0 s/d 0 dari 0 data",
                    processing: "<i class='fas fa-spinner fa-spin'></i> Loading....",
                    // zeroRecords: "Tidak ada data",
                    // emptyTable: "Tidak ada data ditemukan",
                    lengthMenu: "_MENU_"
                },
                select: {
                    style: 'os',
                    info: false,
                    items: 'row'
                },
                buttons: [],
                dom: "<'card'"+
                        "<'card-header with-border'"+
                            "<'row'"+
                                "<'col-sm-12 col-md-12 table-title'>"+
                                "<'col-sm-12 col-md-8'B>"+
                                "<'col-sm-12 col-md-4'f>"+
                                // "<'col-sm-12 col-md-4'l>"+
                            ">"+
                        ">"+
                        "<'card-body'"+
                            "t"+
                        ">"+
                        "<'card-footer'"+
                            "<'row'<'col-sm-12 col-md-5'li><'col-sm-12 col-md-7'p>>"+
                        ">"+
                    ">",
                ajax: {
                    cache: true,
                    dataSrc: (json) => {
                        json.recordsTotal = json.number_of_elements
                        json.recordsFiltered = json.total_elements
                        // json.iTotalRecords = json.number_of_elements
                        // json.iTotalDisplayRecords = json.total_elements
                        return json.content
                    },
                    data(params, settings) {
                        // console.log(settings)
                        let overrideParams = {}
                        try {
                            overrideParams.page = !params.start ? 0 : Math.round(params.start / params.length)
                            overrideParams.size = params.length

                            if (params.order.length > 0) {
                                let order = params.order[0]
                                overrideParams.sort = order.dir != 'desc' ? '' : '-'
                                overrideParams.sort+= params.columns[order.column].data
                                // params.columns = [params.columns[order.column]]
                            }
                            if (params.search.value) {
                                let columns = []
                                for (let c in params.columns) {
                                    let col = params.columns[c]
                                    if (col.searchable == false) {
                                        continue
                                    }
                                    columns.push(JSON.stringify(
                                        [col.data, "like", params.search.value.replace(/^\s+|\s+$/g, '')]
                                    ))
                                }
                                overrideParams.filters = '['+columns.join(',["or"],')+']'
                            }
                        } catch(e){
                            // console.log(e.message)
                        }

                        return overrideParams
                    }
                }
            },
            bootstrapButtons: {
                dom: {
                    container: {
                        tag: "div"
                        // className: "btn-group"
                    },
                    collection: {
                        tag: "div",
                        className: "dt-button-collection"
                    },
                    button: {
                        tag: "button",
                        className: "btn",
                        active: "active",
                        disabled: "disabled"
                    },
                    buttonLiner: {
                        tag: "",
                        className: ""
                    }
                }
            }
        }
    },
    created() {

        $.extend($.fn.dataTable.defaults, this.baseConfig)
        $.extend($.fn.dataTable.Buttons.defaults, this.bootstrapButtons)
        // $.fn.dataTable.defaults = baseConfig
        $.extend(this.cnf, this.config)
        for (var i in this.$props) {
            if (i != 'config' && typeof this.$props[i] != 'undefined') {
                this.setup(i, this.$props[i], false)
            } else if (i == 'config' && typeof this.$props[i] != 'undefined') {
                for (var v in this.$props[i]) {
                    this.setup(v, this.$props[i][v], false)
                }
            }
        }
    },
    mounted() {
        var me = this

        this.cnf.initComplete = function(settings) {
            var t = this.api()
            if (me.rowNumbers) {
                t.on('order.dt search.dt draw.dt', function () {
                    t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
                        cell.innerHTML = '<span class="float-right">'+(i+1)+'</span>'
                    });
                }).draw()
            }
            this.trigger('created')
        }

        this.table = $('table', this.$el).eq(0)
        this.table.off('click', 'tbody tr').on('click', 'tbody tr', function() {
            if (me.dataTable) {
                me.clickRow(me.dataTable.row(this))
            }
        })

        this.init(this.cnf)
        $(window).on('resize', () => {
            setTimeout(() => {
                if (this.dataTable) {
                    this.dataTable.columns.adjust().draw()
                }
            }, 500)
        })
    },
    methods: {
        clickRow(row) {
            this.$emit('click', row)
        },
        init(config) {
            if (!config.columns && this.cnf.columns) {
                config.columns = this.cnf.columns
            }
            if (!config.ajax) {
                config.ajax = {}
            }
            var hasDefaults = !!config.columns && (!!config.data || !!config.ajax.url)
            if (!hasDefaults) {
                hasDefaults = !!config.columns
            }
            if (!hasDefaults) {
                return
            }

            config.destroy = true
            config.serverSide = !config.data && !!config.ajax.url
            config.ajax = config.serverSide ? config.ajax : null

            if (this.autocommit !== false) {
                this.cnf = config;
                this.commit()
            }
        },
        commit() {
            this.$nextTick(() => {
                try {
                    if (this.table.closest('.modal').length) {
                        this.cnf.fixedHeader = false
                    } else {
                        if ($('body').hasClass('layout-navbar-fixed')) {
                            $.fn.dataTable.FixedHeader.defaults.headerOffset = $('.main-header').innerHeight()
                        } else {
                            $.fn.dataTable.FixedHeader.defaults.headerOffset = 0
                        }
                    }
                    this.dataTable = this.table.DataTable(this.cnf)
                    if (this.cnf.border) {
                        this.table.addClass('table-bordered')
                    } else {
                        this.table.removeClass('table-bordered')
                    }
                    if (!this.cnf.hover) {
                        this.table.removeClass('table-hover')
                    } else {
                        this.table.addClass('table-hover')
                    }
                    this.dataTable.columns.adjust()
                } catch (e){
                    console.log(e)
                }
            })
        },
        setup(key, value, reRender) {
            var cnf = {}
            cnf[key] = value
            if (key == 'url') {
                cnf = {
                    ajax: this.baseConfig.ajax,
                    processing: true,
                    bProcessing: true,
                    serverSide: true,
                    bServerSide: true,
                    searchDelay: 1000
                };
                cnf.ajax.url = value
            } else if (key == 'data') {
                cnf = {
                    data: value,
                    sAjaxSource: undefined,
                    processing: false,
                    bProcessing: false,
                    serverSide: false,
                    bServerSide: false,
                    searchDelay: 500
                };
            }
            $.extend(this.cnf, cnf)

            // console.log(JSON.parse(JSON.stringify(this.cnf)))

            if (reRender && this.columns && this.columns.length > 0) {
                this.init(this.cnf)
            }
        }
    },
    watch: {
        ...watchers,
        config(values) {
            for (var v in values) {
                this.setup(v, values[v], false)
            }
            this.commit()
        }
    },
    beforeDestroy() {
        console.log('destroy')
        try {
            if (this.dataTable) {
                this.dataTable.destroy()
            }
            if (this.table) {
                this.table.off()
            }
        } catch(e) {}
        this.dataTable = undefined
        this.table = undefined
    },
    destroyed() {
        // console.log('destroyed')
    }
}
</script>

<style>
.dataTables_length, .dataTables_info{
    display: inline-block;
    padding-right: 10px;
}
</style>
