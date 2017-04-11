Feature: As a user, I want to be able to save and edit locations so that I can see rain information for those location.
----


**Scenario:** Launch without previously saving a location.

**GIVEN** I launch the app without previously saving a location (i.e. first launch)
**WHEN** I see the home screen
**THEN** I am presented with a prompt to search and save a location

--

**Scenario:** Launch with previously saved location.

**GIVEN** I have saved a location previously
**WHEN** I see the home screen
**THEN** I am presented with rain information for that location for today and tomorrow

--

**Scenario:** Change saved location.

**GIVEN** I have saved a location previously
**WHEN** I change my saved location
**THEN** I am presented with rain information for that new location for today and tomorrow

--

Feature: As a user, when it is about to rain, I want to be notified so that I am aware of rain conditions for my saved location.
----


Scenario: Receive a notification if it is about to rain.

GIVEN I have a saved location
WHEN it is likely to rain within the next hour
THEN I am presented with a notification

--

Feature: As a user, I want to be able to see if it will rain today or tomorrow so that I am away of my saved location’s weather conditions.
----

**Scenario:** Rain today and tomorrow

**GIVEN** it will rain today and tomorrow
**WHEN** I am on the home screen
**THEN** I am presented with rain information for today and tomorrow
**AND** temperature information for today and tomorrow

--

**Scenario:** Rain today only

**GIVEN** it will rain today only
**WHEN** I am on the home screen
**THEN** I am presented with rain information for today only
**AND** temperature information for today and tomorrow

--

**Scenario:** Rain tomorrow only

**GIVEN** it will rain today and tomorrow
**WHEN** I am on the home screen
**THEN** I am presented with rain information for tomorrow only
**AND** temperature information for today and tomorrow

--

**Scenario:** No rain today or tomorrow

**GIVEN** it will rain today and tomorrow
**WHEN** I am on the home screen
**THEN** I am presented with a ‘no rain’ state
**AND** temperature information for today and tomorrow


