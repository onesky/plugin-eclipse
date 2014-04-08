Eclipse Plugin for OneSky
=========================
OnySky Sync allows you to send file to OneSky platform for translation and download translated files back into your project. Currently, only **Android Project** is supported.

Features
========
* Send Android Project base language file to OneSky Platform
* Sync translated language files back into project.

Installing this Plugin
========================

Via Eclipse Marketplace
-------------------------
[![Install Button](http://marketplace.eclipse.org/sites/all/modules/custom/marketplace/images/installbutton.png)](http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=1605645)

You can install this plugin by dragging the install button above into your Eclipse Workspace or search it through in Eclipse Marketplace (navigate to /Help/Eclipse Marketplace) 

Via Install New Software
--------------------------
You can install this plugin by manually adding the update site.

1. Navigate to /Help/Install New Software
2. Press **Add...**
3. Enter the name **OneSky Sync**
4. Enter the Location: https://s3.amazonaws.com/oneskyapp.plugin/eclipse
5. Press **OK**
6. Tick **OneSky Sync**
7. Press **Next** and follow instructions

Setup Project
==============
1. From your project properties, navigate to **OneSky Sync**. 
2. Enter your API **Public Key** & **Secret Key**. You can get those keys from your site settings.
3. Either enter your project ID or Press the *Browse* button to select project.
4. Press **OK**.  

![Project Properties](/md_imgs/project_properties.png)

Android Project
----------------
You can send or sync language files from project context menu.

![Project Context Menu](/md_imgs/project_context_menu.png)

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
4. Press **Replace**.


