import React from 'react';
import {registerComponent, ComponentNames} from '../../core/registry';

import styles from './styles.module.scss';

import { Container, Headline } from '../common/atoms';
import { ArticleList, ArticleItemType } from '../common/molecules/ArticleList'


type BenefitsProps = {
  headline?: string;
  products: ArticleItemType[];
};

const Benefits: React.FC<BenefitsProps> = ({
  headline,
  products
}) => (
  <div className={styles.benefitsHolder}>
    <Container>
      <Headline text={headline} />
      <ArticleList articles={products} />
    </Container>
  </div>
);

registerComponent(ComponentNames.SKM_Benefits, Benefits);
