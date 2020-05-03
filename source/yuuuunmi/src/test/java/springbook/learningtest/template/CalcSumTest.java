package springbook.learningtest.template;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CalcSumTest {

    Calculator calculator;
    String numFilePath;

    @Before
    public void setup() {
        this.calculator = new Calculator();
        this.numFilePath = getClass().getResource("numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        int result = calculator.calcSum(numFilePath);
        Assert.assertEquals(result, 10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        int result = calculator.calMultiply(numFilePath);
        Assert.assertEquals(result, 24);
    }

    @Test
    public void concatenateStings() throws IOException {
        String result = calculator.concatenate(numFilePath);
        Assert.assertEquals(result, "1234");
    }


}
