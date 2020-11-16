import * as React from 'react';
import classNames from 'classnames';

import styles from './index.module.scss';

interface ColoredButtonProps extends React.BaseHTMLAttributes<HTMLButtonElement> {
    children: React.ReactChildren;
    className?: string;
    theme?: string;
    type?: 'submit' | 'reset' | 'button';
    analyticsButtonName?: string;
    analyticsButtonType?: string;
}

export const ColoredButton: React.FC<ColoredButtonProps> = ({
    children,
    className,
    theme,
    type = 'button',
    analyticsButtonName,
    analyticsButtonType,
    ...restProps

}) => (
    <button
        className={classNames(styles.coloredButton, className, styles[theme])}
        {...restProps}
        type={type}
        data-analytics-link-type={analyticsButtonType}
        data-analytics-link={analyticsButtonName}
    >
        {children}
    </button>
);
