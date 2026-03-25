PCD a.y. 2025-2026 - ISI LM UNIBO - Cesena Campus

# Lab Activity #06 - 20260320

v1.0.0-20260320

### Multi-threaded programs with GUI

- **[Lab Notes] GUI Frameworks and Concurrency**
  - chrono case study
    - bugged version: not responsive and with races: `pcd.lab06.chrono_mvc.not_reactive_plus_races`
    - correct version: reactive and with no races: `pcd.lab06.chrono_mvc.reactive_no_races`

### Further Insights for Assignment #01

- [Assignment-01](https://github.com/pslab-unibo/pcd-2025-2026/tree/master/assignments/assignment-01)
  - `Sketch-02`example - applying producer/consumer architecture for handling asynchronous input
 
### From Threads to Tasks

- **[Lab Notels] Task-Oriented Programming in Java**
  - `Executor` Framework - Examples
    - Matmul example – `pcd.lab06.executors.matmul`
  	- Quadrature problem example, with different strategies to collect results
  	  - collecting results with monitor + shutdown mechanism to synch – `pcd.lab06.executors.quad1_basic`
  	  - collecting results only with monitor - `pcd.lab06.executors.quad2_withsynch`
  	  - without monitors, using futures - `pcd.lab06.executors.quad2_withfuture`
  - Beware of deadlocks using Executors – example: `pcd.lab06.executors.deadlock`
    - Why is there a deadlock?
  -  Improving structure in task-oriented programming  
      -  `ForkJoin` executor   
         - map-reduce example: `pcd.lab06.executors.fj`   
      - Structured Concurrency (JEP 437)    

### Virtual Threads 

- **[Lab Notes] Virtual Threads**
   - Examples – `pcd.lab06.virtual_threads`


