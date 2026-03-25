PCD a.y. 2025-2026 - ISI LM UNIBO - Cesena Campus

# Lab Activity #05 - 20260316

v1.0.0-20260309


### How to Implement Monitors in Java

- [Lab Notes] Implementing Monitors in Java
- Testing raw behavior of `wait`, `notify`
  - `pcd.lab05.TestRawWaitNotify` 
  - `pcd.lab05.TestIllegalMonitorStateEx`
  - `pcd.lab05.TestInterruptedException`
- Implementing a monitor - first approach, using raw support
  - `pcd.lab05.monitors.SynchCell`, 
- Implementing a monitor - second approach, using lib support
  - `pcd.lab05.monitors.SynchCell2`   
- More complex example: Bounded-Buffer implementation example in Producers-Consumers  
  - `pcd.lab05.monitors.BoundedBufferImplRaw`, `pcd.lab05.monitors.BoundedBufferImplWithLib`
- Work-in-Lab  
  - Implementing a barrier monitor - `pcd.lab05.monitors.ex_barrier`  
  - Implementing latch monitor - `pcd.lab05.monitors.ex_latch`  
- Testing signalling discipline semantics in Java
  - the S&C signalling discipline effects - different behaviours 
     - `pcd.lab05.monitors.TestSemantics1` (raw monitor impl)
         - Thread #1 is the first to enter the monitor
         - Thread #2 and Thread #3 arrive after Thread #1 is already inside
         - Thread #3 requests to enter after Thread #2
         - Thread #1 executes a wait inside the monitor before Thread #3 requests to enter
     - `pcd.lab05.monitors.TestSemantics2` (raw monitor impl, different timings)
         - Like previous case, but Thread #3 requests to enter before Thread #1 executes the wait, so it is already in the entry set when Thread #1 executes the wait   
     - `pcd.lab05.monitors.TestSemantics3`  
         - monitor impl. Based on Locks+Cond

### Assignment #01 - First announcement and presentation

- [Description](https://github.com/pslab-unibo/pcd-2025-2026/tree/master/assignments/assignment-01)

 