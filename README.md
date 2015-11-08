![Carrot Logo](http://temp.boxedfolder.com/smartlogo_carrot.png =476x)

Carrot is the beacon management system for everyone. It's build on top of the popular Spring framework und utilizes AngularJS as a frontend component. 

The following technologies are in use:

+ Spring Boot
+ Spring Security
+ Spring Data / Hibernate
+ HSQLDB
+ Thymeleaf
+ Joda Time
+ Bootstrap
+ Jquery

# How to get started?

This repository consists of two separate projects. The folder 'core' contains the Spring Boot project and the folder 'client' includes the AngularJS client application. Dependencies are managed via Maven & Bower. You can run both _client_ and _core_ on your local machine, your own server hardware or deploy everything to the cloud (e.g. Heroku via Spring Cloud). Use the appropriate _application.yml_ file to configure your datasource.

_See the CarrotSDK repository for additional details on how to communicate with devices._

## Development

Run Spring Boot by running the command ´mvn spring-boot:run´ (_/core_). When your Spring Boot application is running, you can fire up the build-in frontend server by running ´grunt server´ (_/client_).

## Deployment

To integrate the client module into the core, run the grunt command ´grunt build´. All assets, html, js and css files are being copied to the resources directory inside your Spring boot project.

# Example

An instance of carrot is running on Heroku under http://carrotbms.heroku.com. Login with _admin_ (username) and _carrot_ (password).

# What's next?

The following features are still missing:

+ (Advanced) user management - Currently we have a single user with credentials stored inside the application settings file. This should be replaced with a user and role management in the future.

+ Support for binary files - Carrots current support for text and notifications should be extended to allow file attachments. The syncing process needs to be overhauled for that purpose.

# License

Copyright 2015 Heiko Dreyer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
