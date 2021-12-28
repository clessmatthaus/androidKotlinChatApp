package fr.clesspavy

class Dialogue {
    var dialogue: String? = null
    var senderId: String? = null

    constructor() {}

    constructor(dialogue: String?, senderId: String? ) {
        this.dialogue = dialogue
        this.senderId = senderId
    }
}