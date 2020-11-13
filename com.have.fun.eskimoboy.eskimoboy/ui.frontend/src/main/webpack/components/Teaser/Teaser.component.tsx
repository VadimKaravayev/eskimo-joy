import React from 'react';
import {registerComponent, ComponentNames} from '../../core/registry';

import styles from './styles.module.scss';

import {Container} from '../common/atoms';

type TeaserProps = {
    headline?: string,
    text?: string,
    image?: string,
};

const Teaser: React.FC<TeaserProps> = ({
    headline,
    text,
    image
}) => (
    <Container>
        <div className={styles.teaser}>
            <div className={styles.teaserImage} style={{ backgroundImage: `url('${image}')`}} />
            <div className={styles.teaserContent}>
                <strong className={styles.title}>{headline}</strong>
                <p>{text}</p>
            </div>
        </div>
    </Container>
);

registerComponent(ComponentNames.SKM_Teaser, Teaser);