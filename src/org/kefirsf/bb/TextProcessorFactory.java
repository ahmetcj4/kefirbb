package org.kefirsf.bb;

/**
 * The TextProcessor factory interface
 *
 * @author Kefir
 */
public interface TextProcessorFactory {
    /**
     * Create the TextProcessor instance
     *
     * @return instance of TextProcessor interface
     * @throws TextProcessorFactoryException when factory can't create the TextProcessor instance
     */
    TextProcessor create();
}
