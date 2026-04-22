package main

import (
	"fmt"
)

type Msg struct {
	x     int
	reply chan int
}

func clientA_body(ch chan Msg) {
	fmt.Println("[A] hello from client A")
	mychan := make(chan int)
	ch <- Msg{x: 1, reply: mychan}
	res := <-mychan
	fmt.Printf("[A] res: %d \n", res)
}

func clientB_body(ch chan Msg) {
	fmt.Println("[B] hello from client B")
	mychan := make(chan int)
	ch <- Msg{x: 2, reply: mychan}
	res := <-mychan
	fmt.Printf("[B] res: %d \n", res)
}

func server_body(ch chan Msg) {
	fmt.Println("[server] hello from agent C")
	for {
		msg := <-ch
		fmt.Printf("[server] received %d ", msg.x)
		msg.reply <- msg.x * 2
	}
}

func main() {

	c := make(chan Msg)

	go server_body(c)
	go clientA_body(c)
	go clientB_body(c)

	for {
	}
}
