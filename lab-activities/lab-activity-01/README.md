PCD a.y. 2024-2025 - ISI LM UNIBO - Cesena Campus

# Lab Activity #01 - 20260216


### Multithreaded programming in Java - Basics 

- Java Thread Basic API overview 
	- How to define / create / spawn threads
	- A look at basic methods/mechanisms and Java System API  
	- Example: `pcd.lab01.hello`

### About Performance

- First look at performance 

	- how many threads?
	- A first tool for profiling concurrent programs: JConsole
	- how thread behaviour can affect CPU usage & reactivity
		- an example: `pcd.lab01.bballs` (bouncing balls)
		- Threads with non terminating behaviour (`BallAgent`)

### Work-in-Lab 

- Preparation
	- program `pcd.lab01.ex01` - it creates and sorts an array
- Exercise
	- Create a concurrent version exploiting 2 threads and measure the speedup
	- Generalise the solution to scale with the number of cores


