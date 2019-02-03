package com.lorescianatico.driftcoin.controller;

import com.lorescianatico.driftcoin.service.BlockMiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class BlockChainController {

    private BlockMiner miner;

    public BlockChainController(BlockMiner blockMiner) {
        this.miner=blockMiner;
    }

    @GetMapping({"", "/", "/index"})
    public String createChain(Model model) {
        logger.debug("Getting Index page");

        model.addAttribute("blockchain", miner.createChain());

        return "index";
    }

}
