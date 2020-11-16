import React from 'react';
import {registerComponent, ComponentNames} from '../../core/registry';
import {Container} from '../common/atoms';

import styles from './styles.module.scss';

interface seoItem {
    title: string;
    description: string;
}

type SeoContentProps = {
    headline: string;
    headlineTag: string;
    secondaryHeadline: string;
    featureHeadline: string;
    seoItems: seoItem[];
};

const SeoContent: React.FC<SeoContentProps> = ({
    headline,
    headlineTag,
    secondaryHeadline,
    featureHeadline,
    seoItems
}) => {

    const Tag: any = headlineTag;

    const renderSeoItems = () => (
        seoItems.map((seoItem, index) => (
            <div key={index.toString()}>
                <strong className={styles.packageName}>{seoItem.title}</strong>
                <p className={styles.packageDescription}>{seoItem.description}</p>
            </div>
        ))
    );

    return (
        <div className={styles.dealsHolder}>
            <Container>
                <Tag className={styles.dealsTitle}>{headline}</Tag>
                <span className={styles.dealsSubtitle}>{secondaryHeadline}</span>
                <div className={styles.dealsPeace}>
                    <span className={styles.dealsPeaceHeading}>{featureHeadline}</span>
                    <div className={styles.packagesHolder}>{renderSeoItems()}</div>
                </div>
            </Container>
        </div>
    );
};

registerComponent(ComponentNames.SKM_SeoContent, SeoContent);
