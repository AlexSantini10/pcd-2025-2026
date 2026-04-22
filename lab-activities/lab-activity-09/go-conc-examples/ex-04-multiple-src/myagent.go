package main

import (
	"time"
)

type Msg struct {
	content  string
	senderId string
	time     time.Time
}

func MyAgent(ch chan Msg, id string) {
	msg := Msg{content: "Hello", senderId: id, time: time.Now()}
	ch <- msg
}

func spawnMyAgent(id string) chan Msg {
	ch := make(chan Msg)
	go MyAgent(ch, id)
	return ch
}
