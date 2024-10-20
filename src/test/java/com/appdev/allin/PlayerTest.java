package com.appdev.allin;

import com.appdev.allin.player.PlayerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlayerTest {

    @Autowired private PlayerController controller;
}
