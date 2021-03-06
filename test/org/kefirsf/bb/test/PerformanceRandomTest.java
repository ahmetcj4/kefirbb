package org.kefirsf.bb.test;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.ConfigurationFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.conf.Configuration;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Random;

/**
 * It's test for profiling
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class PerformanceRandomTest {
    private static final String[] symbols = {
            "[b]", "[/b]",
            "[i]", "[/i]",
            "[s]", "[/s]",
            "[u]", "[/u]",
            "[img]", "[/img]",
            "[url]", "[/url]",
            "[url=", "]", "[/]",
            "[", "\\[", "\\]",
            "&", "'", "\"", "\\", ";", "<", ">",
            "test", "src", "text", "123", "http://",
            "=", ",", "Превед", "Медвед", "Smells like teen spirit",
            "Nevermind", "Contra la Contra", "Hate to state",
            "Klowns", " ", "\n", "\r", "Apple", "Sun microsystems",
            "IBM", "Hello world", "Good job", "Spring", "Hibernate",
            "Grails", "KefirBB", "http://kefirsf.org/kefirbb/",
            "Sitemech", "More english words"
    };

    public static void main(String[] args) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            builder.append(symbols[random.nextInt(symbols.length)]);
        }
        String text = builder.toString();

        Configuration conf = ConfigurationFactory.getInstance().createFromResource("org/kefirsf/bb/default.xml");
        conf.setPropagateNestingException(true);
        conf.setNestingLimit(1000);
        TextProcessor processor = BBProcessorFactory.getInstance().create(conf);

        long start = System.currentTimeMillis();
        String result = processor.process(text);
        long finish = System.currentTimeMillis();

        DecimalFormat format = new DecimalFormat("# ##0");
        System.out.println(
                MessageFormat.format("Text length: {0} chars.", format.format(text.length()))
        );

        System.out.println(
                MessageFormat.format("Time: {0} milliseconds.", format.format(finish - start))
        );

        System.out.println(MessageFormat.format("Result: {0}", result.substring(0, Math.min(256, result.length()))));
    }
}
