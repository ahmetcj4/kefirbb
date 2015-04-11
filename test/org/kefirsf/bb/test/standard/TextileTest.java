package org.kefirsf.bb.test.standard;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.ConfigurationFactory;

/**
 * Tests for the Textile configuration.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class TextileTest extends AbstractConfigurationTest {
    /**
     * Create Textile text processor
     */
    @Before
    public void createProcessor() {
        processor = BBProcessorFactory.getInstance().createFromResource(
                ConfigurationFactory.TEXTILE_CONFIGURATION_FILE
        );
    }

    /**
     * Paragraphs
     * http://txstyle.org/doc/2/paragraphs
     */
    @Test
    public void testParagraphs() {
        assertProcess(
                "<p>A paragraph.</p><p>And a paragraph with<br/>a line break.</p>",
                "A paragraph.\n\nAnd a paragraph with\na line break."
        );

        assertProcess(
                "<p>A paragraph.</p><p>And a paragraph with<br/>a line break.</p>",
                "A paragraph.\n \t\nAnd a paragraph with\na line break."
        );

        assertProcess(
                "<p>A paragraph.</p><p>And a paragraph with<br/>a line break.</p>",
                "p. A paragraph.\n\np. And a paragraph with\na line break."
        );

        assertProcess(
                "<p>This is a normal paragraph.<br/>With a continuation line.</p>But no paragraph tags here.",
                "This is a normal paragraph.\nWith a continuation line.\n\n But no paragraph tags here."
        );

        assertProcess(
                "<p style=\"text-align:left;\">Aligned left paragraph (default).</p>",
                "p<. Aligned left paragraph (default)."
        );

        assertProcess(
                "<p style=\"text-align:right;\">Aligned right paragraph.</p>",
                "p>. Aligned right paragraph."
        );

        assertProcess(
                "<p style=\"text-align:center;\">Centered paragraph.</p>",
                "p=. Centered paragraph."
        );

        assertProcess(
                "<p style=\"text-align:justify;\">Justified paragraph.</p>",
                "p<>. Justified paragraph."
        );

        assertProcess(
                "<p style=\"padding-left:1em;\">Left indent 1em.</p>",
                "p(. Left indent 1em."
        );

        assertProcess(
                "<p style=\"padding-left:2em;\">Left indent 2em.</p>",
                "p((. Left indent 2em."
        );

        assertProcess(
                "<p style=\"padding-left:3em;\">Left indent 3em.</p>",
                "p(((. Left indent 3em."
        );

        assertProcess(
                "<p style=\"padding-left:7em;\">Left indent 7em.</p>",
                "p(((((((. Left indent 7em."
        );

        assertProcess(
                "<p style=\"padding-right:7em;\">Right indent 7em.</p>",
                "p))))))). Right indent 7em."
        );

        assertProcess(
                "<p>1</p><p>2</p><p>3</p><p>4</p>",
                "1\n\n2\n\n3\n\n4"
        );

        assertProcess(
                "<p>1<br/>2</p>",
                "1\n2\n\n"
        );

        assertProcess(
                "<p>1<br/>2</p><p>3</p>",
                "1\n2\n\n3\n\n"
        );

        assertProcess(
                "<p>1</p><p>2</p><p>3</p><p>4</p>",
                "1\n\n2\n\n3\n\n4"
        );

        // Language
        assertProcess(
                "<p lang=\"ru\">Language</p>",
                "p[ru]. Language"
        );

        assertProcess(
                "<p style=\"text-align:left;padding-left:1em;\" lang=\"ru\">Language right</p>",
                "p<([ru]. Language right"
        );

        assertProcess(
                "<p lang=\"ru\" style=\"padding-right:1em;text-align:right;\">Language left</p>",
                "p[ru])>. Language left"
        );
    }

    /**
     * Headers
     * http://txstyle.org/doc/7/headings
     */
    @Test
    public void testHeaders() {
        assertProcess("<h1>Header</h1>", "h1. Header");
        assertProcess("<h1>Header</h1>", "h1. Header\n");
        assertProcess("<h1>Header</h1>", "h1. Header\n\n");

        assertProcess("<h2>Header</h2>", "h2. Header");
        assertProcess("<h2>Header</h2>", "h2. Header\n");
        assertProcess("<h2>Header</h2>", "h2. Header\n\n");

        assertProcess("<h3>Header</h3>", "h3. Header");
        assertProcess("<h3>Header</h3>", "h3. Header\n");
        assertProcess("<h3>Header</h3>", "h3. Header\n\n");

        assertProcess("<h4>Header</h4>", "h4. Header");
        assertProcess("<h4>Header</h4>", "h4. Header\n");
        assertProcess("<h4>Header</h4>", "h4. Header\n\n");

        assertProcess("<h5>Header</h5>", "h5. Header");
        assertProcess("<h5>Header</h5>", "h5. Header\n");
        assertProcess("<h5>Header</h5>", "h5. Header\n\n");

        assertProcess("<h6>Header</h6>", "h6. Header");
        assertProcess("<h6>Header</h6>", "h6. Header\n");
        assertProcess("<h6>Header</h6>", "h6. Header\n\n");

        assertProcess("<h3 style=\"text-align:left;\">Header</h3>", "h3<. Header");
        assertProcess("<h3 style=\"text-align:right;\">Header</h3>", "h3>. Header");
        assertProcess("<h3 style=\"text-align:center;\">Header</h3>", "h3=. Header");
        assertProcess("<h3 style=\"text-align:justify;\">Header</h3>", "h3<>. Header");

        assertProcess(
                "<h5>Header</h5><h6>Header</h6><p>Paragraph.</p>",
                "h5. Header\n\nh6. Header\n\nParagraph."
        );

        // Language
        assertProcess(
                "<h1 lang=\"ru\">Language</h1>",
                "h1[ru]. Language"
        );

        assertProcess(
                "<h1 style=\"text-align:left;padding-left:1em;\" lang=\"ru\">Language right</h1>",
                "h1<([ru]. Language right"
        );

        assertProcess(
                "<h1 lang=\"ru\" style=\"padding-right:1em;text-align:right;\">Language left</h1>",
                "h1[ru])>. Language left"
        );
    }

    /**
     * Blockcode
     * http://txstyle.org/doc/4/block-code
     */
    @Test
    public void testBlockCode() {
        // bc.
        assertProcess("<pre><code>Code in the ond of text</code></pre>", "bc. Code in the ond of text");
        assertProcess("<pre><code>Code with one nl</code></pre>", "bc. Code with one nl\n");
        assertProcess("<pre><code>Code with double nl</code></pre>", "bc. Code with double nl\n\n");
        assertProcess(
                "<pre lang=\"ru\" style=\"text-align:left;padding-left:1em;\"><code>Code in the ond of text</code></pre>",
                "bc[ru]<(. Code in the ond of text"
        );

        assertProcess(
                "<pre><code>Code with\na new string.</code></pre><p>And the paragraph.</p>",
                "bc. Code with\na new string.\n\nAnd the paragraph."
        );

    }

    /**
     * Long block code
     */
    @Test
    public void testMultilineBlockCode(){
        assertProcess(
                "<pre><code>The code\n\nWith blank line.\nWith new line.</code></pre><p>End of code.</p>",
                "bc.. The code\n\nWith blank line.\nWith new line.\n\np. End of code."
        );
        assertProcess(
                "<pre style=\"text-align:left;\"><code>The code\n\nWith blank line.\nWith new line.</code></pre><p>End of code.</p>",
                "bc<.. The code\n\nWith blank line.\nWith new line.\n\np. End of code."
        );
    }

    /**
     * Preformatted text
     * http://txstyle.org/doc/5/pre-formatted-text
     */
    @Test
    public void testPreformatted(){
        // pre.
        assertProcess("<pre>Pre text.</pre>", "pre. Pre text.");
        assertProcess("<pre>Pre text.</pre>", "pre. Pre text.\n");
        assertProcess("<pre>Pre text.</pre>", "pre. Pre text.\n\n");

        assertProcess(
                "<pre style=\"padding-right:1em;\">Pre text.</pre>",
                "pre). Pre text.\n\n"
        );

        assertProcess(
                "<pre>Pre text.\nWith a new line.</pre><p>Paragraph.</p>",
                "pre. Pre text.\nWith a new line.\n\nParagraph."
        );
    }

    /**
     * Long preformatted
     */
    @Test
    public void testMultilinePreformatted(){
        assertProcess(
                "<pre>The code\n\nWith blank line.\nWith new line.</pre><p>End of code.</p>",
                "pre.. The code\n\nWith blank line.\nWith new line.\n\np. End of code."
        );
    }

    /**
     * Comments
     * http://txstyle.org/doc/40/textile-comments
     */
    @Test
    public void testComments(){
        assertProcess("", "###. Comment");
        assertProcess("", "###. Comment\n");
        assertProcess("", "###. Comment\n\n");
        assertProcess("", "###. Comment\nnew line\n\n");
        assertProcess("<p>Text</p>", "###. Comment\n\nText");
    }

    /**
     * Multiline comments
     */
    @Test
    public void testMultilineComments(){
        assertProcess("<p>Paragraph</p>", "###.. Comment\nnew line\n\nWith blank line.\n\np. Paragraph");
        assertProcess("<p lang=\"ru\">Paragraph</p>", "###.. Comment\nnew line\n\nWith blank line.\n\np[ru]. Paragraph");
    }

    /**
     * Block quotations
     * http://txstyle.org/doc/3/block-quotations
     */
    @Test
    public void testBlockQuote(){
        assertProcess("<blockquote><p>A quoted text.</p></blockquote>", "bq. A quoted text.");
        assertProcess("<blockquote><p>A quoted text.</p></blockquote>", "bq. A quoted text.\n");
        assertProcess("<blockquote><p>A quoted text.</p></blockquote>", "bq. A quoted text.\n\n");
        assertProcess("<blockquote><p>A quoted text.<br/>Newline</p></blockquote>", "bq. A quoted text.\nNewline\n\n");

        assertProcess(
                "<blockquote cite=\"http://kefirsf.org/\"><p>A quoted text.</p></blockquote>",
                "bq.:http://kefirsf.org/ A quoted text."
        );
    }

    @Test
    public void testMultilineBlockQuote(){
        assertProcess(
                "<blockquote><p>Paragraph 1</p><p>Paragraph 2</p></blockquote><blockquote><p>New quotation</p></blockquote>",
                "bq.. Paragraph 1\n\nParagraph 2\n\nbq.. New quotation"
        );
    }

    /**
     * No formatting
     * http://txstyle.org/doc/6/no-textile-processing
     */
    @Test
    public void testNoFormatting(){
        assertProcess("Text", "notextile. Text");
        assertProcess("<p>Text with no ^2^ formatting.</p>", "Text with no ==^2^== formatting.");
    }

    /**
     * Test inline CSS-styles
     * http://txstyle.org/doc/23/css-styles
     */
    @Test
    public void testCssStyle(){
        assertProcess(
                "<p style=\"margin:1px\">Margin.</p>",
                "p{margin:1px}. Margin."
        );

        assertProcess(
                "<p style=\"margin:1px;\">Margin with semicolon.</p>",
                "p{margin:1px;}. Margin with semicolon."
        );

        assertProcess(
                "<p style=\"margin:1px;padding:1px\">Margin padding.</p>",
                "p{margin:1px;padding:1px}. Margin padding."
        );

        assertProcess(
                "<p style=\"margin:1px;padding:1px;\">Margin padding with semicolon.</p>",
                "p{margin:1px;padding:1px;}. Margin padding with semicolon."
        );

        assertProcess(
                "<p>The <span style=\"padding: 5px;\">span</span> with a style.</p>",
                "The %{padding: 5px;}span% with a style."
        );
    }

    /**
     * Test CSS classes and ids.
     * http://txstyle.org/doc/22/css-classes
     */
    @Test
    public void testCssClass(){
        assertProcess(
                "<p>The <span class=\"my-CLASS\">span</span> with a class.</p>",
                "The %(my-CLASS)span% with a class."
        );
        assertProcess(
                "<p>The <span id=\"my-id\">span</span> with an id.</p>",
                "The %(#my-id)span% with an id."
        );
        assertProcess(
                "<p>The <span id=\"my-id\" class=\"my-class\">span</span> with a class and an id.</p>",
                "The %(my-class#my-id)span% with a class and an id."
        );
        assertProcess(
                "<p>The <span class=\"my-class\" style=\"padding: 5px;\">span</span> with a class and a style.</p>",
                "The %(my-class){padding: 5px;}span% with a class and a style."
        );
        assertProcess(
                "<p>The <span id=\"my-id\" class=\"my-class\" style=\"padding: 5px;\">span</span> with a class and an id and a style.</p>",
                "The %(my-class#my-id){padding: 5px;}span% with a class and an id and a style."
        );
    }

    /**
     * Inline code
     * http://txstyle.org/doc/21/inline-code
     */
    @Test
    public void testInlineCode(){
        assertProcess("<p>The string with <code>a code</code>.</p>", "The string with @a code@.");
    }


    /**
     * http://redcloth.org/hobix.com/textile/
     */
    @Test
    public void testWriting() {
        assertProcess(
                "<p>I spoke.<br/>And none replied.</p>",
                "I spoke.\nAnd none replied."
        );

        assertProcess(
                "<p>&ldquo;Observe!&rdquo;</p>",
                "\"Observe!\""
        );

        assertProcess(
                "<p>Observe &mdash; very nice!</p>",
                "Observe -- very nice!"
        );

        assertProcess(
                "<p>Observe &ndash; tiny and brief.</p>",
                "Observe - tiny and brief."
        );

        assertProcess(
                "<p>Observe&hellip;</p>",
                "Observe..."
        );

        assertProcess(
                "<p>Observe: 2 &times; 2.</p>",
                "Observe: 2 x 2."
        );

        assertProcess(
                "<p>one&trade;, two&reg;, three&copy;.</p>",
                "one(TM), two(R), three(C)."
        );
    }

    @Test
    public void testPhrase() {
        assertProcess(
                "<p>I <em>believe</em> every word.</p>",
                "I _believe_ every word."
        );
        assertProcess(
                "<p>I <em style=\"padding:1px;\">believe</em> every word.</p>",
                "I _{padding:1px;}believe_ every word."
        );

        assertProcess(
                "<p>And then? She <strong>fell</strong>!</p>",
                "And then? She *fell*!"
        );
        assertProcess(
                "<p>And then? She <strong style=\"padding:1px;\">fell</strong>!</p>",
                "And then? She *{padding:1px;}fell*!"
        );

        assertProcess(
                "<p>I <i>know</i>.<br/>I <b>really</b> <i>know</i>.</p>",
                "I __know__.\nI **really** __know__."
        );
        assertProcess(
                "<p>I <i style=\"padding:1px;\">know</i>.<br/>I <b style=\"padding:1px;\">really</b> <i>know</i>.</p>",
                "I __{padding:1px;}know__.\nI **{padding:1px;}really** __know__."
        );

        assertProcess(
                "<p><cite>Cat&apos;s Cradle</cite> by Vonnegut</p>",
                "??Cat's Cradle?? by Vonnegut"
        );
        assertProcess(
                "<p><cite style=\"padding:1px;\">Cat&apos;s Cradle</cite> by Vonnegut</p>",
                "??{padding:1px;}Cat's Cradle?? by Vonnegut"
        );

        assertProcess(
                "<p>Convert with <code>r.to_html</code></p>",
                "Convert with @r.to_html@"
        );
        assertProcess(
                "<p>Convert with <code style=\"padding:1px;\">r.to_html</code></p>",
                "Convert with @{padding:1px;}r.to_html@"
        );

        assertProcess(
                "<p>I&apos;m <del>sure</del> not sure.</p>",
                "I'm -sure- not sure."
        );
        assertProcess(
                "<p>I&apos;m <del style=\"padding:1px;\">sure</del> not sure.</p>",
                "I'm -{padding:1px;}sure- not sure."
        );

        assertProcess(
                "<p>You are a <ins>pleasant</ins> child.</p>",
                "You are a +pleasant+ child."
        );
        assertProcess(
                "<p>You are a <ins style=\"padding:1px;\">pleasant</ins> child.</p>",
                "You are a +{padding:1px;}pleasant+ child."
        );

        assertProcess(
                "<p>a <sup>2</sup> + b <sup>2</sup> = c <sup style=\"padding:1px;\">2</sup></p>",
                "a ^2^ + b ^2^ = c ^{padding:1px;}2^"
        );

        assertProcess(
                "<p>log <sub>2</sub> x</p>",
                "log ~2~ x"
        );
        assertProcess(
                "<p>log <sub style=\"padding:1px;\">2</sub> x</p>",
                "log ~{padding:1px;}2~ x"
        );

        assertProcess(
                "<p>The <span>span</span> example.</p>",
                "The %span% example."
        );
    }

    @Test
    public void testBlocks() {
        assertProcess(
                "<p>A single paragraph.</p><p>Followed by another.</p>",
                "A single paragraph.\n\nFollowed by another."
        );

        assertProcess(
                "<p>A single paragraph.<br/>With a line break.</p><p>Followed by another.</p>",
                "A single paragraph.\nWith a line break.\n\nFollowed by another."
        );

        assertProcess(
                "<p>A paragraph.</p>",
                "A paragraph.\n\n"
        );

        assertProcess(
                "<p>A paragraph.</p>",
                "A paragraph.\n"
        );

        assertProcess(
                "<p>An old text</p><blockquote><p>A block quotation.</p></blockquote><p>Any old text</p>",
                "An old text\n\nbq. A block quotation.\n\nAny old text"
        );

        // Foot note
        assertProcess(
                "<p>This is covered elsewhere<sup><a href=\"#fn1\">1</a></sup>.</p>",
                "This is covered elsewhere[1]."
        );
        assertProcess(
                "<p id=\"fn1\"><sup>1</sup> Down here, in fact.</p>",
                "fn1. Down here, in fact."
        );
        assertProcess(
                "<p id=\"fn2\"><sup>2</sup> Down here, in fact.</p>",
                "fn2. Down here, in fact.\n"
        );
        assertProcess(
                "<p id=\"fn234\"><sup>234</sup> Down here, in fact.</p>",
                "fn234. Down here, in fact.\n\n"
        );
    }
}
