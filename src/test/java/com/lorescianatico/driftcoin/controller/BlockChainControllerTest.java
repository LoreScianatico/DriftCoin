package com.lorescianatico.driftcoin.controller;

import com.lorescianatico.driftcoin.model.BlockFactory;
import com.lorescianatico.driftcoin.service.BlockMiner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest
public class BlockChainControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BlockMiner blockMiner;

    @Test
    public void createChain() {

        Mockito.when(blockMiner.createChain()).thenReturn(Mono.just(BlockFactory.getBlockChain()));

        this.webTestClient.get().uri("/").exchange()
                .expectStatus().isOk();

    }
}