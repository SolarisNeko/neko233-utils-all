package com.neko233.action_chain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ActionChainTest {

    @Test
    public void testRecursiveRetry() throws Exception {
        log.info("----Demo--------");

        ActionChain.create()
                .success(() -> {
                    System.out.println("HHHHHHHHHHHH");

                    throw new RuntimeException("test exception retry");
                })
                .success(() -> {
                    System.out.println("----- handler 2 ---------");
                })
                .exception((e) -> {
                    log.error("error.", e);
                })
                .doFinally(() -> {
                    System.out.println("finally end.");
                })
                .retry(2, 3)
                .closable(null)
                .execute();
    }

}