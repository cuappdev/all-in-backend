package com.example.allin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.allin.player.PlayerController;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest
class PlayerTest {

	@Autowired
	private PlayerController controller;
}
