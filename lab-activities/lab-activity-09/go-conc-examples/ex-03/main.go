package main

import (
	"fmt"
)

type Msg struct {
	x,y int
	reply chan int
}

func client_body(xv, yv int, ch chan Msg) {
	fmt.Println("hello from client")
    mychan := make(chan int)
	ch <- Msg{x: xv, y: yv, reply: mychan}
    res := <- mychan	
	fmt.Printf("res: %d \n", res)    
}


func server_body(ch1 chan Msg, ch2 chan Msg) {
	fmt.Println("hello from agent C")
	for {
		select {
		case msg := <- ch1:
   			fmt.Printf("[add] received %d %d \n", msg.x, msg.y)
    		msg.reply <- msg.x + msg.y
		case msg := <- ch2:
   			fmt.Printf("[mul] received %d %d \n", msg.x, msg.y)
    		msg.reply <- msg.x * msg.y
    	}
    }
}

func main() {

	c_mul := make(chan Msg)
	c_add := make(chan Msg)

	go server_body(c_add, c_mul)
	go client_body(2, 3, c_add)
	go client_body(2, 3, c_mul)

	for {}
}
