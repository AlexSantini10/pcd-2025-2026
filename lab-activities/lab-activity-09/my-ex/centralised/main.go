package main

import "fmt"

// Messaggio inviato da un processo al coordinatore con il suo valore locale.
type Submission struct {
	ProcessID int
	Value     int
}

// Risultato globale (min/max) calcolato dal coordinatore.
type GlobalRange struct {
	Min int
	Max int
}

// Risultato finale da stampare per ogni processo.
type Result struct {
	ProcessID int
	Value     int
	Min       int
	Max       int
}

func coordinator(processCount int, submissions chan Submission, replies []chan GlobalRange) {
	// Leggo il primo valore per inizializzare min e max.
	firstSubmission := <-submissions
	globalMin := firstSubmission.Value
	globalMax := firstSubmission.Value

	// Raccolgo gli altri valori e aggiorno gli estremi.
	for i := 1; i < processCount; i++ {
		submission := <-submissions
		if submission.Value < globalMin {
			globalMin = submission.Value
		}
		if submission.Value > globalMax {
			globalMax = submission.Value
		}
	}

	// Invio lo stesso risultato a tutti i processi.
	globalRange := GlobalRange{Min: globalMin, Max: globalMax}
	for i := 0; i < processCount; i++ {
		replies[i] <- globalRange
	}
}

func process(processID int, localValue int, submissions chan Submission, reply chan GlobalRange, done chan Result) {
	// 1. Invio il mio valore al coordinatore.
	submissions <- Submission{ProcessID: processID, Value: localValue}
	// 2. Ricevo il min/max globale.
	globalRange := <-reply
	// 3. Notifico il main.
	done <- Result{
		ProcessID: processID,
		Value:     localValue,
		Min:       globalRange.Min,
		Max:       globalRange.Max,
	}
}

func main() {
	values := []int{8, -3, 12, 7, 0, 31, -9}
	processCount := len(values)

	submissions := make(chan Submission)
	done := make(chan Result, processCount)
	replies := make([]chan GlobalRange, processCount)

	for i := 0; i < processCount; i++ {
		replies[i] = make(chan GlobalRange)
	}

	go coordinator(processCount, submissions, replies)

	for processID, localValue := range values {
		go process(processID, localValue, submissions, replies[processID], done)
	}

	results := make([]Result, processCount)
	for i := 0; i < processCount; i++ {
		result := <-done
		results[result.ProcessID] = result
	}

	fmt.Println("Centralised strategy")
	for i := 0; i < processCount; i++ {
		result := results[i]
		fmt.Printf("P%d value=%d -> global(min=%d, max=%d)\n", result.ProcessID, result.Value, result.Min, result.Max)
	}
}
