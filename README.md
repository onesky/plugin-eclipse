Elipse plugin for OneSky
=========================
OnySky Sync allows you to send file to OneSky platform for translation and download translated files back into your project. Currently, only **Android Project** is supported.

Features
========
* Send Android Project base language file to OneSky Platform
* Sync translated language files back into project.

Setup
======
1. From your project properties, navigate to **OneSky Sync**. 
2. Enter your API *Public Key* & *Secret Key*. You can get those keys from your site settings.
3. Either enter your project ID or Press the *Browse* button.
4. Press *OK*  

Android Project
----------------
### Send Android base language file to OneSky
1. From project context menu, navigate to **OneSky Sync**.
2. Choose **Send to OneSky for Translation**. This plugin will send Project's */res/values/strings.xml* to OneSky.

### Sync All Languages
1. From project context menu, navigate to **OneSky Sync**.
2. Choose **Sync All Languages**. All available languages from your OneSky project will be downloaded.

### Sync Specific Languages
1. From project context menu, navigate to **OneSky Sync**.
2. Choose **Send to OneSky for Translation**. 
3. Select languages. Selected languages will be downloaded.

### Restore File
If you sync file by mistake. You can make use of eclipse feature *Local History*.

1. From file context menu, navigate to **Replace with**.
2. Choose **Local History**.
3. Select version from history.
