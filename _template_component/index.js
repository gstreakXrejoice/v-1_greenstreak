// External
import React, { PureComponent } from 'react';
import {
  View,
  Text
} from 'react-native';

// Internal
import StyleSheet from './stylesheet';
import Props from './props';

/**
 * The template component. All other components are generated using this one.
 * This component is not meant to be used as is
 * @type {Object}
 */
class Template extends PureComponent {
  /**
  * The main render function. Part of the React Native lifecycle methods
  * @return {ReactElement} JSX component
  */
  render() {
    return (
      <View style={ StyleSheet.container }>
        <Text>
          Template Component
        </Text>
      </View>
    );
  }
}

Template.propTypes = Props.propTypes;
Template.defaultProps = Props.defaultProps;

export default Template;
