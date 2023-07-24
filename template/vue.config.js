const webpack = require('webpack')
const path = require('path')

module.exports = {
    outputDir: path.resolve(__dirname, '../src/main/resources/public'),
    publicPath: process.env.BASE_URL ? process.env.BASE_URL : '',
    filenameHashing: false,
    runtimeCompiler: true,
    configureWebpack: config => {
        config.plugins.push(new webpack.ProvidePlugin({
            $: 'jquery',
            jquery: 'jquery',
            'window.jQuery': 'jquery',
            jQuery: 'jquery',
            moment: 'moment',
            adminlte: 'admin-lte/dist/js/adminlte.js'
        }))
        config.externals = {
            ...config.externals,
            '$': 'jquery',
            'jQuery': 'jquery'
        }
        config.resolve.alias['@app'] = __dirname + '/src/app'
        config.resolve.alias['@components'] = __dirname + '/src/app/components'
        config.resolve.alias['@pages'] = __dirname + '/src/app/pages'
        config.resolve.alias['@config'] = __dirname + '/src/config'
        config.resolve.alias['@assets'] = __dirname + '/src/assets'
        config.resolve.alias['vue-router$'] = 'vue-router/dist/vue-router.esm.js'
    }
}
