# Documentation

All documentation can be found in the [wiki](https://git.gbhapps.com/cloverleaf/greenstreak/mobile-app/wikis/home)

# Installation guide

## Requisites
* Fastlane
* Ruby
* play-store-credentials.json (1Password)
* AuthKey_X849CWY799.p8 (1Password)

## Automatic

```sh
yarn
yarn start
```

## Manual

### Install dependencies

```sh
yarn
```

#Firebase distribution

to upload to firebase we use fastlane
just run
fastlane distribute.

## Building

### parameters

* env
  
  example:

  > fastlane build_ios env:"stage"

  values:

  * dev
  * qa
  * stage

### iOS

> fastlane build_ios env:"$YOUR_ENV"

### Android

> fastlane build_android env:"$YOUR_ENV"

***

## Distribution

### Android

> fastlane distribute_firebase_android env:"$YOUR_ENV"

> fastlane distribute_to_playstore env:"$YOUR_ENV"

### iOS

> fastlane distribute_firebase_ios env:"$YOUR_ENV"

> fastlane distribute_to_testflight env:"$YOUR_ENV"