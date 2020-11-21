import React from 'react';
import classNames from "classnames";
import styles from '../styles.module.scss';
import {BlockItem} from "../types";

type InclusionsItemProps = {
  title: string;
  items: Array<BlockItem>;
  details: boolean;
};

export const InclusionsItem: React.FC<InclusionsItemProps> = ({
  title,
  items,
  details
}) => (
  <>
    <div className={styles.inclusionsItem}>
      <h3 className={styles.inclusionsTitle}>{title}</h3>
      <ul className={styles.itemList}>
        {items.map(item => (
          <li className={classNames(styles.itemListItem, {[styles.strikeOut]: item.strikeThrough})}
              aria-hidden={item.strikeThrough}
          >
            <div className={styles.itemIco}>
              {item.strikeThrough
                ? (<span className={styles.closeIcon} />)
                : (<span className={styles.checkmark} />)
              }
            </div>
            <div className={styles.itemText}>
              <h4 className={styles.inclusionsTitle}>{item.title}</h4>
              {details && <p className={styles.inclusionsDescriptions}>{item.description}</p>}
              {details && item.linkTitle && (
                <a className={styles.inclusionsLink}
                   href={item.linkPath}
                   data-analytics-link-type='link'
                   data-analytics-link={item.analyticsLinkName}
                >{item.linkTitle}</a>
              )}
            </div>
          </li>
        ))}
      </ul>
    </div>
  </>
);
