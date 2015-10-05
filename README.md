# PhotoLocator
Android application for clients of PhotoLocator

This application just an example of using some Android features. 
In this application I tried to show how work with such part of Android SDK like:
- RestFULL client. Using Retrofit
- Take a picture from Android Camera (version 1)
- Use SQLite local database. Create table, data manipulations.
- Using BroadcastReciever
- Using AlarmManager to send data in background to server.
- Using LocationManager to obtain current location.
- Create a SwipeViewGrid with a custom row element. Handle deleteting operation in swipe mode.

PhotoLocator allow to take pictures, labeled with text comment, bind it with GEO location, and store in local database. 
From time-to time, program will try to send them to some host on OpenShift.
When its done, they going to appear on the map page -
http://test3-inhouseapp.rhcloud.com/web/map

Thank you.
