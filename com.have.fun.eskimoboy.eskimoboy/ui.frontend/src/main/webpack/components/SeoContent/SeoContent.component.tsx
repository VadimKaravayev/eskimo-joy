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
    isHiddenHeadline?: string;
    headlineTag: string;
    secondaryHeadline: string;
    featureHeadline: string;
    seoItems: seoItem[];
};

const SeoContent: React.FC<SeoContentProps> = ({
    headline,
    isHiddenHeadline,
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

    const isHidden = isHiddenHeadline === 'true';
    const displayMode = isHidden ? 'none' : 'block';

    return (
        <div className={styles.dealsHolder} style={{ display: displayMode }}>
            <Container>
                <Tag className={styles.dealsTitle}>{headline}</Tag>
              { !isHidden && (
                <>
                  <span className={styles.dealsSubtitle}>{secondaryHeadline}</span>
                  <div className={styles.dealsPeace}>
                      <span className={styles.dealsPeaceHeading}>{featureHeadline}</span>
                      <div className={styles.packagesHolder}>{renderSeoItems()}</div>
                  </div>
                </>
              )}
            </Container>
        </div>
    );
};

registerComponent(ComponentNames.SKM_SeoContent, SeoContent);
