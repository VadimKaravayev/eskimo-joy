const path = require('path');
const base = require('./clientlib.config');

const clientLibRoot = path.resolve(
  __dirname,
  ...`aemsync/jcr_root/apps/eskimoboy/clientlibs/ui.frontend`.split(`/`)
)

module.exports = {
  ...base,
  clientLibRoot,
};
