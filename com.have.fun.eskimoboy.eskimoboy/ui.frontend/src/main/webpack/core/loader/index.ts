import React, {
  FunctionComponent,
} from 'react';
import ReactDOM from 'react-dom';

import {
  ICCI,
} from '../icci';
import {
  RegistryObject,
  RegistryTypes,
} from '../registry';

interface SubscribeComponentProps {
    componentName: string;
    children: RegistryObject;
}

class SubscribeComponent extends React.Component {
    private subscriptionToken: string;

    constructor(props: SubscribeComponentProps) {
      super(props);

      this.state = {};

      this.subscriptionToken = ICCI.subscribe(props.componentName, (receiver: string, payload: any) => {
        this.setState(payload);
      });
    }

    public componentWillUnmount() {
      ICCI.unsubscribe(this.subscriptionToken);
    }

    public render() {
      return React.createElement(this.props.children as FunctionComponent<any>, {
        ...this.props,
        eventData: this.state,
      });
    }
}

export const processLoading = () => {
  window.document.addEventListener('DOMContentLoaded', () => {
    processServices();
    processComponents();
  });
};

const processServices = () => {
  const serviceElements = Array.from(document.getElementsByClassName('aem-react-service'));

  serviceElements.forEach((el) => {
    try {
      const serviceConfig = JSON.parse(el.getAttribute('data-config'));
      const serviceName = el.getAttribute('data-service-name').toString();

      window.Registry.get(RegistryTypes.service, serviceName)
        .createInstance(serviceConfig);
    } catch (e) {
      console.log(e);
    }
  });
};

const processComponents = () => {
  const componentElements = Array.from(document.getElementsByClassName('aem-react-component'));

  componentElements.forEach((el) => {
    const content = el.getAttribute('data-config').replace(/\n/g, '');
            const componentConfig = JSON.parse(content);
    const componentName = el.getAttribute('data-component-name').toString();
    const componentSource = window.Registry.get(RegistryTypes.component, componentName);
    const publisher = ICCI.getPublisher(componentName);
    const subscribeComponent = React.createElement(SubscribeComponent, {
      componentName,
      ...componentConfig,
      publisher,
      children: componentSource,
    });
    ReactDOM.render(subscribeComponent, el);
  });
};
