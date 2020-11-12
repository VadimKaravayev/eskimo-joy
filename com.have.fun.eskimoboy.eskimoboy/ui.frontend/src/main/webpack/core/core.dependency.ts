import React from 'react';
import ReactDOM from 'react-dom';

import {
  processLoading,
} from './loader';

window.React = React;
window.ReactDOM = ReactDOM;

processLoading();
