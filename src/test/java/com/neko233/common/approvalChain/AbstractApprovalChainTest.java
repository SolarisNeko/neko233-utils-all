package com.neko233.common.approvalChain;

import com.neko233.common.approvalChain.AbstractApprovalChain;
import com.neko233.common.approvalChain.ApprovalChainNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author SolarisNeko
 * Date on 2022-12-15
 */
public class AbstractApprovalChainTest {

    class DemoApprovalChain extends AbstractApprovalChain<String, String> {

    }

    @Test
    public void testOk() {
        DemoApprovalChain demoPipeline = new DemoApprovalChain();
        String output = demoPipeline.addPipelineNode(new ApprovalChainNode<String, String>() {
                    @Override
                    public boolean isItHandle() {
                        return true;
                    }

                    @Override
                    public String handle(String o) {
                        return "1: " + o;
                    }
                })
                .addPipelineNode(new ApprovalChainNode<String, String>() {
                    @Override
                    public boolean isItHandle() {
                        return true;
                    }

                    @Override
                    public String handle(String o) {
                        return "2: " + o;
                    }
                })
                .handle("hello");

        Assert.assertEquals("1: hello", output);
    }

}