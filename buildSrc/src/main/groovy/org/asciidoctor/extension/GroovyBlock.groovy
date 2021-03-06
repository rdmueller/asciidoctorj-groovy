package org.asciidoctor.extension

import org.asciidoctor.extension.BlockProcessor
import org.asciidoctor.ast.AbstractBlock
import org.asciidoctor.extension.Reader

class GroovyBlock extends BlockProcessor {

    private static binding = new Binding()

    GroovyBlock(String name, Map<String, Object> config) {
        super(name, [contexts: [':paragraph'], content_model: ':verbatim'])
        binding.context = [:]
    }

    def process(AbstractBlock parent, Reader reader, Map<String, Object> attributes) {
        def code = reader.lines().join("\n")
        def shell = new GroovyShell(binding)
        def result = shell.evaluate(code)

        //second parameter is "context" which seems to take one of
        //these values: https://github.com/asciidoctor/asciidoctor/blob/master/lib/asciidoctor/block.rb#L14

        def codeblock = createBlock(parent, 'listing', "$code", [:],[:])
        parent.blocks().add(codeblock)

        def resultBlock = createBlock(parent, 'listing', "$result", [:], [:])
        parent.blocks().add(resultBlock)

        null
    }


}
