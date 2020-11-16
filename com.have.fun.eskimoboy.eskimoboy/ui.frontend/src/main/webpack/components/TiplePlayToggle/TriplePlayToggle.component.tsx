import React, { useState, useEffect } from 'react';
import classNames from 'classnames';

import { registerComponent, ComponentNames } from '../../core/registry';

import { LinkRow, ToggleButton, SimCardIcon } from '../common/atoms';
import { richText } from '../../core/utils/richText';

import styles from './styles.module.scss';
import { TriplePlayToggleProps } from './types';

const TriplePlayToggle: React.FC<TriplePlayToggleProps> = ({
  headline,
  secondaryHeadline,
  toggleText,
  linkTitle,
  linkPath,
  analyticsLinkName,
  isActiveByDefault,
  queryParameterName,
  cssClass,
  publisher,
}) => {

  const queryParameterValue = new URLSearchParams(document.location.search).get(queryParameterName);
  const isTriplePlayActivated = queryParameterValue ? queryParameterValue === 'true' : isActiveByDefault === 'true';
  const [isActive, setActiveState] = useState(isTriplePlayActivated);

  useEffect(() => {
    setTimeout(() => {
      publisher(ComponentNames.SKM_ProductListing, { isSimCardDisplayed: isActive});
    }, 300);
  }, []);

  useEffect(() => {
    publisher(ComponentNames.SKM_ProductListing, { isSimCardDisplayed: isActive });
  }, [isActive]);

  const onSwitchToggle = () => {
    setActiveState(state => !state);
  };

  return (
    <div
      className={classNames(styles.triplePlayWrapper, {
        [cssClass]: cssClass
      })}
    >
      <div className={styles.triplePlayHolder}>
        <strong className={styles.triplePlayHeading}>
          {headline}
        </strong>
        <div
          className={styles.triplePlayDescription}
          dangerouslySetInnerHTML={
            { __html: richText(secondaryHeadline)}
          }
        />
        {linkTitle && linkPath && (
          <LinkRow
            to={linkPath}
            target={analyticsLinkName}
            cssClass={styles.triplePlayLink}
            data-analytics-link={analyticsLinkName}
          >
            {linkTitle}
          </LinkRow>
        )}
        <div className={styles.addPanel}>
          <div className={styles.addPanelIconWrapper}>
            <div className={styles.addPanelIcon}>
              <SimCardIcon />
            </div>
          </div>
          <div className={styles.addPanelChoose}>
            <span className={styles.addPanelTitle}>{toggleText}</span>
            <div className={styles.addPanelCheck}>
              <span className={styles.addPanelText}>No</span>
              <ToggleButton checkedState={isActive} onClick={onSwitchToggle} >
                Choose
              </ToggleButton>
              <span className={styles.addPanelText}>Yes</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

registerComponent(ComponentNames.SKM_TriplePlayToggle, TriplePlayToggle);
