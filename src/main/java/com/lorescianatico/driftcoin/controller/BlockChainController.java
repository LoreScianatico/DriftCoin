package com.lorescianatico.driftcoin.controller;

import com.lorescianatico.driftcoin.service.BlockMiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class BlockChainController {

    @Autowired
    private BlockMiner miner;

    @RequestMapping({"", "/", "/index"})
    public String createBlock(Model model) {
        log.debug("Getting Index page");

        model.addAttribute("blockchain", miner.createChain());

        return "index";
    }

}
