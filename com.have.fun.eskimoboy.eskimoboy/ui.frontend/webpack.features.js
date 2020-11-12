'use strict';

const path = require('path');
const glob = require('glob');
const merge = require('webpack-merge');
const webpack = require('webpack');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const common = require('./webpack.common.js');

const SOURCE_ROOT = path.resolve(__dirname, 'src');
const entryPointTypes = [
    'component',
    'service',
];

function collectEntryPoints() {
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
    entry: () => collectEntryPoints(),
    optimization: {
        moduleIds: 'hashed',
        minimize: true,
        splitChunks: {
            chunks: "all",
        }
    },
    externals: {
        'react': 'React',
        'react-dom': 'ReactDOM',
        'axios': 'Axios',
        '@vlocity-cme-sdk/digitalcommerce-sdk': 'VlocitySDK',
    }
});
