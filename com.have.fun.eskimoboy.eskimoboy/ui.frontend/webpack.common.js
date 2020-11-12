'use strict';

const path                    = require('path');
const webpack                 = require('webpack');
const MiniCssExtractPlugin    = require("mini-css-extract-plugin");
const TSConfigPathsPlugin     = require('tsconfig-paths-webpack-plugin');
const CopyWebpackPlugin       = require('copy-webpack-plugin');
const { CleanWebpackPlugin }  = require('clean-webpack-plugin');

const moduleCSSRule = require('./webpack/config/moduleCSSRule');
const defaultCSSRule = require('./webpack/config/defaultCSSRule');

const SOURCE_ROOT = __dirname + '/src/main/webpack';

module.exports = {
        resolve: {
            extensions: ['.js', '.ts', '.jsx', '.tsx'],
            plugins: [new TSConfigPathsPlugin({
                configFile: "./tsconfig.json"
            })]
        },
        output: {
            filename: 'clientlib-site/js/[name].bundle.js',
            path: path.resolve(__dirname, 'dist')
        },
        module: {
            rules: [
               {
                   oneOf: [
                       moduleCSSRule,
                       defaultCSSRule,
                   ]
               },
               {
                   test: /\.tsx?$/,
                   exclude: [
                       /(node_modules)/
                   ],
                   use: [
                       {
                           loader: "ts-loader"
                       },
                       {
                           loader: "webpack-import-glob-loader",
                           options: {
                               url: false
                           }
                       }
                   ]
               },
               {
                   test: /\.(ico|jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2)(\?.*)?$/,
                   use: {
                       loader: 'file-loader',
                       options: {
                           name: '[path][name].[ext]'
                       }
                   }
               },
            ]
        },
        plugins: [
            new CleanWebpackPlugin(),
            new webpack.NoEmitOnErrorsPlugin(),
            new MiniCssExtractPlugin({
                filename: 'clientlib-site/css/[name].bundle.css',
            }),
            new CopyWebpackPlugin([
                { from: path.resolve(__dirname, SOURCE_ROOT + '/resources'),
                to: './clientlib-site/resources' }
            ]) 
        ],
        stats: {
            assetsSort: "chunks",
            builtAt: true,
            children: false,
            chunkGroups: true,
            chunkOrigins: true,
            colors: false,
            errors: true,
            errorDetails: true,
            env: true,
            modules: false,
            performance: true,
            providedExports: false,
            source: false,
            warnings: true
        }
};
