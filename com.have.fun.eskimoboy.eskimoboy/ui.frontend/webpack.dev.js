const merge = require('webpack-merge');
const clientlib = require('aem-clientlib-generator');
const common = require('./webpack.features.js');

const clientLibGeneratorConfig = require('./clientlib.config.dev');

class AEMClientPlugin {
  constructor(options) {
    this.options = options;
  }

  apply(compiler) {
    compiler.hooks.afterEmit.tap('AEMClientPlugin', () => {
      const {
        clientLibRoot
      } = clientLibGeneratorConfig;

      const options = {
        verbose: true,
        clientLibRoot,
      };

      clientLibGeneratorConfig.libs.map(l => clientlib(l, options));
    });
  }
}

module.exports = merge(common, {
  cache: true,
  mode: 'development',
  devtool: 'inline-source-map',
  optimization: {
    moduleIds: 'hashed',
    minimize: false,
    splitChunks: false
  },
  performance: {
    hints: false
  },
  devServer: {
    inline: true,
    proxy: [{
      context: ['/content', '/etc.clientlibs'],
      target: 'http://localhost:4502',
    }]
  },
  plugins: [
    new AEMClientPlugin(),
  ]
});
