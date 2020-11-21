import {InclusionBlock} from "./InclusionBlock";

export type Tab = {
  tabTitle: string;
  tabAnalyticsName?: string;
  blocks: Array<InclusionBlock>;
};
