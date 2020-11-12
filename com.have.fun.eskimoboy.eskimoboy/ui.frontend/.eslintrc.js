module.exports = {
  env: {
    browser: true,
    es6: true,
  },
  extends: [
    'eslint:recommended',
    'airbnb',
    'plugin:react/recommended',
    'plugin:@typescript-eslint/recommended',
  ],
  settings: {
    'import/extensions': [],
    'import/parsers': {
      '@typescript-eslint/parser': ['.ts', '.tsx']
    },
    'import/resolver': {
      node: {
        paths: ['.'],
        extensions: ['.js', '.jsx', '.ts', '.tsx'],
      },
    },
    react: {
      createClass: 'createReactClass',
      pragma: 'React',
      version: 'detect',
    },
  },
  globals: {
    Atomics: 'readonly',
    SharedArrayBuffer: 'readonly',
    _: true
  },
  parserOptions: {
    ecmaVersion: 2018,
    sourceType: 'module',
  },
  rules: {
    'no-console': 'error',
    'no-debugger': 'error',
    'no-alert': 'error',
    'no-dupe-args': 'error',
    'no-dupe-keys': 'error',
    'no-duplicate-case': 'error',
    'no-empty': 'error',
    'no-extra-boolean-cast': 'error',
    'no-extra-semi': 'error',
    camelcase: 'error',
    'no-unused-vars': [
      'error',
      {
        args: 'after-used'
      }
    ],
    'linebreak-style': 'off',
    'no-trailing-spaces': [
      'error',
      {
        skipBlankLines: true,
        ignoreComments: true
      }
    ],
    indent: [
      'error',
      2
    ],
    'max-len': [
      'error',
      {
        code: 120,
        ignoreComments: true,
        ignoreTrailingComments: true
      }
    ],
    'require-jsdoc': 'off',
    'valid-jsdoc': 'off',
    'import/prefer-default-export': 'off',
    'import/extensions': 'off',
    'arrow-parens': 'off',
    'no-confusing-arrow': 'off',
    'implicit-arrow-linebreak': 'off',
    'no-prototype-builtins': 'off',
    'no-undef': 'off',
    'comma-dangle': 'off',
    'no-case-declarations': 'off',
    'no-unused-expressions': 'off',
    'no-useless-constructor': 'off',

    // REACT
    'react/jsx-filename-extension': 'off',
    'react/no-typos': 'warn',
    'react/jsx-props-no-spreading': 'off',
    'react/prop-types': 'off',
    'react/no-danger': 'off',
    'react/require-default-props': 'off',
    'react/state-in-constructor': 'off',
    'jsx-a11y/iframe-has-title': 'off',

    // TypeScript
    '@typescript-eslint/no-useless-constructor': ['error']
  }
};
