package com.hclc.nrgyinvoicr.backend.plans.boundary;

import com.hclc.nrgyinvoicr.backend.plans.control.ExpressionTester;
import com.hclc.nrgyinvoicr.backend.plans.entity.ExpressionTestInput;
import com.hclc.nrgyinvoicr.backend.plans.entity.ExpressionTestResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/expressions/tested")
public class ExpressionController {
    private final ExpressionTester expressionTester;

    public ExpressionController(ExpressionTester expressionTester) {
        this.expressionTester = expressionTester;
    }

    @PostMapping
    @Transactional(readOnly = true)
    public ExpressionTestResult testExpression(@RequestBody ExpressionTestInput input) throws IOException {
        return expressionTester.test(input.getExpression());
    }
}
