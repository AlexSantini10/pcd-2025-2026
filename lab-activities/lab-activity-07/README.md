PCD a.y. 2025-2026 - ISI LM UNIBO - Cesena Campus

# Lab Activity #07 - 20260330

v1.0.0-20260330
 
### From Threads to Tasks (part of Lab Activity #06)

- Lab Activity #06 recall

### Virtual Threads (part of Lab Activity #06)

- **[Lab Notes] Virtual Threads**
   - Examples – `pcd.lab06.virtual_threads`

### Asynchronous Programming in JavaScript (First part)

- Discussing the examples in module-2.1 using a Playground
- Introducing promises (module-2.1)

### Asynchronous Programming in Java using Vert.x

- [Vert.x Framework](http://vertx.io/)
- Key points:
  - Reactor and Multi-Reactor, The Golden Rule - Don’t Block the Event Loop
  - "Verticle" abstraction => event loop
    - multiple verticles => multiple independent event loops 
  - Async programming style
    - based on (async) "future" + promise
    - future composition and coordination
  - How to run blocking code or long-term computation (`executeBlocking` mechanism)
  - Verticles asynchronous communication using the Event Bus 
  - Libraries. Among the others:
    - file systems, network (e.g. http) 
- Basic examples in `pcd.lab07.vertx`
    - Simple examples (through steps) showing some core API at work  
