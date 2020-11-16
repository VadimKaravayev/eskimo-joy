import React from 'react';
import classNames from 'classnames';

import styles from './styles.module.scss';

export type ArticleItemType = {
  title: string;
  text: string;
  altText?: string;
  image?: string;
  linkTitle?: string;
  linkPath?: string;
  analyticsLinkName?: string;
};

type ArticleListProps = {
  articles: ArticleItemType[];
  isArticleSmallSize?: boolean | false;
};

export const ArticleList: React.FC<ArticleListProps> = ({
  articles,
  isArticleSmallSize
}) => (
  <ul className={classNames(styles.articleList, { [styles.articleListSmall]: isArticleSmallSize })}>
    {
      articles && articles.map(({
        title,
        text,
        image,
        altText,
        linkTitle,
        linkPath,
        analyticsLinkName
      }, i) => (
        <li className={styles.articleListItem} key={i}>
          <div className={styles.itemBox}>
            {
              image && (
                <div className={styles.imageBox}>
                  <img src={image} alt={altText} />
                </div>
              )
            }
            <div className={styles.content}>
              <h2 className={styles.title}>{title}</h2>
              <div dangerouslySetInnerHTML={{ __html: text }}/>
              {
                linkPath && linkTitle && (
                  <div className={styles.buttonBox}>
                    <a href={linkPath} data-analytics-link={analyticsLinkName}>
                      {linkTitle}
                      <div className={styles.chevron} />
                    </a>
                  </div>
                )
              }
            </div>
          </div>
        </li>
      ))
    }
  </ul>
);
