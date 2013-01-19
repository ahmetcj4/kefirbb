package org.kefirsf.bb.comp;

import java.io.IOException;

/**
 * The bbcode class
 *
 * @author Kefir
 */
public class WCode implements Comparable<WCode> {
    /**
     * template for build result char sequence
     */
    private final WTemplate template;
    /**
     * Pattern for parsing code
     */
    private final WPattern pattern;
    /**
     * Priority. If priority higher then code be checking early.
     */
    private final int priority;
    /**
     * The code name.
     */
    private final String name;

    /**
     * Create the bb-code with priority
     *
     * @param pattern  pattern to parse the source text
     * @param template template to build target text
     * @param name     name of code
     * @param priority priority. If priority higher then code be checking early.
     */
    public WCode(WPattern pattern, WTemplate template, String name, int priority) {
        this.template = template;
        this.priority = priority;
        this.name = name;
        this.pattern = pattern;
    }

    /**
     * Parse bb-code
     * <p/>
     * Before invocation suspicious method must be call
     *
     * @param context the bb-processing context
     * @return true - if parse source
     *         false - if can't parse code
     * @throws java.io.IOException if can't append to target
     */
    public boolean process(Context context) throws IOException {
        Context codeContext = new Context(context);
        if (pattern.parse(codeContext)) {
            codeContext.mergeWithParent();
            template.generate(context);
            return true;
        }

        return false;
    }

    /**
     * Check if next sequence can be parsed with this code.
     * It's most called method in this project.
     *
     * @param source text source
     * @return true - if next sequence can be parsed with this code;
     *         false - only if next sequence can't be parsed with this code.
     */
    public boolean suspicious(Source source) {
        return pattern.suspicious(source);
    }

    public int findSuspicious(Source source) {
        return pattern.findSuspicious(source);
    }

    /**
     * Compare by priorities
     */
    public int compareTo(WCode code) {
        return this.priority - code.priority;
    }

    /**
     * Get code name
     *
     * @return code name
     */
    public String getName() {
        return name;
    }
}
