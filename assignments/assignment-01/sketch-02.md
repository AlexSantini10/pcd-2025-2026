PCD a.y. 2024-2025 - ISI LM UNIBO - Cesena Campus

# Sketch 02 - Notes

v1.0.0-20260320

This sketch (`pcd.sketch02`) shows an example of how to manage **asynchronous inputs** from the keyboard in a concurrent program with a GUI, applying the **MVC** architectural pattern.
- the Model (package `pcd.sketch02.model`) includes:
  - a passive `Counter` object, which is implemented as a monitor
    - a **Observer pattern** is adopted (following MVC) so that this object is a source of events that are observer by observer objects (also called listeners, in the Java world)
      - `CounterObserver` is the interface that must be implemented by observers
    - **ATTENTION POINT**: the pattern is implemented as in sequential programs, so that observers are notified through a synchronous method call. Being the source in this case a monitor, the call is done without releasing the lock on the monitor.  
  - an active component `AutonomousUpdater` that periodically updates the count, by performing an `inc`
- the View (package `pcd.sketch02.view`) includes a simple GUI, visualizing the current count value and its graphical representation (as a analog clock)
  - following the MVC pattern, the view is observer of update events notified by the model (count object in this case)
  - the view in this case plays also the role of input source for the controller in the MVC pattern
    - the `ViewFrame` uses the Java Swing API to listen key events, notifying them to the controller as commands   
- the Controller (package `pcd.sketch02.controller`) is realized by an active component `ActiveController`, which reacts to asynchronous input representing requests/commands from the user and executes them, acting on the counter
  - `Command` design pattern is adopted
    - `Cmd` is the command interface
    - `IncCmd` models a request to increment the counter, `ResetCmd` to reset the counter
  - a producer/consumer architecture is implemented
    - the active controller consumes input produced by the input components (the view in this case), by means of a bounded buffer


