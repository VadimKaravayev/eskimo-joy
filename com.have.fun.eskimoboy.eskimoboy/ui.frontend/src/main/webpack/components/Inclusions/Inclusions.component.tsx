import React, {useState} from 'react';
import { registerComponent, ComponentNames } from '../../core/registry';
import { Container, Headline} from '../common/atoms';
import classNames from "classnames";
import styles from './styles.module.scss';
import {Tab} from "./types";
import {InclusionsItem} from "./components/InclusionsItems.component";


type InclusionsProps = {
  headline: string;
  tabLeft: Tab;
  tabRight: Tab;
};

const Inclusions: React.FC<InclusionsProps> = ({
  headline,
  tabLeft,
  tabRight
}) => {
  const [details, setDetails] = useState(false);
  const [activeLeftTab, setActiveLeftTab] = useState(true);
  return (
    <div className={classNames(styles.inclusionsWrapper, {[styles.detailsActive]: details})}>
      <Container>
        <Headline text={headline} cssClass={styles.inclusionsMainTitle} />
        <div className={styles.buttonsContainer}>
          <button className={`${activeLeftTab && styles.active}`}
                  onClick={ () => setActiveLeftTab(true)}
                  data-analytics-link={tabLeft.tabAnalyticsName}
          >{tabLeft.tabTitle}</button>
          <button className={`${!activeLeftTab && styles.active}`}
                  onClick={() => setActiveLeftTab(false)}
                  data-analytics-link-type='link'
                  data-analytics-link={tabRight.tabAnalyticsName}
          >{tabRight.tabTitle}</button>
        </div>
        <div className={styles.inclusions}>
          {activeLeftTab
            ? tabLeft.blocks.map(block => <InclusionsItem title={block.blockTitle} items={block.items} details={details} />)
            : tabRight.blocks.map(block => <InclusionsItem title={block.blockTitle} items={block.items} details={details} />)
          }
        </div>
        <div className={styles.detailsContainer}>
          <button className={classNames(styles.detailsButton, {[styles.detailsActive]: details})}
                  data-analytics-link-type='link'
                  data-analytics-link={details ? 'Hide details' : 'View details'}
                  onClick={() => setDetails(!details)}
          >{details ? 'Hide details' : 'View details'}</button>
        </div>
      </Container>
    </div>
  );
};

registerComponent(ComponentNames.SKM_Inclusions, Inclusions);
