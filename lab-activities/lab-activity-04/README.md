PCD a.y. 2025-2026 - ISI LM UNIBO - Cesena Campus

# Lab Activity #04 - 20260309

v1.0.0-20260309

### Semaphores in Java

- [Lab Notes] Thread Coordination in Java - Library Support
	- `Semaphore` class 
		- Implementing a critical section with semaphores: `pcd.lab04.cs_withsem`

- **Work-in-Lab** -  Thread synchronisation with semaphores
	- Preparation
		- Consider the `pcd.lab04.ex01_syncwithsem.TestPingPong` program
			- `Pinger` and `Ponger` agents, concurrently printing ping and pong
	- Exercise  
		- Make `Pinger` and `Ponger` synchronised using semaphores, so that they alternate in printing ping and pong
		- What correctness properties? How to specify and check them with JPF?

### Thread Liveness & Deadlocks

- [Lab Notes] Thread Liveness
- The simplest deadlock in Java: `pcd.lab04.deadlock_simplest.TestDeadlockSimplest`
  - Analysis using VisualVM
- More complex example: AccountManager in pcd.lab04.liveness.accounts 
  - requirements
    - no race conditions in updating individual accounts
    - transfer between accounts should be atomic 
    - maximising concurrency
      - two transactions involving distinct couples of accounts should be served concurrentl
- Deadlocks when implementing MVC and Observer pattern
  - `pcd.lab04.deadlock_obs.TestObsPatternDeadlock`

### Using JPF

      
  - **Preparing the environment**:
    - Start the container mounting the [`pcd-jpf`]() directory included in the repo (in `lab-activity-04`):

   		`docker-compose run --rm -v <path to PCD repo>/pcd-2025-2026/lab-activities/lab-activity-04/pcd-jpf:/pcd-jpf jpf-dev` 
  	- Compile all Java sources:
    
    	`javac -d /pcd-jpf/target/classes -classpath /pcd-jpf/target/classes:/pcd-jpf/lib/jpf.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/*.java`
 
  		(the classpath must include also `jpf.jar` including the Verify API classes)

  - **Example #1** - model-checking sequential programs...
    - sequential program - `pcd.lab04.jpf.TestSequential`
      - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestSequential.jpf`
    - sequential program with input (using **Verify API**) - `pcd.lab04.jpf.TestSequentialWihtInput`
      - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestSequentialWithInput.jpf`
    - sequential program with random (using **Verify API**) - `pcd.lab04.jpf.TestSequentialWihtRand`
      - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestSequentialWithRand.jpf`
  - **Example #2** - model-checking simple concurrent programs 
    - simple concurrent program - `pcd.lab04.jpf.TestScenarios`
      - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestScenarios.jpf`
      - look at the number of states and traces (scenarios)
    - defining atomic blocks (using **Verify API**)  
      - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestScenariosWithAtomicBlocks.jpf`
      - look at the number of states and traces
  - **Example #3a** - Finding lost updates races using precise race detector
    - `pcd.lab04.jpf.TestLostUpdate`
    - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestLostUpdate.jpf`
  - **Example #3b** - **TODO** (Work-in-Lab): Finding lost updates races - **using assert** 
    - `pcd.lab04.jpf.TestLostUpdateUsingAssert`
    - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestLostUpdateUsingAssert.jpf`
  - **Example #4** - Finding check-and-act races 
    - `pcd.lab04.jpf.TestCheckAct`
    - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestCheckAct.jpf`
  - **Example #5** - Detecting deadlocks
    - `pcd.lab04.jpf.TestDeadlock`
    - `java -jar build/RunJPF.jar /pcd-jpf/src/main/java/pcd/lab04/jpf/TestDeadlock.jpf`

### Tool: VisualVM profiler
 
- [VisualVM](https://visualvm.github.io/)
	- useful also to track thread behaviour/state (blocked, not blocked)


 