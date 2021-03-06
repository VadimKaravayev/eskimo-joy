const merge                   = require('webpack-merge');
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin");
const TerserPlugin            = require('terser-webpack-plugin');
const common                  = require('./webpack.features.js');

module.exports = merge(common, {
   mode: 'production',
   optimization: {
      minimizer: [
          new TerserPlugin(),
          new OptimizeCSSAssetsPlugin({
              cssProcessorPluginOptions: {
                  cssProcessor: require('cssnano'),
                  preset: ['default', {
                      calc: true,
                      convertValues: true,
                      discardComments: {
                          removeAll: true
                      },
                      discardDuplicates: true,
                      discardEmpty: true,
                      mergeRules: true,
                      normalizeCharset: true,
                      reduceInitial: true, // This is since IE11 does not support the value Initial
                      svgo: true
                  }],
              },
              canPrint: false
          })
      ],
      splitChunks: {
        cacheGroups: {
          defaultVendors: {
            name: 'vendors',
            test: /[\\/]node_modules[\\/]/,
          },
          commons: {
            name: 'commons',
            chunks: 'initial',
            minChunks: 3
          }
        }
      }
   },
   devtool: 'none',
   performance: {hints: false}
});
