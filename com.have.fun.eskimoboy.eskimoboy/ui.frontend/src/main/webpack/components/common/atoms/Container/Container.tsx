import React from 'react';
import styles from './styles.module.scss';

export const Container: React.FC<any> = ({children}) => (
    <div className={styles.container}>
        {children}
    </div>
);