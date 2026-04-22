package main

import "fmt"

// Messaggio con il valore locale inviato da un processo agli altri.
type ValueMessage struct {
	FromProcessID int
	Value         int
}

// Risultato finale da stampare per ogni processo.
type Result struct {
	ProcessID int
	Value     int
	Min       int
	Max       int
}

func process(processID int, localValue int, inbox chan ValueMessage, allInboxes []chan ValueMessage, processCount int, done chan Result) {
	// Fase 1: mando il mio valore a tutti gli altri processi.
	for peerID := 0; peerID < processCount; peerID++ {
		if peerID == processID {
			continue
		}
		allInboxes[peerID] <- ValueMessage{FromProcessID: processID, Value: localValue}
	}

	// Fase 2: ricevo i valori dagli altri e aggiorno min/max locale.
	localMin := localValue
	localMax := localValue

	for i := 0; i < processCount-1; i++ {
		message := <-inbox
		if message.Value < localMin {
			localMin = message.Value
		}
		if message.Value > localMax {
			localMax = message.Value
		}
	}

	done <- Result{
		ProcessID: processID,
		Value:     localValue,
		Min:       localMin,
		Max:       localMax,
	}
}

func main() {
	values := []int{8, -3, 12, 7, 0, 31, -9}
	processCount := len(values)

	allInboxes := make([]chan ValueMessage, processCount)
	done := make(chan Result, processCount)

	for i := 0; i < processCount; i++ {
		// Ogni inbox puo contenere tutti i messaggi attesi dagli altri processi.
		allInboxes[i] = make(chan ValueMessage, processCount-1)
	}

	for processID, localValue := range values {
		go process(processID, localValue, allInboxes[processID], allInboxes, processCount, done)
	}

	results := make([]Result, processCount)
	for i := 0; i < processCount; i++ {
		result := <-done
		results[result.ProcessID] = result
	}

	fmt.Println("Symmetric strategy")
	for i := 0; i < processCount; i++ {
		result := results[i]
		fmt.Printf("P%d value=%d -> global(min=%d, max=%d)\n", result.ProcessID, result.Value, result.Min, result.Max)
	}
}
