import React from 'react';
import classNames from "classnames";

import styles from './styles.module.scss';

type ToggleButtonProps = {
  checkedState: boolean;
  onClick: () => void;
};

export const ToggleButton: React.FC<ToggleButtonProps> = ({
  checkedState,
  onClick,
  children
}) => (
  <button
    type='button'
    onClick={onClick}
    className={classNames(styles.toggleButton, { [styles.checked]: checkedState })}
    data-analytics-link-type='link'
    data-analytics-link={`Triple play toggle:${checkedState ? 'no' : 'yes'}`}
  >
    {children}
    <span className={styles.toggleButtonCircle} />
  </button>
);
