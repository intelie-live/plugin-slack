var webpack = require('webpack'),
    path = require("path");

var exposedModules = {
    live: [
        'components/',
        'data/',
        'init/',
        'lib/',
        'services/',
        'widgets/'
    ],
    packages: [
        // react core
        'react',
        'react-dom',
        'react-addons-pure-render-mixin',
        'react-addons-shallow-compare',
        'react-addons-update',

        // react third-party components
        'react-bootstrap',
        'react-tagsinput',

        // libs
        'lodash',
        'moment',
        'codemirror',
        'codemirror/mode/javascript/javascript',
        'codemirror/mode/css/css',
        'codemirror/mode/groovy/groovy'
    ]
};

module.exports = {
    entry: './webapp/js/app.js',
    output: {
        path: path.join(__dirname, "/webapp/target/"),
        filename: "bundle.js"
    },
    resolveLoader: {
        root: path.join(__dirname, "node_modules")
    },
    externals: [
        function (context, request, callback) {
            if ((/^live\//.test(request)) || exposedModules.packages.indexOf(request) > -1)
                return callback(null, 'commonjs ' + request);
            callback();
        }
    ],
    module: {
        loaders: [
            {
                test: /\.js?$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel',
                query: {
                    presets: ['es2015', 'react']
                }
            },
            {
                test: /\.s?css$/,
                loader: 'style!css!sass'
            }
        ]
    },
    plugins: [
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false
            }
        })
    ]
};
