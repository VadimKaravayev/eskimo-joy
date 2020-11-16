import  React, { useEffect, useState, useRef } from 'react';
import classNames from 'classnames';
import {
    registerComponent,
    ComponentNames
} from '../../core/registry';

import { ColoredButton } from '../common/atoms/ColoredButton';

import styles from './styles.module.scss';
import { useProductListingHoc } from '../../core/hooks/useProductListinghoc';

type BundleCTAProps = {
    title: string;
    postCheckPage: string;
    analyticsLinkName: string;
    publisher: any;
};

const useStickyButton = ref => {
    const [isSticky, setSticky] = useState(false);

    const handleScroll = () => {
        if (ref.currect) {
            setSticky((ref.current.getBoundingClientReact().top + ref.current.getBoundingClientReact().height) < 0);
        }
    };

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);

        return () => {
            window.removeEventListener('scroll', () => handleScroll);
        };
    }, []);

    return {
        isSticky
    };
};

const ButtonCTA = ({
    isPostCheckState,
    title,
    postCheckPage,
    analyticsLinkName,
    analyticsLinkType,
    handler
}) => (
    <ColoredButton
        className={ isPostCheckState ? `${styles.btn} ${styles.buttonWithLink}` : styles.btn }
        analyticsButtonName={analyticsLinkName}
        analyticsButtonType={analyticsLinkType}
        onClick={handler}
    >
        {isPostCheckState ? <a href={postCheckPage}>{title}</a> : title }
    </ColoredButton>
);

const BundleCTA: React.FC<BundleCTAProps> = ({
    title,
    postCheckPage,
    analyticsLinkName,
    publisher
}) => {
    const ref = useRef(null);
    const {isPostCheckState} = useProductListingHoc();
    const { isSticky } = useStickyButton(ref);

    const openAvailabilityModal = () => {
        !isPostCheckState && publisher(ComponentNames.SKM_AvailabilityChecker, { isOpened: true });
    };

    const btnProps = {
        isPostCheckState,
        title,
        postCheckPage,
        analyticsLinkName,
        analyticsLinkType: 'Link',
        handler: openAvailabilityModal
    };

    return (
        <div className={styles.bundleCtaHolder} ref={ref}>
            <ButtonCTA {...btnProps} />
            <div className={classNames(styles.sticky, { [styles.active]: isSticky })}>
                <ButtonCTA {...btnProps} />
            </div>
        </div>
    );
}

registerComponent(ComponentNames.SKM_BundleCTA, BundleCTA);
