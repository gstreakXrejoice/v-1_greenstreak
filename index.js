/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import {App} from './app/index';
import {notificationManager} from './app/utils/notification-manager'


notificationManager.start();
App.initializeApp();