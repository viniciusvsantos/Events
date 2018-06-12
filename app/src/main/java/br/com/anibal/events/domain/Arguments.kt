package br.com.anibal.events.domain

import android.os.Bundle

object Arguments {

    private val bundle = Bundle()
    private const val key_event = "event"
    private const val key_talk = "talk"

    private fun putEvent(event: Event) {
        bundle.remove(key_event)
        bundle.putSerializable(key_event, event)
    }

    private fun findEvent(): Event {
        return bundle.getSerializable(key_event) as Event
    }

    private fun putTalk(talk: Talk) {
        bundle.remove(key_talk)
        bundle.putSerializable(key_talk, talk)
    }

    private fun findTalk(): Talk {
        return bundle.getSerializable(key_talk) as Talk
    }

    var event: Event
        get() = findEvent()
        set(value) = putEvent(value)

    var talk: Talk
        get() = findTalk()
        set(value) = putTalk(value)
}