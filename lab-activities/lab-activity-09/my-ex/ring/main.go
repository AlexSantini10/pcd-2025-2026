package main

import "fmt"

const (
	// Primo giro: si calcola min/max globale.
	collectPhase = "collect"
	// Secondo giro: si distribuisce il risultato globale.
	sharePhase = "share"
)

// Token che circola nell'anello.
type Token struct {
	Phase          string
	Min            int
	Max            int
	StepsRemaining int
}

// Risultato finale da stampare per ogni processo.
type Result struct {
	ProcessID int
	Value     int
	Min       int
	Max       int
}

func ringProcess(
	processID int,
	localValue int,
	processCount int,
	incoming chan Token,
	outgoing chan Token,
	done chan Result,
	isStarter bool,
) {
	if isStarter {
		// Lo starter avvia il primo giro con il proprio valore.
		outgoing <- Token{
			Phase:          collectPhase,
			Min:            localValue,
			Max:            localValue,
			StepsRemaining: processCount - 1,
		}
	}

	for {
		token := <-incoming

		if token.Phase == collectPhase {
			if isStarter {
				// Il token e tornato allo starter: min/max globale pronto.
				done <- Result{ProcessID: processID, Value: localValue, Min: token.Min, Max: token.Max}
				outgoing <- Token{
					Phase:          sharePhase,
					Min:            token.Min,
					Max:            token.Max,
					StepsRemaining: processCount - 1,
				}
				continue
			}

			if localValue < token.Min {
				token.Min = localValue
			}
			if localValue > token.Max {
				token.Max = localValue
			}
			token.StepsRemaining--
			outgoing <- token
			continue
		}

		if token.Phase == sharePhase {
			if !isStarter {
				done <- Result{ProcessID: processID, Value: localValue, Min: token.Min, Max: token.Max}
			}

			if token.StepsRemaining == 0 {
				return
			}

			token.StepsRemaining--
			outgoing <- token
		}
	}
}

func main() {
	values := []int{8, -3, 12, 7, 0, 31, -9}
	processCount := len(values)

	if processCount == 1 {
		fmt.Println("Ring strategy")
		fmt.Printf("P0 value=%d -> global(min=%d, max=%d)\n", values[0], values[0], values[0])
		return
	}

	ringChannels := make([]chan Token, processCount)
	done := make(chan Result, processCount)

	for i := 0; i < processCount; i++ {
		ringChannels[i] = make(chan Token, 1)
	}

	for processID, localValue := range values {
		nextProcessID := (processID + 1) % processCount
		go ringProcess(
			processID,
			localValue,
			processCount,
			ringChannels[processID],
			ringChannels[nextProcessID],
			done,
			processID == 0,
		)
	}

	results := make([]Result, processCount)
	for i := 0; i < processCount; i++ {
		result := <-done
		results[result.ProcessID] = result
	}

	fmt.Println("Ring strategy")
	for i := 0; i < processCount; i++ {
		result := results[i]
		fmt.Printf("P%d value=%d -> global(min=%d, max=%d)\n", result.ProcessID, result.Value, result.Min, result.Max)
	}
}
