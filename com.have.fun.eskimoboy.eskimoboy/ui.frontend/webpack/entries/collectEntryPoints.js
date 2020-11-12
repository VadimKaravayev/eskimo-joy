const path = require('path');
const glob = require('glob');

function collectEntryPoints(sourceRootFolder, entryPointTypes, extension = '(j|t)s?(x)') {
  const innerCollectEntryPoints = (entryPointType) =>
    glob.sync(path.resolve(sourceRootFolder, '**', `*.${entryPointType}.${extension}`));

  return entryPointTypes
    .reduce((a, entryPoint) => [...a, ...innerCollectEntryPoints(entryPoint)], [])
    .reduce((a, filePath) => {
      const fileName = path.basename(filePath);
      const pathPrefix = path
        .dirname(filePath)
        .replace(sourceRootFolder, '')
        .replace(new RegExp('/', 'g'), '.');
      const chunkName = `${pathPrefix}.${fileName.match(/[^.]*/)}`;

      return {
        ...a,
        [chunkName]: filePath
      };
    }, {});
}

module.exports = collectEntryPoints;
