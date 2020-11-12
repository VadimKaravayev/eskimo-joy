import { Config, Service, ConfigurableService } from './service';

export type ServiceClass = new () => Service;

export type ConfigurableServiceClass<C extends Config> = new (config: Config) => ConfigurableService<C>;

abstract class GenericServiceProvider<Class extends ServiceClass | ConfigurableServiceClass<Config>, Instance extends Service> {
    protected readonly name: string;

    protected readonly class: Class;

    protected instance: Instance;

    constructor(name: string, _class: Class) {
      this.name = name;
      this.class = _class;
    }

    abstract getInstance(): Instance;

    abstract createInstance(param?: Config): void;
}

export class ServiceProvider extends GenericServiceProvider<ServiceClass, Service> {
  getInstance(): Service {
    if (!this.instance) {
      this.createInstance();
    }
    return this.instance;
  }

  createInstance(): void {
    this.instance = new this.class();
  }
}

export class ConfigurableServiceProvider<C extends Config> extends GenericServiceProvider<ConfigurableServiceClass<C>, ConfigurableService<C>> {
  getInstance(): ConfigurableService<C> {
    if (!this.instance) {
      throw new Error(`Instance for ${this.name} service was not configured.`);
    }
    return this.instance;
  }

  createInstance(config: C): void {
    this.instance = new this.class(config);
  }
}
