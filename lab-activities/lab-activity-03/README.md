PCD a.y. 2025-2026 - ISI LM UNIBO - Cesena Campus

# Lab Activity #03 - 20260302

### Implementing Critical Sections in Java

- Using `synchronized` blocks: `pcd.lab03.cs_raw`
- Using locks: `pcd.lab03.cs_withlocks`
    - `Lock` interface and `ReentrantLock` class, part of Java lib for concurrency

### Thread Safety in Java 

- [Lab Notes] Thread Safety
- **Work-in-Lab #01** -  Thread safety - lost update
	- Check lost update problem in `pcd.lab03.lost_updates`
	- Implement a solution 
- **Work-in-Lab #02** -  Thread safety - check and act
	- Check check & act problem in `pcd.lab03.check_act`
	- Implement a solution

### Model Checking with Java Path Finder (JPF) - Getting Started

- [About JPF](https://github.com/javapathfinder/jpf-core)
	- [Main docs](https://github.com/javapathfinder/jpf-core/wiki)
	- [NASA web site](https://ti.arc.nasa.gov/tech/rse/vandv/jpf/)
	- [Old JPF sourceforge web site](http://javapathfinder.sourceforge.net/)
- JPF Design and Details
	- [Relationship between JPF and JVM](https://github.com/javapathfinder/jpf-core/wiki/Model-Java-Interface)
	- [Understanding JPF output](https://github.com/javapathfinder/jpf-core/wiki/Understanding-JPF-output)
	- [Top-level design](https://github.com/javapathfinder/jpf-core/wiki/Search-Strategies)
	- [A full perspective about using JPF](https://github.com/javapathfinder/jpf-core/wiki/Different-applications-of-JPF)

- [Installing/Configuring JPF](https://github.com/javapathfinder/jpf-core/wiki/How-to-install-JPF) 
  - Using Docker - Steps:
    1) Open a terminal, choose a directory where work with JPF and clone the JPF repo:

       `git clone https://github.com/javapathfinder/jpf-core.git`
    2) Change directory to `jpf-core`

    3) Build the image:

       `docker-compose build`

    4) Start a container, mounting also a local directory to be used to host the Java projects to be model-checked. In the following the directory is mounted as `/pcd-jpf` inside the container:

       `docker-compose run --rm -v <your local directory>:/pcd-jpf jpf-dev` 

       For instance: mounting the `/pcd-jpf` directory included in the repo (in `lab-activity-03`):

       `docker-compose run --rm -v <path to PCD repo>/pcd-2025-2026/lab-activities/lab-activity-03/pcd-jpf:/pcd-jpf jpf-dev` 

    5) Build JPF

        `./gradlew clean build`

       - On Windows systems you may get an error such as:
         
         `/bin/sh^M: bad interpreter`
          
         This is due to unwanted (not visible) line ending characters (`\r\n`) that could have been added to the file name `gradlew`. If a file with Windows line endings is used on Linux/macOS (inside the container), it causes the error mentioned above. 

           To remove the unwanted characters, just type (from inside the container):

         `sed -i 's/\r$//' gradlew`  
         `chmod +x gradlew`
 
          - `sed` is a command-line tool used to search, replace, insert, and delete text in files. Int his case it substitutes sequence of characters `\r\n` (represented by the pattern `\r$` with empty (so it removes the characters). 
          - `chmode +x` is to make the script file executable.

    6) To check if JPF is working, we launch one of the example available with the JPF distribution, the Racer (a Java program with a race condition):

        `java -jar build/RunJPF.jar src/examples/Racer.jpf`
      

 