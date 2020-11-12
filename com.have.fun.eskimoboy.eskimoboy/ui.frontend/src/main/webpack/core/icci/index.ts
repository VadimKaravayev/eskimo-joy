import * as PubSub from 'pubsub-js';

export interface IPropsWithSender {
    sender: string;
}

export class ICCI {
  public static getSubscription(channel: string): (callback: () => void) => void {
    return (callback: () => void) => {
      ICCI.subscribe(channel, callback);
    };
  }

  public static getPublisher(sender: string): (channel: string, payload: object) => void {
    return (channel: string, payload: object) => {
      ICCI.publish(channel, {
        ...payload,
        sender,
      });
    };
  }

  public static publish(channel: string, payload: IPropsWithSender): void {
    PubSub.publishSync(channel, payload);
  }

  public static subscribe(channel: string, callback: (receiver: string, payload: any) => void): string {
    return PubSub.subscribe(channel, callback);
  }

  public static unsubscribe(token: string) {
    return PubSub.unsubscribe(token);
  }
}
