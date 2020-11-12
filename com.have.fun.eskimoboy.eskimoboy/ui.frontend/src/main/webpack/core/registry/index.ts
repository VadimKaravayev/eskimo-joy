import { Storage } from '../types';
import { ServiceNames } from './serviceNames';
import {
  Service,
  Config,
  ServiceClass,
  ServiceProvider,
  ConfigurableServiceClass,
  ConfigurableServiceProvider,
} from './service';

export * from './componentNames';
export * from './serviceNames';
export {
  ConfigurableService,
} from './service';

export enum RegistryTypes {
  'component' = 'Component',
  'service' = 'Service',
}

export type RegistryObject = any;

export interface IRegistry {
  register(type: RegistryTypes, name: string, classOrServiceObject: RegistryObject): void;

  get(type: RegistryTypes, name: string): RegistryObject;
}

class Registry implements IRegistry {
  private storage: Storage<RegistryTypes, RegistryObject>;

  constructor() {
    this.storage = {
      [RegistryTypes.service]: {},
      [RegistryTypes.component]: {},
    };
  }

  public get(type: RegistryTypes, name: string): RegistryObject {
    const typeStorage = this.storage[type];
    if (!typeStorage || !typeStorage[name]) {
      throw new Error(`${type} with name ${name} not registered`);
    }

    return this.storage[type][name];
  }

  public register(type: RegistryTypes, name: string, classOrServiceObject: RegistryObject): void {
    if (this.storage[type][name]) {
      throw new Error(`${type} with name ${name} already registered`);
    }

    this.storage[type][name] = classOrServiceObject;
  }
}

export const registryPublicInterface = (() => {
  const registry = new Registry();

  return {
    register(type: RegistryTypes, name: string, classOrServiceObject: RegistryObject) {
      return registry.register(type, name, classOrServiceObject);
    },
    get(type: RegistryTypes, name: string): RegistryObject {
      return registry.get(type, name);
    },
  };
})();

declare global {
  interface Window {
    Registry: IRegistry;
  }
}

function getRegistryPublicInterface() {
  if (!window.Registry) {
    window.Registry = registryPublicInterface;
  }

  return window.Registry;
}

export function registerComponent(name: string, classOrServiceObject: RegistryObject): void {
  getRegistryPublicInterface().register(RegistryTypes.component, name, classOrServiceObject);
}

export function registerService(name: ServiceNames, serviceClass: ServiceClass): void {
  getRegistryPublicInterface()
    .register(RegistryTypes.service, name,
      new ServiceProvider(name, serviceClass));
}

export function registerConfigurableService<C extends Config>(
  name: ServiceNames, serviceClass: ConfigurableServiceClass<C>
): void {
  getRegistryPublicInterface()
    .register(RegistryTypes.service, name,
      new ConfigurableServiceProvider<C>(name, serviceClass));
}

export function getService<S extends Service>(name: ServiceNames): S {
  return getRegistryPublicInterface()
    .get(RegistryTypes.service, name)
    .getInstance();
}
