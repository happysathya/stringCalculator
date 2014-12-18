import org.junit.Before;
import org.junit.Test;

import java.util.IllegalFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Before
    public void setup() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void addNumber_shouldReturn0_ifStringIsNull() {
        int result = stringCalculator.add(null);
        assertEquals(0, result);
    }

    @Test
    public void addNumber_shouldReturn0_ifStringIsEmpty() {
        int result = stringCalculator.add("");
        assertEquals(0, result);
    }

    @Test
    public void addNumber_shouldReturnTheSumAsTheGivenNumber_ifInputIsSingle() {
        int result = stringCalculator.add("1");
        assertEquals(1, result);
    }

    @Test
    public void addNumber_shouldReturnTheSum_ofGivenNumbersSeparatedByCommaInAString() {
        assertEquals(3, stringCalculator.add("1,2"));
        assertEquals(8, stringCalculator.add("1,2,5"));
    }

    @Test
    public void addNumber_shouldReturnTheSum_ofGivenNumbersSeparatedByEitherCommaOrNewLine() {
        assertEquals(3, stringCalculator.add("1\n2"));
        assertEquals(8, stringCalculator.add("1\n2,5"));
    }

    @Test
    public void addNumber_shouldReturnTheSum_ofGivenNumbersSeparatedByGivenDelimiter() {
        assertEquals(7, stringCalculator.add("//;\n5;2"));
        assertEquals(13, stringCalculator.add("//;\n10;3"));
        assertEquals(57, stringCalculator.add("//:\n50:7"));
        assertEquals(57, stringCalculator.add("//*\n50*7"));
    }

    @Test
    public void addNumber_shouldThrowException_ifTheDataContainsNegativeNumbers() {
        try {
            stringCalculator.add("//;\n5;-22;-31");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Negative numbers: -22,-31", e.getMessage());
        }
    }

    @Test
    public void addNumber_shouldIgnoreTheNumbersGreaterThan1000() {
        assertEquals(5, stringCalculator.add("//;\n5;1002"));
        assertEquals(1003, stringCalculator.add("//;\n1000;3"));
    }

    @Test
    public void addNumber_shouldHandleMoreThanSingleCharDelimiters() {
        assertEquals(5, stringCalculator.add("//;;\n5;;1002"));
        assertEquals(1003, stringCalculator.add("//;;\n1000;;3"));
        assertEquals(2003, stringCalculator.add("//[***]\n1000***3***1000"));
    }

    @Test
    public void addNumber_shouldHandleMultiCharDelimiters() {
        assertEquals(6, stringCalculator.add("//[*][%]\n1*2%3"));
    }
}
