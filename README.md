== Application spins up 2 thread pools
1) ScheduledExecutorThreadPool that checks bus schedules every ### minutes (configurable).
    * This may not be a good idea, the ScheduledExecutorThreadPool will sill launch new threads to do parsing if the older thread is still active/outsanding
    * I could get around this by limiting the pool size to one and adding a queue which does not expire
    * Or I could have the precondition to any work being done checking to make sure if the past thread succeeded (somehow).  This sounds like it may lead to a dirty implementation.
2) BoundedCachedThreadPool which runs threads responsible for:
    * Posting debug messages
    * Transforming the bus arrival message into the external Notification type and posting that message
    * GUI thread

I believe I can wire this together by having that scheduled task post its message which is read by
an intermediary class.


== When the bus schedule checking thread wakes up.  It will:
1) Grab a list of TravelWindows from an embedded SQLite database or JSON file
2) Filter out the TravelWindows which are not activate at this time
3) From the remaining TravelWindows, gather all RouteAndStopToMonitor objects
4) For each RouteAndStopToMonitor, get the arrival times of each bus
5) Create a BusArrivalMessages for each arrival/departure time
6) Post BusArrivalMessages to the event bus

== The NotificationFactory threads will:
1) Listen to BusArrivalMessages and when it finds one
2) Format the external Notification using the info from the message
3) Post the Notification

== Ideas for the future:
1) Bake in logic to determine how long it'll take the user to walk from their current location.
    * https://stackoverflow.com/questions/22032062/how-to-get-my-current-location-on-my-laptop-java-program
    * https://dev.maxmind.com/geoip/legacy/geolite/
2) Debug mode that pushes error/debugging notifications
3) Make a GUI to input starting locations, times, and stops/routes
4) Do some research and see if I can flatten those thread pools.  Maybe create a thread which simply sleeps for the sleep time and posts a "wake up and do work" message

== Other notes:
1) Why did I make the MessageFactoryTask a task?  Did I do that because the Retryer wrapper for the ApiInterface requires a sucess callback task or something? (that's definitely why)
2) Retryer documentation: https://github.com/rholder/guava-retrying

== Dumb ideas:
1) This should've been a Ruby program.  Rewrite in Ruby