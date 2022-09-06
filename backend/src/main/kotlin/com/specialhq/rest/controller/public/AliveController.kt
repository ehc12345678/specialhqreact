package com.specialhq.rest.controller.public

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/public"])
class AliveController {
    @GetMapping("/alive", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getIsAlive(): String = "Alive"
}