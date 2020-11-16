import React from 'react';
import { registerComponent, ComponentNames } from '../../core/registry';

import { Container, Headline } from '../common/atoms';
import { ArticleList, ArticleItemType } from '../common/molecules/ArticleList';

type ProductBreakdownProps = {
  headline?: string;
  products: ArticleItemType[];
};

const ProductBreakdown: React.FC<ProductBreakdownProps> = ({
  headline,
  products
}) => (
  <Container>
    <Headline text={headline} />
    <ArticleList articles={products} isArticleSmallSize />
  </Container>
);

registerComponent(ComponentNames.SKM_ProductBreakdown, ProductBreakdown);
