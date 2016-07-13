/*
 * (C) Copyright 2016 Intelie (http://intelie.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
