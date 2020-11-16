import React from "react";
import classNames from "classnames";

import styles from './styles.module.scss';

type LinkRowProps =  {
  children: React.ReactNode;
  to: string;
  cssClass?: string;
  target: string;
}

export const LinkRow: React.FC<LinkRowProps> = ({
  children,
  to,
  cssClass,
  target,
  ...restProps
}) => (
  <a
    href={to}
    className={classNames(styles.linkRow, {
      [cssClass]: cssClass
    })}
    target={target}
    data-analytics-link-type='link'
    data-analytics-link={children}
    {...restProps}
  >
    <strong>{children}</strong>
  </a>
);
