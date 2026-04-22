package main

import (
	"fmt"
)

func agentA_body(ch chan int) {
	fmt.Println("hello from agent A")
	ch <- 1
}

func agentB_body(ch chan int) {
	fmt.Println("hello from agent B")
	ch <- 2
}

func main() {

	c := make(chan int)

	go agentA_body(c)
	go agentB_body(c)

	a := <-c
	b := <-c

	fmt.Printf("Received %d and %d\n", a, b)
}
