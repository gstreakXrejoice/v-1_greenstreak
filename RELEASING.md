Releasing
========

 1. Make a merge from last branch release to branch master and branch dev.
 2. Create a new branch (release/version name) from branch dev.
 3. Inside the new branch.
    * 3.1 Fix all bugs from this release version.
    * 3.2 Update the version name and build number.
        * For android:
            * a. Update the version name and version code for android inside the app folder go to ./android/app/build.gradle and change it.
            * b. Sign the app with the new version in Android using the android key availible in this link [android sign](https://github.com/greenstreak/mobile-app-android-certificates.git).
        * For ios:
            * a. Update the version name and version code for ios inside xcode go to .xcodeproj and change it.
            * b. In ios go to terminal and write fastlane release.
 4. Make a merge from new release to branch dev.
 5. Make a merge from dev to branch master.
 6. Checkout branch master and make a tag for the new version.