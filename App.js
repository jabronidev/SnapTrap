import React, { useEffect } from 'react';
import { ApplicationProvider, IconRegistry } from '@ui-kitten/components';
import * as eva from '@eva-design/eva';
import { EvaIconsPack } from '@ui-kitten/eva-icons';
import { Header } from './components/Header.js';
import { TabNavigator } from './components/BottomNav.js';
import { default as theme } from './custom-theme.json';
import { NavigationContainer } from '@react-navigation/native';
import DefaultPreference from 'react-native-default-preference';
import { Platform } from 'react-native';
import { NativeModules } from 'react-native';
const { SnapSettings } = NativeModules;

export default () => {
	if (Platform.OS === 'android') DefaultPreference.setName('SnapTrapStorage');

	useEffect(async() => {
		await SnapSettings.exportVerifySettingsFile();
	})

	return (
		<>
			<IconRegistry icons={EvaIconsPack} />
			<ApplicationProvider {...eva} theme={{ ...eva.light, ...theme }} style={{ backgroundColor: '#FBFBFB' }}>
				<Header />
				<NavigationContainer>
					<TabNavigator />
				</NavigationContainer>
			</ApplicationProvider>
		</>
	)
};