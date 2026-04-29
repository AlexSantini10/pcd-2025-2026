/*
 *
 * Exchanging values problem (module-3.1)
 *
 * Centralized solution.
 *
 */
package main

import (
	"fmt"
	"math/rand"
)

type MinMaxMsg struct {
	min int
	max int
}

func Peer(id int, coord_ch chan int, my_ch chan MinMaxMsg) {
	v := rand.Intn(100)
	fmt.Printf("[Peer %d] my number is %d \n", id, v)
	coord_ch <- v
	m := <-my_ch
	fmt.Printf("[Peer %d] Max is %d and min is %d \n", id, m.max, m.min)
}

func Coord(n_peers int, coord_ch chan int, channels []chan MinMaxMsg) {
	max := -1
	min := 101
	for i := 0; i < n_peers; i++ {
		val := <-coord_ch
		fmt.Printf("[Coord] received %d \n", val)
		if val < min {
			min = val
		} else if val > max {
			max = val
		}
	}
	fmt.Printf("[Coord] Max is %d and min is %d \n", max, min)
	for i := 0; i < n_peers; i++ {
		channels[i] <- MinMaxMsg{min: min, max: max}
	}
}

func main() {
	fmt.Println("Booted.")

	n_peers := 10

	coord_ch := make(chan int)
	channels := make([]chan MinMaxMsg, n_peers)
	for i := 0; i < n_peers; i++ {
		channels[i] = make(chan MinMaxMsg)
	}

	go Coord(n_peers, coord_ch, channels)

	for i := 0; i < n_peers; i++ {
		go Peer(i, coord_ch, channels[i])
	}

	for {
	}
}
