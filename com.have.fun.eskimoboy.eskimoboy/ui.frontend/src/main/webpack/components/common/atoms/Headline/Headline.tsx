import * as React from 'react';
import classNames from 'classnames';

import './styles.scss';

type positionType = 'center' | 'left';

interface HeadlineProps {
  text: string;
  position?: positionType;
  cssClass?: string;
}

export const Headline: React.FC<HeadlineProps> = ({
  text,
  position = 'center',
  cssClass
}) => (
  <div className={classNames('headline', {
    [`headline__${position}`]: position,
    [cssClass]: cssClass
  })}>
    {text}
  </div>
);
