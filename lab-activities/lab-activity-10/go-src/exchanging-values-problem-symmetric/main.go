/*
 *
 * Exchanging values problem (module-3.1)
 *
 * Symmetric solution.
 *
 */
package main

import (
	"fmt"
	"math/rand"
)

func Peer(id int, channels []chan int) {
	v := rand.Intn(100)
	fmt.Printf("[Peer %d] my number is %d \n", id, v)

	for i := 0; i < len(channels); i++ {
		if i != id {
			fmt.Printf("[Peer %d] sending to channel %d \n", id, i)
			channels[i] <- v
		}
	}

	my_peers := len(channels) - 1
	min := v
	max := v
	for i := 0; i < my_peers; i++ {
		val := <-channels[id]
		fmt.Printf("[Peer %d] Received %d\n", id, val)
		if val < min {
			min = val
		}
		if val > max {
			max = val
		}
	}
	fmt.Printf("[Peer %d] Max is %d and min is %d \n", id, max, min)
}

func main() {
	fmt.Println("Booted.")

	n_peers := 10
	channels := make([]chan int, n_peers)
	for i := 0; i < n_peers; i++ {
		channels[i] = make(chan int, n_peers)
	}

	for i := 0; i < n_peers; i++ {
		go Peer(i, channels)
	}

	for {
	}
}
