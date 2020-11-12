export type Service = object;

export type Config = object;

export class ConfigurableService<C extends Config> implements Service {
  protected readonly config: C;

  constructor(config: C) {
    this.config = config;
  }
}
