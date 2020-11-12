const fs = require('fs');
const os = require('os');
const loaderUtils = require('loader-utils');
const path = require('path');

// See: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Lexical_grammar#Reserved_keywords_as_of_ECMAScript_2015
const RESERVED_WORDS = new Set([
  'break',
  'case',
  'catch',
  'class',
  'const',
  'continue',
  'debugger',
  'default',
  'delete',
  'do',
  'else',
  'export',
  'extends',
  'finally',
  'for',
  'function',
  'if',
  'import',
  'in',
  'instanceof',
  'new',
  'return',
  'super',
  'switch',
  'this',
  'throw',
  'try',
  'typeof',
  'var',
  'void',
  'while',
  'with',
  'yield'
]);

const EXPORT_LOCALS_RE = /(^|\n)exports\.locals[\s]*=[\s]*{/;
const CSS_CLASS_NAME_KEY_RE = /"([^\\"]+)":/g;
const ALPHABET_ONLY_CHARS_RE = /^[a-zA-Z]+$/i;

module.exports = function createTypeDeclarations(content, map, meta) {
  this.cacheable();
  const callback = this.async();

  let typeDecls = '';
  const options = loaderUtils.getOptions(this) || {};

  if (options.banner && typeof options.banner === 'string') {
    typeDecls += `${options.banner}\n`;
  }

  const cssClassNames = [];

  let match;

  const locals = content.split(EXPORT_LOCALS_RE).pop() || '';
  while (match = CSS_CLASS_NAME_KEY_RE.exec(locals), !!match) {
    const key = match[1];

    if (!cssClassNames.includes(key) && isAllowedKey(key)) {
      cssClassNames.push(key);
    }
  }

  for (const key of cssClassNames) {
    typeDecls += `export const ${key}: string;\n`;
  }

  if (typeDecls !== '') {
    const filename = typeDefsFilename(this.resourcePath);
    const options = {
      encoding: 'utf-8',
      mode: 0o644
    };
    fs.writeFile(filename, typeDecls.replace(/\n/g, os.EOL), options, () => {
      callback(null, content, map, meta);
    });
  } else {
    callback(null, content, map, meta);
  }
};

function isAllowedKey(key) {
  return !RESERVED_WORDS.has(key) && ALPHABET_ONLY_CHARS_RE.test(key);
}

function typeDefsFilename(filename) {
  const dirname = path.dirname(filename);
  const basename = path.basename(filename);
  return path.join(dirname, `${basename}.d.ts`);
}
