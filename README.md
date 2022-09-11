# SportsRec App
This is just a sample application used as a show case of my technical prowess.
SportsRec is used to store, edit and post sport performace records.

This README also contains my thoughts on different possible approaches and implementations of different features in this app.

## Overview
SportsRec uses standard MVVM architecture and Clean Architecture code structure, mainly separation of concerns, code base is divided into three different modules:

- app - module concerned with app configuration and views, it has access to both domain and data modules
- domain - module concerned with business logic, this module should always be independent of platform (no access to things like Context, ViewModel, etc), it also provides interfaces for data module
- data - module concerned with data storage and remote communication, it has access to domain module

There are multiple other approaches that could have been used (MVI is getting traction in recent months). Notably we could have separated app module into two different modules - app module for application configuration and Application itself and presentation for screens and ViewModels, that way, we could better separate module concerned with views from data module.

It would often make sense to use feature based approach - different feature modules in root of the project and presentation/domain/data layers in each feature module - but this sample app doesn't contain that many features for this approach to make sense.

## App module
App module contains app configuration, Hilt modules and screens and view models for different features.

Screens are implemented using Jetpack Compose.

## Domain module
Domain module contains business logic of the app and general functions and extensions. It also exposes interfaces for repositories and services that different modules can implement. Domain module should also be independent of platform (Android, iOS or anything else) so that this business logic can be used in multiplatform development.

## Data module
Data module contains data storage and remote communication logic. Local storage is implemented using Room SQLite database, remote communication uses Retrofit client.

Data module also contains authentication logic using FirebaseAuth. User is authenticated using his email and password.

## Authentication

## Records list

## Record detail

## Editing records

## Conclusion