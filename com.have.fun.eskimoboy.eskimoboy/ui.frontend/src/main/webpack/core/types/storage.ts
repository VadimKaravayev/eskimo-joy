import {
  Dictionary
} from './dictionary';

export type Storage<Types, DictionaryDataType> = {
    [key in keyof Types | string]: Dictionary<DictionaryDataType>;
};
