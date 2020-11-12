import React from 'react';
import {
    registerComponent,
    ComponentNames,
} from '../../core/registry';

import styles from './styles.module.scss';

type AlphaProps = {
    headline?: string,
    secondaryHeadline?: string
};

const Alpha: React.FC<AlphaProps> = ({
    headline,
    secondaryHeadline
}) => {
    return (
        <div>
            <h2>Hello Alpha</h2>
            <h4 className={styles.test}>{headline}</h4>
            <p className={styles.testTwo}>{secondaryHeadline}</p>
        </div>);
};

registerComponent(ComponentNames.SKM_Alpha, Alpha);
