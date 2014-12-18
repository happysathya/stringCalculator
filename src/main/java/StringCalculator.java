import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
    public int add(final String input) {
        if (input == null) return 0;
        if ("".equals(input)) return 0;
        final Matcher matcher = getMatcher(input);
        String[] split = matcher.matches() ?
                matcher.group(2).split(matcher.group(1).replace("*", "\\*").replace("][", "|")) :
                input.split("[,\n]");
        int sum = 0;
        final List<String> negativeIntegers = new ArrayList<>();
        for (String s : split) {
            int data = Integer.parseInt(s.trim());
            if (data < 0)
                negativeIntegers.add(s.trim());
            else if (data <= 1000) sum += data;
        }

        if (!negativeIntegers.isEmpty()) {
            final String message = "Negative numbers: " + negativeIntegers.stream().collect(Collectors.joining(","));
            throw new IllegalArgumentException(message);
        }
        return sum;
    }

    private Matcher getMatcher(final String input) {

        final Pattern pattern1 = Pattern.compile("//\\[(.+)\\]\n(.*)");
        final Matcher matcher1 = pattern1.matcher(input);
        if (matcher1.matches()) return matcher1;

        final Pattern pattern2 = Pattern.compile("//(.+)\n(.*)");
        final Matcher matcher2 = pattern2.matcher(input);
        return matcher2;
    }
}
