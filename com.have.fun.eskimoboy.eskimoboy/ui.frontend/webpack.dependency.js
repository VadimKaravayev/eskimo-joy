'use strict';

const path = require('path');
const glob = require('glob');
const merge = require('webpack-merge');
const common = require('./webpack.common.js');
const TerserPlugin = require('terser-webpack-plugin');

const SOURCE_ROOT = path.resolve(__dirname, 'src');
const entryPointTypes = [
    'dependency',
];

function collectDependencyEntryPoints() {
    const __collectEntryPoints = (entryPointType) => {
        return glob.sync(path.resolve(SOURCE_ROOT, `**`, `*.${entryPointType}.+(j|t)s?(x)`));
    }

    return entryPointTypes
        .reduce((a, entryPoint) => [...a, ...__collectEntryPoints(entryPoint)], [])
        .reduce((a, filePath) => {
            const fileName = path.basename(filePath);
            const chunkName = fileName.match(/[^\.]*/);

            return {
                ...a,
                [chunkName]: filePath
            }
        }, {});
}

module.exports = merge(common, {
    entry: () => collectDependencyEntryPoints(),
    output: {
        filename: 'clientlib-site/js/[name].js',
        path: path.resolve(__dirname, 'globals'),
    },
    mode: 'production',
    optimization: {
        splitChunks: {
            chunks: 'all',
            // cacheGroups: {
            //   commons: {
            //     name: 'commons',
            //     chunks: 'initial',
            //   }
            // }
        },
        minimizer: [
            new TerserPlugin(),
        ]
    },
    devtool: 'none',
    performance: {hints: false}
});
