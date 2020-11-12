const path = require('path');

const defaults = {
  allowProxy: true,
  serializationFormat: 'xml',
  cssProcessor: ['default:none', 'min:none'],
  jsProcessor: ['default:none', 'min:none'],
};

 // path to the clientlib root folder (output)
const clientLibRoot = path.resolve(
  __dirname,
  ...'./../ui.apps/src/main/content/jcr_root/apps/eskimoboy/clientlibs/ui.frontend'.split('/')
);


module.exports = {
    // default working directory (can be changed per 'cwd' in every asset option)
    context: __dirname,
    clientLibRoot,
    libs: [
        {
            ...defaults,
            name: "clientlib-dependencies",
            categories: ["eskimoboy.dependencies"],
            assets: {
                js: [
                    "dist/clientlib-dependencies/*.js",
                ],
                css: [
                    "dist/clientlib-dependencies/*.css"
                ]
            }
        },
        {
            ...defaults,
            name: "clientlib-site",
            categories: ["eskimoboy.site"],
            dependencies: ["eskimoboy.dependencies"],
            assets: {
                js: [
                    "dist/clientlib-site/*.js",
                ],
                css: [
                    "dist/clientlib-site/*.css"
                ],
                resources: [
                    {src: "dist/clientlib-site/resources/images/*.*", dest: "images/"},
                    {src: "dist/clientlib-site/resources/fonts/*.*", dest: "fonts/"},
                ]
            }
        },
        {
          ...defaults,
          name: 'core',
          categories: ['skm.core'],
          assets: {
            js: [
              'globals/clientlib-site/js/vendors~core.js',
              'globals/clientlib-site/js/core.js'
            ],
            css: [
              'node_modules/normalize.css/normalize.css'
            ],
          }
        },
        {
          ...defaults,
          name: 'axios',
          categories: ['skm.axios'],
          assets: {
            js: [
              'globals/clientlib-site/js/vendors~axios.js',
              'globals/clientlib-site/js/axios.js'
            ],
          }
        },
        {
          ...defaults,
          name: 'commons',
          categories: ['ui.frontend.commons'],
          assets: {
            js: [
              'dist/clientlib-site/js/*commons.bundle.js',
            ],
            css: [
              'dist/clientlib-site/css/*commons.bundle.css',
            ],
          }
        },
            {
              ...defaults,
              name: 'vendors',
              categories: ['ui.frontend.vendors'],
              assets: {
                js: [
                  'dist/clientlib-site/js/*vendors.bundle.js',
                ],
              }
            },
        {
              ...defaults,
              name: 'skm.Alpha',
              categories: ['skm.Alpha'],
              dependencies: [
                'skm.core',
                'ui.frontend.commons',
                'ui.frontend.vendors'
              ],
              assets: {
                js: [
                  'dist/clientlib-site/js/*Alpha*.js',
                ],
                css: [
                  'dist/clientlib-site/css/*Alpha*.css',
                ],
              }
        },
        {
          ...defaults,
          name: 'skm.SeoContent',
          categories: ['skm.SeoContent'],
          dependencies: [
            'skm.core',
            'ui.frontend.commons',
            'ui.frontend.vendors'
          ],
          assets: {
            js: [
              'dist/clientlib-site/js/*SeoContent*.js',
            ],
            css: [
              'dist/clientlib-site/css/*SeoContent*.css',
            ],
          }
        },
        {
          ...defaults,
          name: 'skm.Teaser',
          categories: ['skm.Teaser'],
          dependencies: [
            'skm.core',
            'ui.frontend.commons',
            'ui.frontend.vendors'
          ],
          assets: {
            js: [
              'dist/clientlib-site/js/*Teaser*.js',
            ],
            css: [
              'dist/clientlib-site/css/*Teaser*.css',
            ],
          }
        },
        {
          ...defaults,
          name: 'skm.BundleCTA',
          categories: ['skm.BundleCTA'],
          dependencies: [
            'skm.core',
            'ui.frontend.commons',
            'ui.frontend.vendors'
          ],
          assets: {
            js: [
              'dist/clientlib-site/js/*BundleCTA*.js',
            ],
            css: [
              'dist/clientlib-site/css/*BundleCTA*.css',
            ],
          }
        },
    ]
};
